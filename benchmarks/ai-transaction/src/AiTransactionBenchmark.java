import com.sun.net.httpserver.HttpServer;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.*;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceManagedTypes;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import javax.sql.DataSource;
import java.lang.reflect.*;
import java.net.*;
import java.net.http.*;
import java.nio.file.*;
import java.sql.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

// shortcut: synchronous boundary comparison, validate the production coroutine path separately before claiming API results.
public class AiTransactionBenchmark {
    static final int POOL = 5, CONCURRENCY = 10, REQUESTS = 30, ROUNDS = 3, DELAY_MS = 3000;
    static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();
    static final List<Lease> LEASES = new CopyOnWriteArrayList<>();
    static final List<Result> RESULTS = new CopyOnWriteArrayList<>();
    static final ConcurrentMap<String, AtomicInteger> OPEN = new ConcurrentHashMap<>();
    static final AtomicInteger AI_CALLS = new AtomicInteger();
    static final UUID DIARY_ID = UUID.fromString("68b4a7cc-a9bf-47ac-a53e-95d76c075002");
    static EntityManagerFactory emf;
    static TransactionTemplate write, read;
    static HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
    static URI aiUrl;
    record Lease(String requestId, double waitMs, double holdMs) {}
    record Result(String mode, int round, String id, double elapsedMs, int aiOpen, boolean aiTransaction, int status) {}
    static class AiFailure extends RuntimeException {}

    @Entity(name = "BenchDiary")
    @Table(name = "diary", schema = "hear_tx_bench")
    public static class BenchDiary {
        @Id public UUID id;
        public String content;
        public BenchDiary() {}
    }

    static class MeasuredDataSource extends AbstractDataSource {
        final DataSource delegate;
        MeasuredDataSource(DataSource delegate) { this.delegate = delegate; }
        public Connection getConnection() throws SQLException { return borrow(null, null); }
        public Connection getConnection(String user, String password) throws SQLException { return borrow(user, password); }
        Connection borrow(String user, String password) throws SQLException {
            String id = REQUEST_ID.get();
            long start = System.nanoTime();
            Connection connection = user == null ? delegate.getConnection() : delegate.getConnection(user, password);
            long acquired = System.nanoTime();
            if (id == null) return connection;
            OPEN.computeIfAbsent(id, k -> new AtomicInteger()).incrementAndGet();
            AtomicBoolean closed = new AtomicBoolean();
            return (Connection) java.lang.reflect.Proxy.newProxyInstance(Connection.class.getClassLoader(), new Class<?>[]{Connection.class}, (proxy, method, args) -> {
                try {
                    if (method.getName().equals("close")) {
                        if (closed.compareAndSet(false, true)) {
                            try { connection.close(); }
                            finally {
                                LEASES.add(new Lease(id, ms(acquired - start), ms(System.nanoTime() - acquired)));
                                OPEN.get(id).decrementAndGet();
                            }
                        }
                        return null;
                    }
                    return method.invoke(connection, args);
                } catch (InvocationTargetException ex) { throw ex.getCause(); }
            });
        }
    }

    static double ms(long nanos) { return nanos / 1_000_000.0; }
    static void check(boolean value, String message) { if (!value) throw new AssertionError(message); }
    static BenchDiary load() {
        EntityManager em = EntityManagerFactoryUtils.getTransactionalEntityManager(emf);
        BenchDiary diary = em.find(BenchDiary.class, DIARY_ID);
        check(diary != null && diary.content.equals("fixture"), "Fixture changed or missing");
        return diary;
    }
    static void callAi(boolean success) {
        try {
            var request = HttpRequest.newBuilder(aiUrl.resolve(success ? "/success" : "/failure"))
                .timeout(Duration.ofSeconds(10)).POST(HttpRequest.BodyPublishers.ofString("{}"))
                .header("Content-Type", "application/json").build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 500) throw new AiFailure();
            check(response.statusCode() == 200, "Unexpected AI HTTP status");
        } catch (AiFailure ex) { throw ex; }
        catch (Exception ex) { throw new RuntimeException(ex); }
    }
    static Result execute(String mode, int round, int index, boolean success) {
        String id = mode + "-" + round + "-" + index;
        REQUEST_ID.set(id);
        long started = System.nanoTime();
        int[] open = {-1}; boolean[] active = {false};
        Runnable work = () -> {
            BenchDiary diary = mode.equals("inside") ? load() : read.execute(s -> load());
            open[0] = OPEN.getOrDefault(id, new AtomicInteger()).get();
            active[0] = TransactionSynchronizationManager.isActualTransactionActive();
            callAi(success);
            if (mode.equals("inside")) {
                diary.content = "saved";
            } else {
                write.executeWithoutResult(s -> {
                    var em = EntityManagerFactoryUtils.getTransactionalEntityManager(emf);
                    em.find(BenchDiary.class, DIARY_ID).content = "saved";
                });
            }
        };
        int status;
        try {
            if (mode.equals("inside")) write.executeWithoutResult(s -> work.run());
            else work.run();
            status = 200;
        } catch (AiFailure ex) { status = 500; }
        finally { REQUEST_ID.remove(); }
        check(OPEN.get(id).get() == 0, "Connection leak: " + id);
        check(open[0] == (mode.equals("inside") ? 1 : 0), "Unexpected AI connection ownership");
        check(active[0] == mode.equals("inside"), "Unexpected AI transaction state");
        check(status == (success ? 200 : 500), "Unexpected result");
        return new Result(mode, round, id, ms(System.nanoTime() - started), open[0], active[0], status);
    }
    static double p95(List<Double> values) {
        var sorted = values.stream().sorted().toList();
        return sorted.get((int) Math.ceil(sorted.size() * .95) - 1);
    }
    static String summary(String mode, int round) {
        var requests = RESULTS.stream().filter(r -> r.mode.equals(mode) && (round == 0 || r.round == round)).toList();
        var ids = new HashSet<>(requests.stream().map(Result::id).toList());
        var leases = LEASES.stream().filter(l -> ids.contains(l.requestId)).toList();
        var holds = requests.stream().map(r -> leases.stream().filter(l -> l.requestId.equals(r.id)).mapToDouble(Lease::holdMs).sum()).toList();
        return String.format(Locale.ROOT, "%s,%d,%d,%d,%.3f,%.3f,%.3f,%.3f", mode, round, requests.size(), leases.size(),
            holds.stream().mapToDouble(Double::doubleValue).average().orElseThrow(), p95(holds),
            p95(leases.stream().map(Lease::waitMs).toList()), p95(requests.stream().map(Result::elapsedMs).toList()));
    }
    public static void main(String[] args) throws Exception {
        check(p95(List.of(1.0, 2.0, 3.0, 4.0)) == 4, "p95 self-check");
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/hear_test");
        config.setUsername("agent_hear");
        config.setPassword(Objects.requireNonNull(System.getenv("BENCH_DB_PASSWORD"), "BENCH_DB_PASSWORD required"));
        config.setMaximumPoolSize(POOL); config.setMinimumIdle(POOL); config.setConnectionTimeout(20000);
        try (var pool = new HikariDataSource(config)) {
            try (var connection = pool.getConnection(); var statement = connection.createStatement()) {
                statement.execute("create schema if not exists hear_tx_bench");
                statement.execute("create table if not exists hear_tx_bench.diary (id uuid primary key, content text not null)");
                statement.execute("insert into hear_tx_bench.diary values ('" + DIARY_ID + "','fixture') on conflict do nothing");
            }
            var factory = new LocalContainerEntityManagerFactoryBean();
            factory.setDataSource(new MeasuredDataSource(pool));
            factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
            factory.setManagedTypes(PersistenceManagedTypes.of(BenchDiary.class.getName()));
            factory.setJpaPropertyMap(Map.of("hibernate.hbm2ddl.auto", "validate", "hibernate.connection.handling_mode", "DELAYED_ACQUISITION_AND_RELEASE_AFTER_TRANSACTION"));
            factory.afterPropertiesSet(); emf = factory.getObject();
            var manager = new JpaTransactionManager(emf);
            write = new TransactionTemplate(manager); read = new TransactionTemplate(manager); read.setReadOnly(true);
            var httpExecutor = Executors.newFixedThreadPool(CONCURRENCY);
            var server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
            server.setExecutor(httpExecutor);
            server.createContext("/", exchange -> {
                AI_CALLS.incrementAndGet();
                boolean success = exchange.getRequestURI().getPath().equals("/success");
                try {
                    exchange.getRequestBody().readAllBytes();
                    if (!success) Thread.sleep(DELAY_MS);
                    byte[] body = (success ? "{\"aiComment\":\"ok\"}" : "{\"error\":\"injected_failure\"}").getBytes();
                    exchange.sendResponseHeaders(success ? 200 : 500, body.length);
                    exchange.getResponseBody().write(body);
                } catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
                finally { exchange.close(); }
            });
            server.start(); aiUrl = URI.create("http://127.0.0.1:" + server.getAddress().getPort());
            var workers = Executors.newFixedThreadPool(CONCURRENCY);
            try {
                for (String mode : List.of("inside", "split")) {
                    execute(mode, -1, 0, true);
                    read.executeWithoutResult(s -> check(EntityManagerFactoryUtils.getTransactionalEntityManager(emf)
                        .find(BenchDiary.class, DIARY_ID).content.equals("saved"), "Successful comment was not persisted"));
                    write.executeWithoutResult(s -> EntityManagerFactoryUtils.getTransactionalEntityManager(emf).find(BenchDiary.class, DIARY_ID).content = "fixture");
                    execute(mode, -1, 1, false);
                    read.executeWithoutResult(s -> load());
                }
                if (Arrays.asList(args).contains("--verify-only")) {
                    System.out.println("SELF-CHECKS PASSED: successful writes persisted, failure unchanged, no leaks, AI boundary, p95");
                    return;
                }
                LEASES.clear(); OPEN.clear(); AI_CALLS.set(0);
                for (int round = 1; round <= ROUNDS; round++) {
                    for (String mode : round % 2 == 1 ? List.of("inside", "split") : List.of("split", "inside")) {
                        final int iteration = round;
                        CountDownLatch start = new CountDownLatch(1);
                        List<Future<Result>> futures = new ArrayList<>();
                        for (int i = 0; i < REQUESTS; i++) {
                            final int index = i;
                            futures.add(workers.submit(() -> { start.await(); return execute(mode, iteration, index, false); }));
                        }
                        start.countDown();
                        for (var future : futures) RESULTS.add(future.get());
                        read.executeWithoutResult(s -> load());
                        System.out.println("ROUND " + summary(mode, round));
                    }
                }
                check(AI_CALLS.get() == REQUESTS * ROUNDS * 2, "Unexpected retries or missing AI calls");
                check(LEASES.size() == RESULTS.size(), "Expected exactly one lease per failed request");
                Path output = Path.of("results"); Files.createDirectories(output);
                var csv = new ArrayList<String>(); csv.add("request_id,wait_ms,hold_ms");
                for (var l : LEASES) csv.add(String.format(Locale.ROOT, "%s,%.6f,%.6f", l.requestId, l.waitMs, l.holdMs));
                Files.write(output.resolve("leases.csv"), csv);
                csv = new ArrayList<>(); csv.add("mode,round,request_id,elapsed_ms,open_connections_at_ai,transaction_active_at_ai,status");
                for (var r : RESULTS) csv.add(String.format(Locale.ROOT, "%s,%d,%s,%.6f,%d,%s,%d", r.mode,r.round,r.id,r.elapsedMs,r.aiOpen,r.aiTransaction,r.status));
                Files.write(output.resolve("requests.csv"), csv);
                csv = new ArrayList<>(); csv.add("mode,round,requests,leases,hold_mean_ms,hold_p95_ms,wait_p95_ms,request_p95_ms");
                for (int round = 0; round <= ROUNDS; round++) for (String mode : List.of("inside", "split")) csv.add(summary(mode, round));
                Files.write(output.resolve("summary.csv"), csv);
                System.out.println("TOTAL " + summary("inside", 0)); System.out.println("TOTAL " + summary("split", 0));
                System.out.println("SELF-CHECKS PASSED: success writes, failure unchanged, no leaks, AI boundary, HTTP count, lease count, p95");
            } finally { workers.shutdownNow(); server.stop(0); httpExecutor.shutdownNow(); factory.destroy(); }
        }
    }
}

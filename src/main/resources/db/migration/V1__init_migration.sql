-- 1. 기초 테이블 생성 (사용자 및 설정)
CREATE TABLE users (
                       id                  UUID         NOT NULL,
                       email               VARCHAR(100) NOT NULL,
                       password            VARCHAR(100) NOT NULL,
                       role                VARCHAR(8)   NOT NULL,
                       status              VARCHAR(16)  NOT NULL,
                       is_email_subscribed BOOLEAN      NOT NULL,
                       created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                       updated_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                       CONSTRAINT pk_users PRIMARY KEY (id),
                       CONSTRAINT uc_users_email UNIQUE (email)
);

CREATE TABLE user_profiles (
                               user_id           UUID         NOT NULL,
                               nickname          VARCHAR(20)  NOT NULL,
                               profile_image_url VARCHAR(255) NOT NULL,
                               created_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                               updated_at        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                               CONSTRAINT pk_user_profiles PRIMARY KEY (user_id)
);

CREATE TABLE user_stats (
                            user_id         UUID    NOT NULL,
                            current_streak  INTEGER NOT NULL DEFAULT 0,
                            total_diaries   INTEGER NOT NULL DEFAULT 0,
                            max_streak      INTEGER NOT NULL DEFAULT 0,
                            last_written_at DATE,
                            created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                            updated_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                            CONSTRAINT pk_user_stats PRIMARY KEY (user_id)
);

-- 2. 통계 및 캘린더 관련
CREATE TABLE monthly_statistics (
                                    user_id              UUID             NOT NULL,
                                    target_year_month    VARCHAR(255)     NOT NULL,
                                    diary_count          INTEGER          NOT NULL DEFAULT 0,
                                    photo_count          INTEGER          NOT NULL DEFAULT 0,
                                    writing_rate         DOUBLE PRECISION NOT NULL DEFAULT 0.0,
                                    ai_report_content    VARCHAR(1000),
                                    emotion_distribution JSONB            NOT NULL,
                                    created_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                    updated_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                    CONSTRAINT pk_monthly_statistics PRIMARY KEY (user_id, target_year_month),
                                    CONSTRAINT ck_monthly_statistics_target_year_month
                                        CHECK (target_year_month ~ '^\d{4}-(0[1-9]|1[0-2])$')
);

CREATE TABLE calendar (
                          calendar_date DATE         NOT NULL,
                          day_of_week   VARCHAR(255) NOT NULL,
                          is_holiday    BOOLEAN      NOT NULL DEFAULT false,
                          CONSTRAINT pk_calendar PRIMARY KEY (calendar_date)
);

CREATE TABLE user_calendars (
                                calendar_date DATE      NOT NULL,
                                user_id       UUID      NOT NULL,
                                diary_id      UUID,
                                has_diary     BOOLEAN   NOT NULL DEFAULT false,
                                emotion       VARCHAR(8),
                                created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                updated_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                CONSTRAINT pk_user_calendars PRIMARY KEY (calendar_date, user_id)
);

-- 3. 채팅 및 메시지
CREATE TABLE chats (
                       id         UUID        NOT NULL,
                       user_id    UUID        NOT NULL,
                       status     VARCHAR(16) NOT NULL,
                       created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                       updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                       CONSTRAINT pk_chats PRIMARY KEY (id)
);

CREATE TABLE messages (
                          id           UUID          NOT NULL,
                          chat_id      UUID          NOT NULL,
                          sender       VARCHAR(8)    NOT NULL,
                          message      VARCHAR(1000) NOT NULL,
                          message_type VARCHAR(8)    NOT NULL,
                          voice_url    VARCHAR(512),
                          duration     BIGINT,
                          created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                          updated_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                          CONSTRAINT pk_messages PRIMARY KEY (id),
                          CONSTRAINT FK_MESSAGES_ON_CHAT FOREIGN KEY (chat_id) REFERENCES chats (id)
);

-- 4. 일기 및 AI 관련
CREATE TABLE diaries (
                         id          UUID          NOT NULL,
                         user_id     UUID          NOT NULL,
                         content     VARCHAR(1000) NOT NULL,
                         emotion     VARCHAR(8)    NOT NULL,
                         tags        JSONB         NOT NULL,
                         source_type VARCHAR(16)   NOT NULL,
                         chat_id     UUID,
                         created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                         updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                         CONSTRAINT pk_diaries PRIMARY KEY (id),
                         CONSTRAINT uc_diaries_chat_id UNIQUE (chat_id)
);

CREATE TABLE diary_images (
                              id            UUID        NOT NULL,
                              diary_id      UUID,
                              chat_id       UUID,
                              image_url     VARCHAR(512),
                              display_order INTEGER     NOT NULL DEFAULT 0,
                              source_type   VARCHAR(16) NOT NULL,
                              status        VARCHAR(16) NOT NULL,
                              created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                              updated_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                              CONSTRAINT pk_diary_images PRIMARY KEY (id),
                              CONSTRAINT FK_DIARY_IMAGES_ON_DIARY FOREIGN KEY (diary_id) REFERENCES diaries (id)
);

CREATE TABLE diary_ai_comments (
                                   diary_id   UUID         NOT NULL,
                                   content    VARCHAR(1000),
                                   status     VARCHAR(16)  NOT NULL,
                                   created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                   updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                   CONSTRAINT pk_diary_ai_comments PRIMARY KEY (diary_id),
                                   CONSTRAINT FK_DIARY_AI_COMMENTS_ON_DIARY FOREIGN KEY (diary_id) REFERENCES diaries (id)
);

CREATE TABLE ai_image_tasks (
                                id         UUID        NOT NULL,
                                chat_id    UUID,
                                diary_id   UUID,
                                status     VARCHAR(16) NOT NULL,
                                created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                CONSTRAINT pk_ai_image_tasks PRIMARY KEY (id)
);

-- 5. 스티커 관련 (추가된 엔티티 반영)
CREATE TABLE stickers (
                          id         UUID         NOT NULL,
                          user_id    UUID         NOT NULL,
                          diary_id   UUID,
                          status     VARCHAR(16)  NOT NULL,
                          image_url  VARCHAR(512) NOT NULL, -- JPA에서 length=16이었으나 URL 특성상 확장함
                          keyword    VARCHAR(255),
                          created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                          updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                          CONSTRAINT pk_stickers PRIMARY KEY (id)
);

CREATE TABLE diary_stickers (
                                diary_id   UUID             NOT NULL,
                                sticker_id UUID             NOT NULL,
                                position_x DOUBLE PRECISION NOT NULL DEFAULT 0.0,
                                position_y DOUBLE PRECISION NOT NULL DEFAULT 0.0,
                                rotation   DOUBLE PRECISION NOT NULL DEFAULT 0.0,
                                scale      DOUBLE PRECISION NOT NULL DEFAULT 1.0,
                                created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                CONSTRAINT pk_diary_stickers PRIMARY KEY (diary_id, sticker_id),
                                CONSTRAINT fk_diary_stickers_on_diary FOREIGN KEY (diary_id) REFERENCES diaries (id),
                                CONSTRAINT fk_diary_stickers_on_sticker FOREIGN KEY (sticker_id) REFERENCES stickers (id)
);

-- 6. 기타 및 인덱스
CREATE TABLE pending_uploads (
                                 id           UUID        NOT NULL,
                                 user_id      UUID        NOT NULL,
                                 s3_key       VARCHAR(512) NOT NULL,
                                 service_type VARCHAR(32) NOT NULL,
                                 expired_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                 created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                 updated_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                                 CONSTRAINT pk_pending_uploads PRIMARY KEY (id)
);

CREATE INDEX idx_user_diary_created ON diaries (user_id, created_at DESC);
CREATE INDEX idx_diary_images_chat_id ON diary_images (chat_id);
CREATE INDEX idx_pending_uploads_expired_at ON pending_uploads (expired_at);
CREATE INDEX idx_pending_uploads_s3_key ON pending_uploads (s3_key);
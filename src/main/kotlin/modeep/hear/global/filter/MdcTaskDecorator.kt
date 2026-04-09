package modeep.hear.global.filter

import org.slf4j.MDC
import org.springframework.core.task.TaskDecorator

class MdcTaskDecorator : TaskDecorator {
    override fun decorate(runnable: Runnable): Runnable {
        // 현재 스레드(부모)의 MDC 데이터를 복사
        val contextMap = MDC.getCopyOfContextMap()
        return Runnable {
            try {
                // 새 스레드(자식)에 데이터 주입
                if (contextMap != null) MDC.setContextMap(contextMap)
                runnable.run()
            } finally {
                MDC.clear()
            }
        }
    }
}

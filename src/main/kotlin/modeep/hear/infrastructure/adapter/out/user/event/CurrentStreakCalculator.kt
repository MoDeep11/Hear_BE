package modeep.hear.infrastructure.adapter.out.user.event

import java.time.LocalDate

object CurrentStreakCalculator {
    fun calculate(dates: List<LocalDate>, now: LocalDate): Int {
        if (dates.isEmpty()) return 0

        // 1. 중복 제거 및 내림차순(최신순) 정렬
        val sortedDates = dates.distinct().sortedDescending()
        val firstDate = sortedDates.first()

        // 2. 가장 최근 일기가 오늘이나 어제가 아니면 스트릭은 이미 깨진 상태
        if (!isToday(firstDate, now) && !isYesterday(firstDate, now)) {
            return 0
        }

        // 3. 연속성 확인 루프
        var currentStreak = 1
        for (i in 0 until sortedDates.size - 1) {
            val current = sortedDates[i]
            val next = sortedDates[i + 1]

            // 하루 차이가 나면 스트릭 유지, 그 이상 벌어지면 종료
            if (current.minusDays(1) == next) {
                currentStreak++
            } else {
                break
            }
        }

        return currentStreak
    }

    private fun isToday(target: LocalDate, now: LocalDate) = target == now
    private fun isYesterday(target: LocalDate, now: LocalDate) = target == now.minusDays(1)
}

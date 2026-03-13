package modeep.hear.global.util.emotion

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class EmotionUtilsTest {

    @Test
    @DisplayName("정확히 나누어 떨어지는 경우 총합이 100.0이어야 한다")
    fun calculatePercentages_Basic() {
        val counts = mapOf("HAPPY" to 1, "SAD" to 1, "NEUTRAL" to 2) // 25%, 25%, 50%
        val result = EmotionUtils.calculatePercentages(counts)

        assertThat(result["HAPPY"]).isEqualTo(25.0)
        assertThat(result["SAD"]).isEqualTo(25.0)
        assertThat(result["NEUTRAL"]).isEqualTo(50.0)
        assertThat(result.values.sum()).isEqualTo(100.0)
    }

    @Test
    @DisplayName("소수점이 무한히 발생하는 경우(33.333...)에도 총합은 반드시 100.0이어야 한다")
    fun calculatePercentages_Rounding() {
        val counts = mapOf("A" to 1, "B" to 1, "C" to 1) // 각각 33.3...
        val result = EmotionUtils.calculatePercentages(counts)

        // 로직에 따라 가장 큰 값(혹은 첫번째 max)에 diff가 더해짐
        // 33.3 + 33.3 + 33.3 = 99.9 -> diff 0.1이 maxKey에 추가됨
        assertThat(result.values.sum()).isEqualTo(100.0)
        assertThat(result.values).contains(33.4, 33.3)
    }

    @Test
    @DisplayName("데이터가 하나도 없는(total 0) 경우 모든 값을 0.0으로 반환해야 한다")
    fun calculatePercentages_ZeroTotal() {
        val counts = mapOf("HAPPY" to 0, "SAD" to 0)
        val result = EmotionUtils.calculatePercentages(counts)

        assertThat(result.values).allMatch { it == 0.0 }
    }

    @Test
    @DisplayName("매우 큰 숫자의 데이터도 정상적으로 퍼센트를 계산한다")
    fun calculatePercentages_LargeNumbers() {
        val counts = mapOf("HAPPY" to 123456, "SAD" to 654321)
        val result = EmotionUtils.calculatePercentages(counts)

        assertThat(result.values.sum()).isEqualTo(100.0)
    }

    @Test
    @DisplayName("diff가 음수인 경우(합계가 100을 초과할 때)에도 100.0으로 보정해야 한다")
    fun calculatePercentages_NegativeDiff() {
        // 반올림으로 인해 100.1이 되는 케이스를 가정 (예: 66.66 + 33.34 등)
        val counts = mapOf("MAX" to 2, "MIN" to 1)
        val result = EmotionUtils.calculatePercentages(counts)
        assertThat(result.values.sum()).isEqualTo(100.0)
    }
}

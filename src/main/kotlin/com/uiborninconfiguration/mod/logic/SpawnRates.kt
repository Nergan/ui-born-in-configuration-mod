package com.uiborninconfiguration.mod.logic

/**
 * Чистая арифметика множителя спавна. Юнит-тесты гоняют этот объект без Minecraft.
 *
 * 1.0 — как в Born in Chaos. Меньше единицы прореживает попытку, больше — добавляет копии.
 * Общий множитель и множитель конкретного моба перемножаются и зажимаются в 0..8.
 */
object SpawnRates {
    const val MAX: Double = 8.0

    fun combined(global: Double, specific: Double): Double =
        (global * specific).coerceIn(0.0, MAX)

    /** [roll] — случайное число из [0, 1). */
    fun keep(multiplier: Double, roll: Double): Boolean {
        if (multiplier >= 1.0) return true
        if (multiplier <= 0.0) return false
        return roll < multiplier
    }

    /** Сколько дополнительных копий создать сверх уже прошедшего спавна. */
    fun extraCount(multiplier: Double, roll: Double): Int {
        if (multiplier <= 1.0) return 0
        val extra = multiplier - 1.0
        val whole = extra.toInt()
        val fraction = extra - whole
        return whole + if (roll < fraction) 1 else 0
    }
}

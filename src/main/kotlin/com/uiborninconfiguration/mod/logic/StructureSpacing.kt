package com.uiborninconfiguration.mod.logic

import kotlin.math.roundToInt

/**
 * Частота 1.0 оставляет ванильный spacing как есть.
 * Больше единицы — структуры чаще (меньше spacing), меньше — реже.
 * spacing всегда строго больше separation, иначе ванильный spread не определён.
 */
object StructureSpacing {
    fun scaled(spacing: Int, separation: Int, frequency: Double): Int {
        if (frequency == 1.0) return spacing
        if (frequency <= 0.0) return spacing
        val raw = (spacing / frequency).roundToInt().coerceAtLeast(1)
        return maxOf(raw, separation + 1)
    }

    fun groupOf(path: String): String = when {
        path.startsWith("grave") -> "graves"
        path.startsWith("clown_caravan") -> "clown_caravan"
        path.startsWith("dark_tower") -> "dark_tower"
        path.startsWith("observation_tower") -> "observation_tower"
        else -> path
    }
}

package com.uiborninconfiguration.mod

import com.uiborninconfiguration.mod.logic.SpawnRates
import com.uiborninconfiguration.mod.logic.StructureSpacing
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class RatesTest {
    @Test
    fun keepUsesTheRollBelowOne() {
        assertFalse(SpawnRates.keep(0.0, 0.0))
        assertTrue(SpawnRates.keep(1.0, 0.99))
        assertTrue(SpawnRates.keep(0.5, 0.49))
        assertFalse(SpawnRates.keep(0.5, 0.5))
    }

    @Test
    fun extraCountAddsWholeAndFractionalCopies() {
        assertEquals(0, SpawnRates.extraCount(1.0, 0.0))
        assertEquals(1, SpawnRates.extraCount(2.0, 0.99))
        assertEquals(2, SpawnRates.extraCount(2.5, 0.1))
        assertEquals(1, SpawnRates.extraCount(2.5, 0.5))
    }

    @Test
    fun combinedClampsTheProduct() {
        assertEquals(4.0, SpawnRates.combined(2.0, 2.0))
        assertEquals(8.0, SpawnRates.combined(8.0, 8.0))
        assertEquals(0.0, SpawnRates.combined(0.0, 4.0))
    }

    @Test
    fun scaledSpacingKeepsVanillaAtOne() {
        assertEquals(75, StructureSpacing.scaled(75, 14, 1.0))
        assertEquals(38, StructureSpacing.scaled(75, 14, 2.0))
        assertEquals(150, StructureSpacing.scaled(75, 14, 0.5))
        assertEquals(9, StructureSpacing.scaled(10, 8, 8.0))
    }

    @Test
    fun structureGroupsShareAPrefix() {
        assertEquals("graves", StructureSpacing.groupOf("grave_orion"))
        assertEquals("graves", StructureSpacing.groupOf("gravecarrionexe"))
        assertEquals("farm", StructureSpacing.groupOf("farm"))
        assertEquals("dark_tower", StructureSpacing.groupOf("dark_tower_taiga"))
        assertEquals("observation_tower", StructureSpacing.groupOf("observation_tower_plains"))
        assertEquals("clown_caravan", StructureSpacing.groupOf("clown_caravan_savanna"))
    }
}

package com.uiborninconfiguration.mod.world

import com.uiborninconfiguration.mod.UiBornInConfigurationMod
import com.uiborninconfiguration.mod.config.ServerConfig
import com.uiborninconfiguration.mod.logic.SpawnRates
import com.uiborninconfiguration.mod.logic.StructureSpacing
import net.minecraft.core.RegistryAccess
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement
import java.util.IdentityHashMap

/**
 * Соответствие экземпляра [StructurePlacement] и id набора структур.
 * Карта строится только из серверного [RegistryAccess]: на интегрированном сервере
 * клиентский пакет тегов живёт в той же JVM и его экземпляры для генерации не годятся.
 */
object StructurePlacementIndex {
    @Volatile
    private var index: Map<StructurePlacement, ResourceLocation> = emptyMap()

    fun rebuild(access: RegistryAccess) {
        val next = IdentityHashMap<StructurePlacement, ResourceLocation>()
        val registry = access.registryOrThrow(Registries.STRUCTURE_SET)
        registry.holders().forEach { holder ->
            next[holder.value().placement()] = holder.key().location()
        }
        index = next
        UiBornInConfigurationMod.LOGGER.debug("Indexed {} structure placements", next.size)
    }

    @JvmStatic
    fun idOf(placement: StructurePlacement): ResourceLocation? = index[placement]
}

/** Множитель для пути набора структур. 1.0, пока серверный конфиг ещё не загружен. */
object StructureRates {
    @JvmStatic
    fun frequency(path: String): Double {
        if (!ServerConfig.SPEC.isLoaded) return 1.0
        val global = ServerConfig.CONFIG.structureFrequency.get()
        val group = StructureSpacing.groupOf(path)
        val specific = ServerConfig.CONFIG.structureGroups[group]?.get() ?: 1.0
        return SpawnRates.combined(global, specific)
    }
}

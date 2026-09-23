package com.uiborninconfiguration.mod.event

import com.uiborninconfiguration.mod.ChaosIds
import com.uiborninconfiguration.mod.config.ServerConfig
import com.uiborninconfiguration.mod.logic.SpawnRates
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.WorldGenRegion
import net.minecraft.world.entity.Mob
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.level.ServerLevelAccessor
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.neoforge.event.EventHooks
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent

/**
 * Прореживает и добавляет копии мобов Born in Chaos.
 * Яйца, команды, спавнеры и мобы из структур не масштабируются.
 * Лишние копии создаются как [MobSpawnType.MOB_SUMMONED], чтобы не умножаться повторно.
 */
object ChaosSpawnScaler {
    private val spawningExtra = ThreadLocal.withInitial { false }

    private val scaledTypes = setOf(
        MobSpawnType.NATURAL,
        MobSpawnType.CHUNK_GENERATION,
        MobSpawnType.PATROL,
        MobSpawnType.EVENT,
    )

    @SubscribeEvent
    fun onFinalizeSpawn(event: FinalizeSpawnEvent) {
        if (spawningExtra.get()) return
        if (!ServerConfig.SPEC.isLoaded) return
        if (event.spawnType !in scaledTypes) return
        val mob = event.entity
        val id = BuiltInRegistries.ENTITY_TYPE.getKey(mob.type) ?: return
        if (id.namespace != ChaosIds.NAMESPACE) return

        val specific = ServerConfig.CONFIG.mobRates[id.path]?.get() ?: 1.0
        val multiplier = SpawnRates.combined(ServerConfig.CONFIG.spawnMultiplier.get(), specific)
        if (!SpawnRates.keep(multiplier, mob.random.nextDouble())) {
            event.setSpawnCancelled(true)
            return
        }

        val extras = SpawnRates.extraCount(multiplier, mob.random.nextDouble())
        if (extras <= 0) return
        val serverLevel = serverLevel(event.level) ?: return
        spawningExtra.set(true)
        try {
            repeat(extras) {
                val copy = mob.type.create(serverLevel) as? Mob ?: return@repeat
                copy.moveTo(mob.x, mob.y, mob.z, mob.yRot, mob.xRot)
                EventHooks.finalizeMobSpawn(copy, event.level, event.difficulty, MobSpawnType.MOB_SUMMONED, event.spawnData)
                event.level.addFreshEntity(copy)
            }
        } finally {
            spawningExtra.set(false)
        }
    }

    private fun serverLevel(level: ServerLevelAccessor): ServerLevel? = when (level) {
        is ServerLevel -> level
        // getLevel() помечен устаревшим, другого доступа к ServerLevel у региона генерации нет.
        is WorldGenRegion -> @Suppress("DEPRECATION") level.level
        else -> null
    }
}

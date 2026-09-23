package com.uiborninconfiguration.mod.config

import com.uiborninconfiguration.mod.logic.ChaosCatalog
import net.neoforged.neoforge.common.ModConfigSpec

/**
 * Конфиг типа SERVER: файл мира, на выделенном сервере его задаёт сервер.
 * Экран NeoForge на чужом сервере показывает значения только для чтения.
 *
 * 1.0 — поведение Born in Chaos. Уже сгенерированные чанки структуры не переставляет.
 */
class ServerConfig(builder: ModConfigSpec.Builder) {
    val spawnMultiplier: ModConfigSpec.DoubleValue
    val mobRates: Map<String, ModConfigSpec.DoubleValue>
    val structureFrequency: ModConfigSpec.DoubleValue
    val structureGroups: Map<String, ModConfigSpec.DoubleValue>

    init {
        builder
            .comment(
                "How often Born in Chaos mobs appear. 1.0 is unchanged, 0 disables scaled spawns, 2 doubles them.",
                "Как часто появляются мобы Born in Chaos. 1.0 — как в моде, 0 — отключает учитываемый спавн, 2 — удваивает.",
            )
            .translation("$PREFIX.spawning")
            .push("spawning")

        spawnMultiplier = builder
            .comment(
                "Multiplier for natural, chunk, patrol and event spawns of every Born in Chaos mob.",
                "Spawn eggs, commands, spawners and structure-placed mobs are left alone.",
                "Множитель естественного спавна, спавна при генерации чанка, патрулей и событий для всех мобов Born in Chaos.",
                "Яйца призыва, команды, спавнеры и мобы, уже прописанные в структуре, не трогаются.",
            )
            .translation("$PREFIX.spawning.spawn_multiplier")
            .defineInRange("spawn_multiplier", 1.0, 0.0, 8.0)

        val mobs = linkedMapOf<String, ModConfigSpec.DoubleValue>()
        builder
            .comment(
                "Extra multiplier for one mob. Combined with the global multiplier, then clamped to 0..8.",
                "Дополнительный множитель одного моба. Перемножается с общим и зажимается в 0..8.",
            )
            .translation("$PREFIX.spawning.mobs")
            .push("mobs")
        for (mob in ChaosCatalog.mobs) {
            mobs[mob.id] = builder
                .translation("$PREFIX.spawning.mobs.${mob.id}")
                .defineInRange(mob.id, 1.0, 0.0, 8.0)
        }
        builder.pop()
        mobRates = mobs
        builder.pop()

        builder
            .comment(
                "How often Born in Chaos structure sets are placed in new chunks. 1.0 keeps the datapack spacing.",
                "Как часто наборы структур Born in Chaos встают в новые чанки. 1.0 оставляет spacing датапака.",
            )
            .translation("$PREFIX.structures")
            .push("structures")

        structureFrequency = builder
            .comment(
                "Global structure frequency. 2 places them about twice as often, 0.5 about half as often, 0 disables new attempts.",
                "Общая частота структур. 2 — примерно вдвое чаще, 0.5 — вдвое реже, 0 — новые попытки выключены.",
            )
            .translation("$PREFIX.structures.structure_frequency")
            .defineInRange("structure_frequency", 1.0, 0.0, 8.0)

        val groups = linkedMapOf<String, ModConfigSpec.DoubleValue>()
        builder
            .comment(
                "Extra frequency for one structure group. Unknown future sets still follow the global value.",
                "Дополнительная частота одной группы структур. Будущие наборы без своей группы всё равно слушают общую частоту.",
            )
            .translation("$PREFIX.structures.groups")
            .push("groups")
        for (group in ChaosCatalog.structureGroups) {
            groups[group.id] = builder
                .translation("$PREFIX.structures.groups.${group.id}")
                .defineInRange(group.id, 1.0, 0.0, 8.0)
        }
        builder.pop()
        structureGroups = groups
        builder.pop()
    }

    companion object {
        private const val PREFIX = "uiborninconfiguration.configuration"

        val SPEC: ModConfigSpec
        val CONFIG: ServerConfig

        init {
            val pair = ModConfigSpec.Builder().configure(::ServerConfig)
            CONFIG = pair.getLeft()
            SPEC = pair.getRight()
        }
    }
}

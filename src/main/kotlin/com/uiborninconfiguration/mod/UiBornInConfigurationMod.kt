package com.uiborninconfiguration.mod

import com.uiborninconfiguration.mod.config.ServerConfig
import com.uiborninconfiguration.mod.event.ChaosSpawnScaler
import com.uiborninconfiguration.mod.event.ModSetup
import com.uiborninconfiguration.mod.event.StructureIndexEvents
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.neoforge.common.NeoForge
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Обычный класс, не Kotlin object: NeoForge передаёт [ModContainer] в конструктор,
 * а у object параметризованного конструктора нет. Kotlin for Forge остаётся
 * обязательной зависимостью, загрузчик в mods.toml — javafml.
 */
@Mod(UiBornInConfigurationMod.MOD_ID)
class UiBornInConfigurationMod(modEventBus: IEventBus, modContainer: ModContainer) {
    companion object {
        const val MOD_ID = ChaosIds.OURS

        @JvmField
        val LOGGER: Logger = LogManager.getLogger(MOD_ID)
    }

    init {
        LOGGER.info("UI for Born in Configuration ({})", MOD_ID)
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC)
        ModSetup.init(modEventBus, modContainer)
        NeoForge.EVENT_BUS.register(ChaosSpawnScaler)
        NeoForge.EVENT_BUS.register(StructureIndexEvents)
    }
}

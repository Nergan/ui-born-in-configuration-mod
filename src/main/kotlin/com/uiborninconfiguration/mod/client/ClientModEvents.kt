package com.uiborninconfiguration.mod.client

import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent

/**
 * Кнопка Config ставится в конце загрузки, когда чужие моды уже успели
 * зарегистрировать свой экран. Миксин к этому моменту его запомнил.
 *
 * Серверный конфиг на титульном экране ещё не загружен, а на чужом сервере
 * NeoForge показывает его только для чтения.
 */
object ClientModEvents {
    fun init(modBus: IEventBus, modContainer: ModContainer) {
        modBus.addListener { _: FMLLoadCompleteEvent ->
            ConfigScreenRegistration.install(modContainer)
        }
    }
}

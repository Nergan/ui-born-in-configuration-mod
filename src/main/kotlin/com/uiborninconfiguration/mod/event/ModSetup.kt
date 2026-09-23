package com.uiborninconfiguration.mod.event

import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.loading.FMLEnvironment

/**
 * Клиентские классы подключаются только на клиенте: на выделенном сервере
 * их нет в classpath.
 */
object ModSetup {
    fun init(modBus: IEventBus, modContainer: ModContainer) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            com.uiborninconfiguration.mod.client.ClientModEvents.init(modBus, modContainer)
        }
    }
}

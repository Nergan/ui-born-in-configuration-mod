package com.uiborninconfiguration.mod.client

import com.uiborninconfiguration.mod.ChaosIds
import com.uiborninconfiguration.mod.UiBornInConfigurationMod
import net.neoforged.fml.ModContainer
import net.neoforged.fml.ModList
import net.neoforged.neoforge.client.gui.IConfigScreenFactory
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Supplier

/**
 * Чужой экран настроек не затирается: если Born in Chaos или Born in Configuration
 * регистрируют свой [IConfigScreenFactory], миксин его запоминает и отменяет запись,
 * а наш общий экран потом ставится на все три мода. Их экран остаётся отдельной кнопкой.
 */
object ConfigScreenRegistration {
    @JvmField
    var installing: Boolean = false

    private val foreign = ConcurrentHashMap<String, () -> IConfigScreenFactory>()

    @JvmStatic
    fun isHost(modId: String): Boolean = modId in ChaosIds.HOSTS

    @JvmStatic
    fun remember(modId: String, factory: IConfigScreenFactory) {
        foreign[modId] = { factory }
        UiBornInConfigurationMod.LOGGER.info("Kept existing config screen for {}", modId)
    }

    @JvmStatic
    fun rememberSupplier(modId: String, supplier: Supplier<IConfigScreenFactory>) {
        foreign[modId] = { supplier.get() }
        UiBornInConfigurationMod.LOGGER.info("Kept existing config screen supplier for {}", modId)
    }

    fun foreignFactory(modId: String): IConfigScreenFactory? = foreign[modId]?.invoke()

    fun install(our: ModContainer) {
        val factory = IConfigScreenFactory { _, parent -> SharedConfigScreen(parent) }
        installing = true
        try {
            our.registerExtensionPoint(IConfigScreenFactory::class.java, factory)
            for (modId in ChaosIds.HOSTS) {
                val container = ModList.get().getModContainerById(modId).orElse(null) ?: continue
                container.registerExtensionPoint(IConfigScreenFactory::class.java, factory)
                UiBornInConfigurationMod.LOGGER.info("Config button for {} opens the shared screen", modId)
            }
        } finally {
            installing = false
        }
    }
}

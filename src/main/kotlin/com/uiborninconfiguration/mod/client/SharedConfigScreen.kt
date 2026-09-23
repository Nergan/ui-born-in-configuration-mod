package com.uiborninconfiguration.mod.client

import com.uiborninconfiguration.mod.ChaosIds
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import net.neoforged.fml.ModList
import net.neoforged.fml.config.ModConfig
import net.neoforged.fml.config.ModConfigs
import net.neoforged.neoforge.client.gui.ConfigurationScreen

/**
 * Один экран на три кнопки Config. Настройки Born in Configuration, частота спавна
 * и структур, а если у Born in Chaos появится свой конфиг или свой экран — они
 * добавляются сюда отдельными кнопками.
 */
class SharedConfigScreen(private val parent: Screen) : Screen(TITLE) {
    override fun init() {
        val buttonWidth = 310.coerceAtMost(width - 40)
        val x = (width - buttonWidth) / 2
        var y = 48

        fun add(label: Component, action: () -> Unit) {
            addRenderableWidget(
                Button.builder(label) { action() }
                    .bounds(x, y, buttonWidth, 20)
                    .build(),
            )
            y += 24
        }

        ModList.get().getModContainerById(ChaosIds.CONFIG).ifPresent { container ->
            add(Component.translatable("uiborninconfiguration.screen.bic")) {
                minecraft?.setScreen(ConfigurationScreen(container, this))
            }
        }
        ModList.get().getModContainerById(ChaosIds.OURS).ifPresent { container ->
            add(Component.translatable("uiborninconfiguration.screen.rates")) {
                minecraft?.setScreen(ConfigurationScreen(container, this))
            }
        }
        ModList.get().getModContainerById(ChaosIds.CHAOS).ifPresent { container ->
            if (hasConfigs(ChaosIds.CHAOS)) {
                add(Component.translatable("uiborninconfiguration.screen.chaos")) {
                    minecraft?.setScreen(ConfigurationScreen(container, this))
                }
            }
        }
        for (modId in ChaosIds.HOSTS) {
            val factory = ConfigScreenRegistration.foreignFactory(modId) ?: continue
            val container = ModList.get().getModContainerById(modId).orElse(null) ?: continue
            val name = container.modInfo.displayName
            add(Component.translatable("uiborninconfiguration.screen.own", name)) {
                minecraft?.setScreen(factory.createScreen(container, this))
            }
        }

        addRenderableWidget(
            Button.builder(CommonComponents.GUI_DONE) { onClose() }
                .bounds(x, height - 28, buttonWidth, 20)
                .build(),
        )
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        renderBackground(graphics, mouseX, mouseY, partialTick)
        super.render(graphics, mouseX, mouseY, partialTick)
        graphics.drawCenteredString(font, title, width / 2, 12, 0xFFFFFF)
        graphics.drawCenteredString(font, SUBTITLE, width / 2, 26, 0xA0A0A0)
    }

    override fun onClose() {
        minecraft?.setScreen(parent)
    }

    companion object {
        private val TITLE: Component = Component.translatable("uiborninconfiguration.screen.title")
        private val SUBTITLE: Component = Component.translatable("uiborninconfiguration.screen.subtitle")

        private fun hasConfigs(modId: String): Boolean =
            ModConfig.Type.entries.any { type ->
                ModConfigs.getConfigSet(type).any { it.modId == modId }
            }
    }
}

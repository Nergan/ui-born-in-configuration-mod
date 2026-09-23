package com.uiborninconfiguration.mod.mixin;

import com.uiborninconfiguration.mod.client.ConfigScreenRegistration;
import java.util.function.Supplier;
import net.neoforged.fml.IExtensionPoint;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Не даёт Born in Chaos и Born in Configuration затереть наш общий экран
 * и не даёт нам затереть их экран: чужая фабрика запоминается, запись отменяется.
 * Пока [ConfigScreenRegistration#installing] истинно, пишем уже мы.
 */
@Mixin(ModContainer.class)
public abstract class ConfigScreenRegistrationMixin {
    @Inject(method = "registerExtensionPoint(Ljava/lang/Class;Lnet/neoforged/fml/IExtensionPoint;)V", at = @At("HEAD"), cancellable = true)
    private <T extends IExtensionPoint> void uibic$keepInstance(Class<T> point, T extension, CallbackInfo ci) {
        if (ConfigScreenRegistration.installing) {
            return;
        }
        if (!IConfigScreenFactory.class.equals(point)) {
            return;
        }
        String modId = ((ModContainer) (Object) this).getModId();
        if (!ConfigScreenRegistration.isHost(modId)) {
            return;
        }
        ConfigScreenRegistration.remember(modId, (IConfigScreenFactory) extension);
        ci.cancel();
    }

    @Inject(method = "registerExtensionPoint(Ljava/lang/Class;Ljava/util/function/Supplier;)V", at = @At("HEAD"), cancellable = true)
    private <T extends IExtensionPoint> void uibic$keepSupplier(Class<T> point, Supplier<T> extension, CallbackInfo ci) {
        if (ConfigScreenRegistration.installing) {
            return;
        }
        if (!IConfigScreenFactory.class.equals(point)) {
            return;
        }
        String modId = ((ModContainer) (Object) this).getModId();
        if (!ConfigScreenRegistration.isHost(modId)) {
            return;
        }
        ConfigScreenRegistration.rememberSupplier(modId, () -> (IConfigScreenFactory) extension.get());
        ci.cancel();
    }
}

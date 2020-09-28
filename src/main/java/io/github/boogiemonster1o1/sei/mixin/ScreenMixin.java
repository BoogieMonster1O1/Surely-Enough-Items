package io.github.boogiemonster1o1.sei.mixin;

import io.github.boogiemonster1o1.sei.api.ContainerScreenEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
@Mixin(Screen.class)
public class ScreenMixin {
    @Inject(method = "mouseClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;buttonClicked(Lnet/minecraft/client/gui/widget/ButtonWidget;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void interceptButtonClicked(int mouseX, int mouseY, int button, CallbackInfo ci, int i, ButtonWidget buttonWidget) {
        //noinspection ConstantConditions
        if (((Object) this) instanceof ContainerScreen) {
            ContainerScreenEvents.BUTTON_PRESSED_SIMPLE.invoker().accept((ContainerScreen) (Object) this, buttonWidget);
        }
    }
}

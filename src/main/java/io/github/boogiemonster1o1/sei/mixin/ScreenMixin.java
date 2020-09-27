package io.github.boogiemonster1o1.sei.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.client.gui.screen.Screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
@Mixin(Screen.class)
public class ScreenMixin {
    @Inject(method = "mouseClicked", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;buttonClicked(Lnet/minecraft/client/gui/widget/ButtonWidget;)V"), locals = LocalCapture.PRINT)
    public void interceptButtonClicked(int mouseX, int mouseY, int button, CallbackInfo ci) {
        // TODO
    }
}

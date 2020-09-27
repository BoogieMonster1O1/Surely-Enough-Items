package io.github.boogiemonster1o1.sei.mixin;

import io.github.boogiemonster1o1.sei.api.ContainerEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.ingame.ContainerScreen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
@Mixin(ContainerScreen.class)
public class ContainerScreenMixin {
    @Inject(method = "init", at = @At("TAIL"))
    public void interceptInit(CallbackInfo ci) {
        ContainerEvents.INIT.invoker().accept( this.getThis());
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void interceptRender(int mouseX, int mouseY, float delta, CallbackInfo ci) {
        ContainerEvents.RENDER.invoker().accept(this.getThis(), mouseX, mouseY, delta);
    }

    private ContainerScreen getThis() {
        return (ContainerScreen)(Object) this;
    }
}

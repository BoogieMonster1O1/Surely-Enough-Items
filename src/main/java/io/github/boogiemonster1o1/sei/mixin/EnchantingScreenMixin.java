package io.github.boogiemonster1o1.sei.mixin;

import io.github.boogiemonster1o1.sei.gui.ContainerOverlay;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.client.gui.screen.ingame.EnchantingScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.container.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(EnchantingScreen.class)
public abstract class EnchantingScreenMixin extends ContainerScreen {
    private ContainerOverlay overlay;

    public EnchantingScreenMixin(Container container) {
        super(container);
    }

    @Override
    public void init() {
        super.init();
        overlay = new ContainerOverlay(this.x, this.containerHeight, width, height);
        overlay.init(this.buttons);
    }

    @Inject(method="render",at=@At("HEAD"))
    public void render(int mouseX, int mouseY, float delta, CallbackInfo ci) {
        overlay.render( this.textRenderer);
    }

    @Override
    public void buttonPressed(ButtonWidget button) {
        super.buttonPressed(button);
        overlay.buttonPressed(button);
    }

    @Inject(method="mouseClicked",at=@At("HEAD"))
    public void mouseClicked(int mouseX, int mouseY, int button, CallbackInfo ci) {
        super.mouseClicked(mouseX, mouseY, button);
        overlay.mouseClicked(mouseX, mouseY, button);
    }
}

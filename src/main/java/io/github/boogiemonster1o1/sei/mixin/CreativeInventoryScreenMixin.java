package io.github.boogiemonster1o1.sei.mixin;

import io.github.boogiemonster1o1.sei.gui.ItemListOverlay;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.container.Container;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(CreativeInventoryScreen.class)
public abstract class CreativeInventoryScreenMixin extends ContainerScreen {
    private ItemListOverlay overlay;

    public CreativeInventoryScreenMixin(Container container) {
        super(container);
    }

    @Inject(method="init",at=@At("HEAD"))
    public void init(CallbackInfo ci) {
        overlay = new ItemListOverlay(x, MathHelper.floor(containerWidth * 1.7f), width, height);
        overlay.init(buttons);
    }

    @Inject(method="render",at=@At("TAIL"))
    public void renderOverlay(CallbackInfo ci){
        overlay.render(textRenderer);
    }

    @Inject(method="buttonPressed",at=@At("HEAD"))
    protected void buttonPressed(ButtonWidget button,CallbackInfo ci) {
        super.buttonPressed(button);
        overlay.buttonPressed(button);
    }

    @Inject(method="mouseClicked",at=@At("HEAD"))
    protected void mouseClicked(int xPos, int yPos, int mouseButton,CallbackInfo ci) {
        super.mouseClicked(xPos, yPos, mouseButton);
        overlay.mouseClicked(xPos, yPos, mouseButton);
    }

    @Inject(method="keyPressed",at=@At("HEAD"))
    public void keyPressed(char character, int code,CallbackInfo ci){
        overlay.keyPressed(code);
    }
}
package io.github.boogiemonster1o1.sei.mixin;

import io.github.boogiemonster1o1.sei.gui.ItemListOverlay;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.container.Container;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends ContainerScreen {

    private ItemListOverlay overlay;

    public InventoryScreenMixin(Container container) {
        super(container);
    }

    @Inject(method="init",at=@At("HEAD"))
    public void initGui(CallbackInfo ci) {
        overlay = new ItemListOverlay(x, containerWidth, width, height);
        overlay.init(buttons);
    }

    @Inject(method="render",at=@At("TAIL"))
    public void renderOverlay(CallbackInfo ci){
        overlay.render(textRenderer);
    }

    @Override
    protected void buttonPressed(ButtonWidget button) {
        super.buttonPressed(button);
        overlay.buttonPressed(button);
    }

    @Override
    protected void mouseClicked(int xPos, int yPos, int mouseButton) {
        super.mouseClicked(xPos, yPos, mouseButton);
        overlay.mouseClicked(xPos, yPos, mouseButton);
    }
}

package io.github.boogiemonster1o1.sei.mixin;

import io.github.boogiemonster1o1.sei.gui.ItemListOverlay;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.client.gui.screen.ingame.FurnaceScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.container.Container;
import org.spongepowered.asm.mixin.Mixin;

@Environment(EnvType.CLIENT)
@Mixin(FurnaceScreen.class)
public abstract class FurnaceScreenMixin extends ContainerScreen {

    private ItemListOverlay overlay;

    public FurnaceScreenMixin(Container container) {
        super(container);
    }

    @Override
    public void init() {
        super.init();
        overlay = new ItemListOverlay(this.x, this.containerHeight, width, height);
        overlay.init(this.buttons);
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        super.render(mouseX, mouseY, delta);
        overlay.render( this.textRenderer);
    }

    @Override
    protected void buttonPressed(ButtonWidget button) {
        super.buttonPressed(button);
        overlay.buttonPressed(button);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        overlay.mouseClicked(mouseX, mouseY, button);
    }
}

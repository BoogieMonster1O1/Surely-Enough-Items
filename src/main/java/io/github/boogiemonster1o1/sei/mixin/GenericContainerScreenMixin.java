package io.github.boogiemonster1o1.sei.mixin;

import io.github.boogiemonster1o1.sei.gui.ContainerOverlay;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.container.Container;
import net.minecraft.inventory.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(GenericContainerScreen.class)
public abstract class GenericContainerScreenMixin extends ContainerScreen {
    @Shadow private Inventory field_3457; // upper
    @Shadow private Inventory field_3458; // lower
    private ContainerOverlay overlay;

    public GenericContainerScreenMixin(Container container) {
        super(container);
    }

    private Inventory getUpperChestInventory() {
        return this.field_3457;
    }

    private Inventory getLowerChestInventory() {
        return this.field_3458;
    }

    @Override
    public void init() {
        super.init();
        overlay = new ContainerOverlay(this.x, this.containerHeight, width, height);
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

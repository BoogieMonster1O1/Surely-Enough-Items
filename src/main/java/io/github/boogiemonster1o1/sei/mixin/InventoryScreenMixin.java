package io.github.boogiemonster1o1.sei.mixin;

import io.github.boogiemonster1o1.sei.SurelyEnoughItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.container.Container;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends ContainerScreen {
    public InventoryScreenMixin(Container container) {
        super(container);
    }

    @Inject(method = "drawForeground",at=@At(value="HEAD"))
    private void renderItemListForeground(int mouseX,int mouseY,CallbackInfo ci){
        GuiLighting.method_2214();
        int xInitial = this.x + 4;
        int x = xInitial;
        int y =  - this.y + 32;
        for (ItemStack stack : SurelyEnoughItems.ITEM_STACKS.itemList) {
            this.drawItemStack(stack, x, y);
            x += 18;
            if ((x + this.x) > width) {
                x = xInitial;
                y += 18;
            }

            if ((y + 16 + this.y) > height) {
                break;
            }
        }
        GuiLighting.method_2210();
    }

    private void drawItemStack(ItemStack itemStack, int xPos, int yPos) {
        TextRenderer text = MinecraftClient.getInstance().textRenderer;
        if (text == null) {
            text = this.textRenderer;
        }
        this.itemRenderer.renderInGuiWithOverrides( itemStack, xPos, yPos);
        this.itemRenderer.renderGuiItemOverlay(text, itemStack, xPos, yPos,null);
    }
}

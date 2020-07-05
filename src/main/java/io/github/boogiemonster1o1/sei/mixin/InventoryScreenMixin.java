package io.github.boogiemonster1o1.sei.mixin;

import io.github.boogiemonster1o1.sei.SurelyEnoughItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.container.Container;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.Color;

@Environment(EnvType.CLIENT)
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends ContainerScreen {

    public ButtonWidget next;
    public ButtonWidget previous;
    public int pageNum = 1;
    static int iconPadding = 2;
    static int iconSize = 16;

    public InventoryScreenMixin(Container container) {
        super(container);
    }

    @Inject(method="init",at=@At(value="TAIL"))
    private void initButtons(CallbackInfo ci){
        int buttonWidth = 50;
        int buttonHeight = 20;
        this.buttons.add(next = new ButtonWidget(-1, this.width - buttonWidth - 4, 0, buttonWidth, buttonHeight, "Next"));
        this.buttons.add(previous = new ButtonWidget(-2, this.x + this.containerWidth + 4, 0, buttonWidth, buttonHeight, "Back"));

        int pageCount = this.getPageCount();
        if (this.pageNum > pageCount) {
            this.pageNum = pageCount;
        }

        this.updateButtons();
    }

    @Inject(method = "drawForeground",at=@At(value="HEAD"))
    private void renderItemListForeground(int mouseX,int mouseY,CallbackInfo ci){
        GuiLighting.method_2214();

        final int xStart = this.containerWidth + 4;
        final int yStart = -this.y + 20 + 4;

        int x = xStart;
        int y = yStart;

        int maxX = 0;

        for (int i = (pageNum - 1) * this.getCountPerPage(); i < SurelyEnoughItems.ITEM_STACKS.itemList.size(); i++) {
            ItemStack stack = SurelyEnoughItems.ITEM_STACKS.itemList.get(i);
            this.drawItemStack(stack, x, y);

            x += iconSize + iconPadding;

            if (x + iconSize + this.x > this.width) {
                x = xStart;
                y += iconSize + iconPadding;
            }

            if (y + iconSize + this.y > this.height) {
                break;
            }
        }

        GuiLighting.method_2210();

        this.next.x = this.x + maxX + iconSize - this.next.getWidth();

        this.drawPagination();
        this.updateButtons();
    }

    private int getCountPerPage() {
        int xArea = this.width - (this.x + this.containerWidth + 4);
        int yArea = this.height - (20 + 4);

        int xCount = xArea / (iconSize + iconPadding);
        int yCount = yArea / (iconSize + iconPadding);

        return xCount * yCount;
    }

    @Override
    protected void buttonPressed(ButtonWidget button) {
        if (button.id == -1 && pageNum < getPageCount()) {
            pageNum++;
        } else if (button.id == -2 && pageNum > 1) {
            pageNum--;
        }
        this.updateButtons();
    }

    private int getPageCount() {
        int count = SurelyEnoughItems.ITEM_STACKS.itemList.size();
        return (int) Math.ceil((double) count / (double) this.getCountPerPage());
    }

    private void updateButtons() {
        this.next.active = this.pageNum < this.getPageCount();
        this.previous.active = this.pageNum > 1;
    }

    protected int getPageNum() {
        return this.pageNum;
    }

    private void drawPagination() {
        String pageDisplay = getPageNum() + " / " + getPageCount();
        int pageDisplayWidth = this.textRenderer.getStringWidth(pageDisplay);

        int pageDisplayX = ((this.previous.x + this.previous.getWidth()) + this.previous.x) / 2;
        int pageDisplayY = this.previous.y + 6;

        this.textRenderer.draw(pageDisplay, pageDisplayX - (pageDisplayWidth / 2) - this.x, pageDisplayY - this.y, Color.white.getRGB());
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

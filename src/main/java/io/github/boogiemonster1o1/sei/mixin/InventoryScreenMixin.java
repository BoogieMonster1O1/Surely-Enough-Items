package io.github.boogiemonster1o1.sei.mixin;

import io.github.boogiemonster1o1.sei.SurelyEnoughItems;
import io.github.boogiemonster1o1.sei.gui.ItemIcon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.container.Container;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends ContainerScreen {

    private static final int iconPadding = 2;
    private static final int iconSize = 16;

    protected List<ItemIcon> items = new ArrayList<>();

    protected ButtonWidget nextButton;
    protected ButtonWidget backButton;
    protected int pageNum = 1;

    public InventoryScreenMixin(Container container) {
        super(container);
    }

    @Inject(method="render",at=@At("TAIL"))
    public void renderAll(int mouseX, int mouseY, float delta,CallbackInfo ci) {
        GuiLighting.method_2214();

        for (ItemIcon itemIcon : items) {
            itemIcon.draw(this.itemRenderer, this.textRenderer);
        }

        GuiLighting.method_2210();

        drawPageNumbers();
    }

    @Inject(method="init",at=@At("RETURN"))
    public void initGui(CallbackInfo ci) {
        final int buttonWidth = 50;
        final int buttonHeight = 20;
        buttons.add(nextButton = new ButtonWidget(-1, this.width - buttonWidth - 4, 0, buttonWidth, buttonHeight, "Next"));
        buttons.add(backButton = new ButtonWidget(-2, this.x + this.containerWidth + 4, 0, buttonWidth, buttonHeight, "Back"));

        int pageCount = getPageCount();
        if (pageNum > pageCount) {
            setPageNum(pageCount);
        }

        updatePage();
        updateButtonEnabled();
    }

    private void updatePage() {
        items.clear();

        final int xStart = this.x + this.containerWidth + 4;
        final int yStart = 20 + 4;

        int x = xStart;
        int y = yStart;
        int maxX = 0;

        for (int i = (pageNum - 1) * getCountPerPage(); i < SurelyEnoughItems.ITEM_STACKS.itemList.size(); i++) {
            ItemStack stack = SurelyEnoughItems.ITEM_STACKS.itemList.get(i);
            items.add(new ItemIcon(stack, x, y));

            x += iconSize + iconPadding;
            if (x + iconSize > width) {
                x = xStart;
                y += iconSize + iconPadding;
            }

            if (y + iconSize > height)
                break;

            if (x > maxX)
                maxX = x;
        }

        this.nextButton.x = maxX + iconSize - this.nextButton.getWidth();
    }

    private void updateButtonEnabled() {
        nextButton.active = pageNum < getPageCount();
        backButton.active = pageNum > 1;
    }

    @Inject(method="buttonPressed",at=@At("TAIL"))
    protected void buttonPressed(ButtonWidget button,CallbackInfo ci) {
        if (button.id == -1 && pageNum < getPageCount()) {
            this.setPageNum(pageNum + 1);
        } else if (button.id == -2 && pageNum > 1) {
            this.setPageNum(pageNum - 1);
        }
        this.updateButtonEnabled();
        this.updatePage();
    }

    @Override
    protected void mouseClicked(int xPos, int yPos, int mouseButton) {
        for (ItemIcon itemIcon : items) {
            if (itemIcon.isMouseOver(xPos, yPos)) {
                itemIcon.mouseClicked(xPos, yPos, mouseButton);
                return;
            }
        }
        super.mouseClicked(xPos, yPos, mouseButton);
    }

    private void drawPageNumbers() {
        String pageDisplay = getPageNum() + " / " + getPageCount();
        int pageDisplayWidth = this.textRenderer.getStringWidth(pageDisplay);

        int pageDisplayX = ((this.backButton.x + this.backButton.getWidth()) + this.nextButton.x) / 2;
        int pageDisplayY = this.backButton.y + 6;

        this.textRenderer.draw(pageDisplay, pageDisplayX - (pageDisplayWidth / 2), pageDisplayY, Color.white.getRGB());
    }

    private int getCountPerPage() {
        int xArea = width - (this.x + this.containerWidth + 4);
        int yArea = height - (20 + 4);

        int xCount = xArea / (iconSize + iconPadding);
        int yCount = yArea / (iconSize + iconPadding);

        return xCount * yCount;
    }

    private int getPageCount() {
        int count = SurelyEnoughItems.ITEM_STACKS.itemList.size();
        return (int) Math.ceil((double) count / (double) getCountPerPage());
    }

    protected int getPageNum() {
        return pageNum;
    }

    protected void setPageNum(int pageNum) {
        if (this.pageNum == pageNum)
            return;
        this.pageNum = pageNum;
        updatePage();
    }
}

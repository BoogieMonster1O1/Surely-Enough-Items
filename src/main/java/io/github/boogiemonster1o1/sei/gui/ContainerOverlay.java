package io.github.boogiemonster1o1.sei.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import io.github.boogiemonster1o1.sei.SurelyEnoughItems;
import io.github.boogiemonster1o1.sei.mixin.ButtonWidgetAccessor;
import io.github.boogiemonster1o1.sei.util.RenderHelper;
import org.lwjgl.input.Mouse;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.item.ItemStack;

public class ContainerOverlay {
    static final int borderPadding = 1;
    protected ArrayList<ItemButton> itemButtons = new ArrayList<>();
    protected ButtonWidget nextButton;
    protected ButtonWidget backButton;
    protected static int pageNum = 1;
    protected int x;
    protected int y;
    protected int containerWidth;
    protected int containerHeight;
    protected int width;
    protected int height;
    private boolean clickHandled = false;

    public void init(int x, int y, int containerWidth, int containerHeight, int width, int height, List<ButtonWidget> buttons) {
        this.x = x;
        this.y = y;
        this.containerWidth = containerWidth;
        this.containerHeight = containerHeight;
        this.width = width;
        this.height = height;

        String next = ">";
        String back = "<";

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        final int nextButtonWidth = 10 + textRenderer.getStringWidth(next);
        final int backButtonWidth = 10 + textRenderer.getStringWidth(back);
        final int buttonHeight = 5 + textRenderer.fontHeight;
        this.nextButton = new ButtonWidget(0, this.width - nextButtonWidth - borderPadding, 0, nextButtonWidth, buttonHeight, next);
        this.backButton = new ButtonWidget(1, this.x + this.containerWidth + borderPadding, 0, backButtonWidth, buttonHeight, back);

        this.createItemButtons();
        this.updateItemButtons();

        int pageCount = this.getPageCount();
        if (pageNum > pageCount)
            pageNum = pageCount;
    }

    private void createItemButtons() {
        this.itemButtons.clear();

        final int xStart = this.x + this.containerWidth + borderPadding;
        final int yStart = ((ButtonWidgetAccessor) this.backButton).getHeight() + (2 * borderPadding);

        int x = xStart;
        int y = yStart;
        int maxX = 0;

        while (y + ItemButton.HEIGHT + borderPadding <= this.height) {
            if (x > maxX)
                maxX = x;

            this.itemButtons.add(new ItemButton(null, x, y));

            x += ItemButton.WIDTH;
            if (x + ItemButton.WIDTH + borderPadding > this.width) {
                x = xStart;
                y += ItemButton.HEIGHT;
            }
        }

        this.nextButton.x = maxX + ItemButton.WIDTH - ((ButtonWidgetAccessor) this.nextButton).getWidth() - borderPadding;
    }

    private void updateItemButtons() {
        int i = (pageNum - 1) * this.getCountPerPage();

        for (ItemButton itemButton : this.itemButtons) {
            if (i >= SurelyEnoughItems.ITEM_STACKS.getPlainList().size()) {
                itemButton.setStack(null);
            } else {
                ItemStack stack = SurelyEnoughItems.ITEM_STACKS.getItemList().get(i);
                itemButton.setStack(stack);
            }
            i++;
        }
    }

    public void nextPage() {
        if (pageNum == this.getPageCount())
            this.setPageNum(1);
        else
            this.setPageNum(pageNum + 1);
    }

    public void backPage() {
        if (pageNum == 1)
            this.setPageNum(this.getPageCount());
        else
            this.setPageNum(pageNum - 1);
    }

    public void drawScreen(MinecraftClient minecraft, int mouseX, int mouseY) {
        GlStateManager.enableRescaleNormal();
        GuiLighting.enable();
        this.drawPageNumbers(minecraft.textRenderer);
        this.drawButtons(minecraft, mouseX, mouseY);
        GuiLighting.disable();
        GlStateManager.disableRescaleNormal();
    }

    private void drawPageNumbers(TextRenderer textRenderer) {
        String pageDisplay = this.getPageNum() + "/" + this.getPageCount();
        int pageDisplayWidth = textRenderer.getStringWidth(pageDisplay);

        int pageDisplayX = ((this.backButton.x + ((ButtonWidgetAccessor) this.backButton).getWidth()) + this.nextButton.x) / 2;
        int pageDisplayY = this.backButton.y + Math.round((((ButtonWidgetAccessor) this.backButton).getHeight() - textRenderer.fontHeight) / 2.0f);

        textRenderer.draw(pageDisplay, pageDisplayX - (Math.round(pageDisplayWidth / 2.0F)), pageDisplayY, Color.white.getRGB(), true);
    }

    public void drawButtons(MinecraftClient minecraft, int mouseX, int mouseY) {
        this.nextButton.render(minecraft, mouseX, mouseY);
        this.backButton.render(minecraft, mouseX, mouseY);
        ItemButton hoveredItemButton = null;
        for (ItemButton guiItemButton : this.itemButtons) {
            if (guiItemButton.isMouseOver(minecraft, mouseX, mouseY)) {
                guiItemButton.render(minecraft, mouseX, mouseY);
                if (hoveredItemButton == null && guiItemButton.isMouseOver(minecraft, mouseX, mouseY)) {
                    hoveredItemButton = guiItemButton;
                }
            }
        }
        if (hoveredItemButton != null)
            RenderHelper.renderToolTip(hoveredItemButton.getStack(), mouseX, mouseY);
    }

    public void handleInput(MinecraftClient minecraft) {
        if (Mouse.getEventButtonState()) {
            if (!this.clickHandled) {
                int mouseX = Mouse.getEventX() * this.width / minecraft.width;
                int mouseY = this.height - Mouse.getEventY() * this.height / minecraft.height - 1;
                this.handleMouseClick(minecraft, Mouse.getEventButton(), mouseX, mouseY);
                this.clickHandled = true;
            }
        } else {
            this.clickHandled = false;
        }
    }

    private void handleMouseClick(MinecraftClient minecraft, int mouseButton, int mouseX, int mouseY) {
        if (this.nextButton.isMouseOver(minecraft, mouseX, mouseY)) {
            this.nextPage();
        } else if (this.backButton.isMouseOver(minecraft, mouseX, mouseY)) {
            this.backPage();
        } else {
            for (ItemButton guiItemButton : this.itemButtons) {
                if (guiItemButton.isMouseOver(minecraft, mouseX, mouseY)) {
                    guiItemButton.handleMouseClick(mouseButton);
                }
            }
        }
    }

    private int getCountPerPage() {
        int xArea = this.width - (this.x + this.containerWidth + (2 * borderPadding));
        int yArea = this.height - (((ButtonWidgetAccessor) this.backButton).getHeight() + (2 * borderPadding));

        int xCount = xArea / ItemButton.WIDTH;
        int yCount = yArea / ItemButton.HEIGHT;

        return xCount * yCount;
    }

    private int getPageCount() {
        int count = SurelyEnoughItems.ITEM_STACKS.getPlainList().size();
        return (int) Math.ceil((double) count / (double) this.getCountPerPage());
    }

    protected int getPageNum() {
        return pageNum;
    }

    protected void setPageNum(int pageNum) {
        if (ContainerOverlay.pageNum == pageNum)
            return;
        ContainerOverlay.pageNum = pageNum;
        this.updateItemButtons();
    }

}
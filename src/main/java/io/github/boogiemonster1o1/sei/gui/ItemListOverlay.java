package io.github.boogiemonster1o1.sei.gui;

import io.github.boogiemonster1o1.sei.SurelyEnoughItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ItemListOverlay {
    static final int iconPadding = 2;
    static final int iconSize = 16;
    private static final ItemRenderer ITEM_RENDERER = MinecraftClient.getInstance().getItemRenderer();

    protected List<ItemIcon> items = new ArrayList<>();

    protected ButtonWidget nextButton;
    protected ButtonWidget backButton;
    protected static int pageNum = 1;

    protected int x;
    protected int containerSize;
    protected int width;
    protected int height;

    public ItemListOverlay(int x, int containerSize, int width, int height) {
        this.x = x;
        this.containerSize = containerSize;
        this.width = width;
        this.height = height;
    }

    public void init(List<ButtonWidget> buttonList) {
        final int buttonWidth = 20;
        final int buttonHeight = 20;
        String next = ">";
        String back = "<";
        buttonList.add(nextButton = new ButtonWidget(50, this.width - buttonWidth - 4, 0, buttonWidth, buttonHeight, next));
        buttonList.add(backButton = new ButtonWidget(51, this.x + this.containerSize + 4, 0, buttonWidth, buttonHeight, back));

        int pageCount = getPageCount();
        if (pageNum > pageCount)
            setPageNum(pageCount);

        updatePage();
    }

    private void updatePage() {
        items.clear();

        final int xStart = x + containerSize + 4;
        final int yStart = 20 + 4;

        int x = xStart;
        int y = yStart;
        int maxX = 0;

        for (int i = (pageNum - 1) * getCountPerPage(); i < SurelyEnoughItems.ITEM_STACKS.itemList.size() && y + iconSize <= height; i++) {
            if (x > maxX)
                maxX = x;

            ItemStack stack = SurelyEnoughItems.ITEM_STACKS.itemList.get(i);
            items.add(new ItemIcon(stack, x, y));

            x += iconSize + iconPadding;
            if (x + iconSize > width) {
                x = xStart;
                y += iconSize + iconPadding;
            }
        }

        nextButton.x = maxX + iconSize - nextButton.getWidth();
    }

    public void buttonPressed(ButtonWidget button) {
        if (button.id == 50) {
            if (pageNum == getPageCount())
                setPageNum(1);
            else
                setPageNum(pageNum + 1);
        } else if (button.id == 51) {
            if (pageNum == 1)
                setPageNum(getPageCount());
            else
                setPageNum(pageNum - 1);
        }
    }

    public void mouseClicked(int xPos, int yPos, int mouseButton) {
        for (ItemIcon itemIcon : items) {
            if (itemIcon.isMouseOver(xPos, yPos)) {
                itemIcon.mouseClicked(xPos, yPos, mouseButton);
                return;
            }
        }
    }

    public void render(TextRenderer textRenderer) {
        GuiLighting.method_2214();

        for (ItemIcon itemIcon : items)
            itemIcon.draw(ITEM_RENDERER, textRenderer);

        GuiLighting.method_2210();

        drawPageNumbers(textRenderer);
    }

    private void drawPageNumbers(TextRenderer textRenderer) {
        String pageDisplay = getPageNum() + " / " + getPageCount();
        int pageDisplayWidth = textRenderer.getStringWidth(pageDisplay);

        int pageDisplayX = ((backButton.x + backButton.getWidth()) + nextButton.x) / 2;
        int pageDisplayY = backButton.y + 6;

        textRenderer.draw(pageDisplay, pageDisplayX - (pageDisplayWidth / 2), pageDisplayY, Color.white.getRGB());
    }

    private int getCountPerPage() {
        int xArea = width - (x + containerSize + 4);
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
        if (ItemListOverlay.pageNum == pageNum)
            return;
        ItemListOverlay.pageNum = pageNum;
        updatePage();

    }

    public void keyPressed(int keyCode){
        try{
            if (keyCode == Keyboard.KEY_LEFT) {
                this.setPageNum(this.getPageNum() + 1);
            } else if (keyCode == Keyboard.KEY_RIGHT) {
                this.setPageNum(this.getPageNum() - 1);
            }
        } catch (Throwable ignore) {
        }
    }
}
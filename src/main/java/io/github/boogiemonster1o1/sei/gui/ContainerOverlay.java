package io.github.boogiemonster1o1.sei.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.github.boogiemonster1o1.sei.SurelyEnoughItems;
import io.github.boogiemonster1o1.sei.mixin.ButtonWidgetAccessor;
import io.github.boogiemonster1o1.sei.util.RenderHelper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ButtonWidget;
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

        ArrayList<Integer> buttonIDs = this.getUnusedButtonIDs(buttons, 2);

        this.nextButton = new ButtonWidget(buttonIDs.get(0), this.width - nextButtonWidth - borderPadding, 0, nextButtonWidth, buttonHeight, next);
        this.backButton = new ButtonWidget(buttonIDs.get(1), this.x + this.containerWidth + borderPadding, 0, backButtonWidth, buttonHeight, back);
        buttons.add(this.nextButton);
        buttons.add(this.backButton);

        this.createItemButtons();
        this.updateItemButtons();

        buttonIDs = this.getUnusedButtonIDs(buttons, this.itemButtons.size());
        for (int i = 0; i < this.itemButtons.size(); i++) {
            ItemButton button = this.itemButtons.get(i);
            button.id = buttonIDs.get(i);
            buttons.add(button);
        }

        int pageCount = this.getPageCount();
        if (pageNum > pageCount)
            pageNum = pageCount;
    }

    private ArrayList<Integer> getUnusedButtonIDs(List<ButtonWidget> buttons, int count) {
        HashSet<Integer> usedButtonIDs = new HashSet<>();
        for (ButtonWidget button : buttons)
            usedButtonIDs.add(button.id);

        ArrayList<Integer> availableButtonIDs = new ArrayList<Integer>();
        int id = 10;
        while (availableButtonIDs.size() < count) {
            if (!usedButtonIDs.contains(id))
                availableButtonIDs.add(id);
            id++;
        }
        return availableButtonIDs;
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

    public boolean actionPerformed(ButtonWidget button) {
        if (button.id == this.nextButton.id) {
            this.nextPage();
        } else if (button.id == this.backButton.id) {
            this.backPage();
        } else if (button instanceof ItemButton) {
            ((ItemButton) button).actionPerformed();
        } else {
            return false;
        }

        return true;
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
        this.drawPageNumbers(minecraft.textRenderer);
    }

    private void drawPageNumbers(TextRenderer textRenderer) {
        String pageDisplay = this.getPageNum() + "/" + this.getPageCount();
        int pageDisplayWidth = textRenderer.getStringWidth(pageDisplay);

        int pageDisplayX = ((this.backButton.x + ((ButtonWidgetAccessor) this.backButton).getWidth()) + this.nextButton.x) / 2;
        int pageDisplayY = this.backButton.y + Math.round((((ButtonWidgetAccessor) this.backButton).getHeight() - textRenderer.fontHeight) / 2.0f);

        textRenderer.draw(pageDisplay, pageDisplayX - (Math.round(pageDisplayWidth / 2.0F)), pageDisplayY, Color.white.getRGB(), true);
    }

    public void drawTooltips(MinecraftClient client, int mouseX, int mouseY) {
        for (ItemButton guiItemButton : this.itemButtons) {
            if (guiItemButton.isMouseOver(client, mouseX, mouseY)) {
                RenderHelper.renderToolTip(guiItemButton.getStack(), mouseX, mouseY);
                break;
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
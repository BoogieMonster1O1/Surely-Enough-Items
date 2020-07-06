package io.github.boogiemonster1o1.sei.gui;

import io.github.boogiemonster1o1.sei.util.SEICommandHelper;
import io.github.boogiemonster1o1.sei.util.SEIPermissions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;

public class ItemIcon {
    protected ItemStack itemStack;
    protected int x;
    protected int y;
    protected int width = 16;
    protected int height = 16;

    public ItemIcon(ItemStack itemStack, int x, int y) {
        this.itemStack = itemStack;
        this.x = x;
        this.y = y;
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + width;
    }

    public void mouseClicked(int xPos, int yPos, int mouseButton) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (SEIPermissions.canPlayerSpawnItems(player)) {
            if (mouseButton == 0) {
                SEICommandHelper.giveFullStack(itemStack);
            }
        }
        //TODO: recipes
    }

    public void draw(ItemRenderer itemRender, TextRenderer textRenderer) {
        itemRender.renderGuiItemOverlay(textRenderer, itemStack, x, y,null);
        itemRender.renderInGuiWithOverrides(itemStack, x, y);
    }
}

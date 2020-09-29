package io.github.boogiemonster1o1.sei.gui;

import io.github.boogiemonster1o1.sei.util.CommandHelper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;

public class ItemButton extends ButtonWidget {
    protected ItemStack stack;
    protected static final int PADDING = 1;
    private static final ItemRenderer ITEM_RENDERER = MinecraftClient.getInstance().getItemRenderer();
    public static final int WIDTH = 16 + (PADDING * 2);
    public static final int HEIGHT = 16 + (PADDING * 2);

    public ItemButton(ItemStack stack, int x, int y) {
        super(0, x, y, WIDTH, HEIGHT, null);
        this.setStack(stack);
    }

    public void setStack(ItemStack stack) {
        this.stack = stack;
        this.visible = this.active = (stack != null);
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public void handleMouseClick(int mouseButton) {
        if (!this.active) {
            return;
        }

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (MinecraftClient.getInstance().getServer().getPlayerManager().isOperator(player.getGameProfile()) && player.inventory.method_7966() != -1) {
            if (mouseButton == 0) {
                CommandHelper.giveFullStack(this.stack);
            } else if (mouseButton == 1) {
                CommandHelper.giveOneFromStack(this.stack);
            }
        }
        //TODO: recipes
    }

    @Override
    public void render(MinecraftClient client, int mouseX, int mouseY) {
        if (!this.visible) {
            return;
        }
        ITEM_RENDERER.renderInGuiWithOverrides(this.stack, this.x + PADDING, this.y + PADDING);
    }

    public int getHeight() {
        return this.height;
    }
}

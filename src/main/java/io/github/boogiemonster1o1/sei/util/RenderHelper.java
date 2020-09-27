package io.github.boogiemonster1o1.sei.util;

import io.github.boogiemonster1o1.sei.mixin.ScreenAccessor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;

public class RenderHelper {
    public static void renderToolTip(ItemStack itemStack, int mouseX, int mouseY) {
        Screen screen = MinecraftClient.getInstance().currentScreen;
        ((ScreenAccessor) screen).invokeRenderTooltip(itemStack, mouseX, mouseY);
    }
}
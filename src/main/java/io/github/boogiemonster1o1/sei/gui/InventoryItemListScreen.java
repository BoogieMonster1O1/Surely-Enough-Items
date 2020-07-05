package io.github.boogiemonster1o1.sei.gui;

import io.github.boogiemonster1o1.sei.SurelyEnoughItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.item.ItemStack;

import java.util.Iterator;

public class InventoryItemListScreen extends InventoryScreen {
    public InventoryItemListScreen() {
        super(MinecraftClient.getInstance().player);
    }
}

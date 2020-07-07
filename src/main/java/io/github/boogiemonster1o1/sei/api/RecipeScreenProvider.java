package io.github.boogiemonster1o1.sei.api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

public interface RecipeScreenProvider {
    void setRecipe(Object recipe, ItemStack focusStack);

    boolean hasRecipe();

    ItemStack getStackUnderMouse(int mouseX, int mouseY);

    int getWidth();
    int getHeight();

    void setPosition(int posX, int posY);

    void draw(MinecraftClient minecraft, int mouseX, int mouseY);

}
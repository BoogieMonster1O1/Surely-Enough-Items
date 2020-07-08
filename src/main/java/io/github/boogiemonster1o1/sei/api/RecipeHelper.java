package io.github.boogiemonster1o1.sei.api;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface RecipeHelper {

    String getTitle();

    Class<?> getRecipeClass();

    RecipeScreenProvider createGui();

    List<ItemStack> getInputs(Object recipe);

    List<ItemStack> getOutputs(Object recipe);

}
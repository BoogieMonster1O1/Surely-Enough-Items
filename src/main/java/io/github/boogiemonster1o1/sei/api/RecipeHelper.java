package io.github.boogiemonster1o1.sei.api;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface RecipeHelper {

    /* Returns the page title for this type of recipe. */
    String getTitle();

    /* Returns the class of the Recipe handled by this IRecipeHelper. */
    Class<?> getRecipeClass();

    /* Returns a new IRecipeGui instance. */
    RecipeScreenProvider createGui();

    /* Returns all input ItemStacks for the recipe. */
    List<ItemStack> getInputs(Object recipe);

    /* Returns all output ItemStacks for the recipe. */
    List<ItemStack> getOutputs(Object recipe);

}
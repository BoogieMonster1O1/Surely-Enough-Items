package io.github.boogiemonster1o1.sei.api;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface RecipeRegistryProvider {
    void registerRecipeHelper(RecipeHelper recipeHelper);

    void addRecipes(Iterable<Object> recipes);

    boolean hasRecipeHelper(Class<?> recipeClass);

    RecipeHelper getRecipeHelper(Class<?> recipeClass);

    List<Class<?>> getInputRecipeClasses(ItemStack input);

    List<Class<?>> getOutputRecipeClasses(ItemStack output);

    List<Object> getInputRecipes(Class<?> recipeClass, ItemStack input);

    List<Object> getOutputRecipes(Class<?> recipeClass, ItemStack output);
}
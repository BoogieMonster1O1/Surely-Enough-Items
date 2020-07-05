package io.github.boogiemonster1o1.sei;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemStacks {
    public List<ItemStack> itemList = new ArrayList<>();
    public Set<String> itemNameSet = new HashSet<>();

    public ItemStacks() {
        for (Block block : Block.REGISTRY) {
            this.addStack(new ItemStack(block));
        }

        for (Item item : Item.REGISTRY) {
            this.addStack(new ItemStack(item));
        }
    }

    public void addStack(ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }

        Item item = itemStack.getItem();

        if (item == null) {
            return;
        }

        if (itemStack.method_8391()) {
            List<ItemStack> subItems = new ArrayList<>();
            item.addToItemGroup(item, null, subItems);
            this.addItemStacks(subItems);
        } else {
            this.addItemStack(itemStack);
        }
    }

    public void addItemStacks(Iterable<ItemStack> stacks) {
        for (ItemStack stack : stacks) {
            this.addItemStack(stack);
        }
    }

    public void addItemStack(ItemStack stack) {
        if (itemNameSet.contains(stack.getTranslationKey()))
            return;
        itemNameSet.add(stack.getTranslationKey());
        itemList.add(stack);
    }
}

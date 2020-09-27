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
            this.addBlockAndSubBlocks(block);
        }

        for (Item item : Item.REGISTRY) {
            this.addItemAndSubItems(item);
        }
    }

    public void addItemAndSubItems(Item item) {
        if (item == null)
            return;

        List<ItemStack> subItems = new ArrayList<>();
        item.appendItemStacks(item, null, subItems);
        this.addItemStacks(subItems);

        if (subItems.isEmpty()) {
            ItemStack stack = new ItemStack(item);
            if (stack.getItem() == null) {
                return;
            }
            this.addItemStack(stack);
        }
    }

    public void addBlockAndSubBlocks(Block block) {
        if (block == null)
            return;

        Item item = Item.fromBlock(block);

        List<ItemStack> subItems = new ArrayList<>();
        if (item != null) {
            block.appendItemStacks(item, null, subItems);
            this.addItemStacks(subItems);
        }

        if (subItems.isEmpty()) {
            ItemStack stack = new ItemStack(block);
            if (stack.getItem() == null) {
                return;
            }
            addItemStack(stack);
        }
    }

    public void addItemStacks(Iterable<ItemStack> stacks) {
        stacks.forEach(this::addItemStack);
    }

    public void addItemStack(ItemStack stack) {
        String itemKey = uniqueIdentifierForStack(stack);
        if (stack.hasTag())
            itemKey += stack.getTag();

        if (itemNameSet.contains(itemKey))
            return;
        itemNameSet.add(itemKey);
        itemList.add(stack);
    }

    private String uniqueIdentifierForStack(ItemStack stack) {
        StringBuilder itemKey = new StringBuilder(stack.getTranslationKey());
        if (stack.hasTag())
            itemKey.append(":").append(stack.getTag().toString());
        return itemKey.toString();
    }
}

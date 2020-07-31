package io.github.boogiemonster1o1.sei.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.GiveCommand;

public class SEICommandHelper {
    public static void giveFullStack(ItemStack itemstack) {
        SEICommandHelper.giveStack(itemstack, itemstack.getMaxCount());
    }

    public static void giveStack(ItemStack itemStack, int amount) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if(player.inventory.method_7966() != -1) {
            return;
        }
//        String senderName = player.getName().asString();
//
//        List<String> commandStrings = new ArrayList<>();
//        commandStrings.add(senderName);
//        commandStrings.add(Item.REGISTRY.getIdentifier(itemStack.getItem()).toString());
//        commandStrings.add("" + amount);
//        commandStrings.add("" + itemStack.getDamage());
//
//        if (itemStack.hasTag())
//            commandStrings.add(itemStack.getTag().toString());
//
//        GiveCommand commandGive = new GiveCommand();
//
//        try {
//            commandGive.method_5885(player, commandStrings.toArray(new String[0]));
//        } catch (CommandException e) {
//            e.printStackTrace();
//        }

        if(MinecraftClient.getInstance().isInSingleplayer()) {
            try {
                ItemStack stack = (ItemStack) itemStack.getClass().getDeclaredMethod("clone").invoke(itemStack);
                player.inventory.insertStack(stack);
                player.inventory.markDirty();
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Error e) {
                throw new RuntimeException(e);
            }
        }
    }
}

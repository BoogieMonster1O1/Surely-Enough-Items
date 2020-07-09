package io.github.boogiemonster1o1.sei.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.GiveCommand;

import java.util.ArrayList;
import java.util.List;

public class SEICommandHelper {
    public static void giveFullStack(ItemStack itemstack) {
        SEICommandHelper.giveStack(itemstack, itemstack.getMaxCount());
    }

    public static void giveStack(ItemStack itemStack, int amount) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if(player.inventory.method_7966() != -1){
            String senderName = player.getName().asString();

            List<String> commandStrings = new ArrayList<>();
            commandStrings.add(senderName);
            commandStrings.add(Item.REGISTRY.getIdentifier(itemStack.getItem()).toString());
            commandStrings.add("" + amount);
            commandStrings.add("" + itemStack.getDamage());

            if (itemStack.hasTag())
                commandStrings.add(itemStack.getTag().toString());

            GiveCommand commandGive = new GiveCommand();

            try {
                commandGive.method_5885(player, commandStrings.toArray(new String[0]));
            } catch (CommandException e) {
                e.printStackTrace();
            }
        }
    }
}

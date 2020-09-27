package io.github.boogiemonster1o1.sei.util;

import java.util.ArrayList;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.CommandException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.GiveCommand;

public class CommandHelper {
    public static void giveFullStack(ItemStack itemstack) {
        giveStack(itemstack, itemstack.getMaxCount());
    }

    public static void giveOneFromStack(ItemStack itemstack) {
        giveStack(itemstack, 1);
    }

    public static void giveStack(ItemStack itemStack, int amount) {
        ClientPlayerEntity sender = MinecraftClient.getInstance().player;
        String senderName = sender.method_6959();

        ArrayList<String> commandStrings = new ArrayList<>();
        commandStrings.add(senderName);
        commandStrings.add(Item.REGISTRY.getIdentifier(itemStack.getItem()).toString());
        commandStrings.add("" + amount);
        commandStrings.add("" + itemStack.getDamage());

        if (itemStack.hasTag())
            commandStrings.add(itemStack.getTag().copy().toString());

        GiveCommand commandGive = new GiveCommand();
        try {
            commandGive.execute(sender, commandStrings.toArray(new String[0]));
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }
}

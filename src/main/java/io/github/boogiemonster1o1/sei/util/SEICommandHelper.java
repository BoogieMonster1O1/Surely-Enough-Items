package io.github.boogiemonster1o1.sei.util;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;

import java.util.List;
import java.util.Objects;

public class SEICommandHelper {
    public static void giveFullStack(ItemStack itemstack) {
        SEICommandHelper.giveStack(itemstack, itemstack.getMaxCount());
    }

    public static void giveStack(ItemStack itemStack, int amount) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if(player.inventory.method_7966() == -1){
            return;
        }
        ItemStack stack = new ItemStack(itemStack.getItem(),amount);
        stack.setTag(itemStack.getTag());
        if(MinecraftClient.getInstance().isInSingleplayer()){
            List<PlayerEntity> players = MinecraftClient.getInstance().getServer().worlds[0].field_272;
            for(PlayerEntity playerInList: players){
                if(playerInList.getUuid() == player.getUuid()){
                    playerInList.inventory.insertStack(stack);
                }
            }
        }
//        String senderName = sender.getName().asString();
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
//            commandGive.method_5885(sender, commandStrings.toArray(new String[0]));
//        } catch (CommandException e) {
//            e.printStackTrace();
//            LogManager.getLogger(SurelyEnoughItems.class).exit();
//        }
    }
}

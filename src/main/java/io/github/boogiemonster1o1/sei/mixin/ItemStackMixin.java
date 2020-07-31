package io.github.boogiemonster1o1.sei.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.item.ItemStack;

@Mixin(ItemStack.class)
public class ItemStackMixin implements Cloneable {
    @Override
    public Object clone()  {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }
}

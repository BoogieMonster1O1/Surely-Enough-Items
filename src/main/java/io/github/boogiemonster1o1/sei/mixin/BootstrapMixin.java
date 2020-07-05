package io.github.boogiemonster1o1.sei.mixin;

import io.github.boogiemonster1o1.sei.ItemStacks;
import io.github.boogiemonster1o1.sei.SurelyEnoughItems;
import net.minecraft.Bootstrap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Bootstrap.class)
public class BootstrapMixin {
    @Inject(method="initialize",at=@At(value="INVOKE",target="Lnet/minecraft/Bootstrap;setupDispenserBehavior()V"))
    private static void initializeList(CallbackInfo ci){
        SurelyEnoughItems.ITEM_STACKS = new ItemStacks();
    }
}

package io.github.boogiemonster1o1.sei.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemStack;

@Mixin(Screen.class)
public interface ScreenAccessor {
    @Invoker
    void invokeRenderTooltip(ItemStack stack, int x, int y);

    @Accessor
    List<ButtonWidget> getButtons();
}

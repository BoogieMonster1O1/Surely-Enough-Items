package io.github.boogiemonster1o1.sei.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.widget.ButtonWidget;

@Mixin(ButtonWidget.class)
public interface ButtonWidgetAccessor {
    @Accessor
    int getHeight();

    @Accessor
    int getWidth();
}

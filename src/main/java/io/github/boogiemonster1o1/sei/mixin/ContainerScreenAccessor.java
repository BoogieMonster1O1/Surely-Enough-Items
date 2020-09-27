package io.github.boogiemonster1o1.sei.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.screen.ingame.ContainerScreen;

@Mixin(ContainerScreen.class)
public interface ContainerScreenAccessor {
    @Accessor
    int getContainerWidth();

    @Accessor
    int getContainerHeight();

    @Accessor("x")
    int getX();

    @Accessor("y")
    int getY();
}

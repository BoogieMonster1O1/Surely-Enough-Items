package io.github.boogiemonster1o1.sei;

import io.github.boogiemonster1o1.sei.api.ContainerScreenEvents;
import io.github.boogiemonster1o1.sei.gui.ContainerOverlay;
import io.github.boogiemonster1o1.sei.mixin.ContainerScreenAccessor;
import io.github.boogiemonster1o1.sei.mixin.ScreenAccessor;
import io.github.boogiemonster1o1.sei.util.RenderHelper;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.container.Slot;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class SurelyEnoughItems implements ModInitializer {
    private static final Logger LOGGER = LogManager.getLogger(SurelyEnoughItems.class);
    private static final ContainerOverlay OVERLAY = new ContainerOverlay();
    public static ItemStacks ITEM_STACKS = new ItemStacks();

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
        ContainerScreenEvents.INIT.register(screen -> {
            OVERLAY.init(((ContainerScreenAccessor) screen).getX(), ((ContainerScreenAccessor) screen).getY(), ((ContainerScreenAccessor) screen).getContainerWidth(), ((ContainerScreenAccessor) screen).getContainerHeight(), screen.width, screen.height, ((ScreenAccessor) screen).getButtons());
        });
        ContainerScreenEvents.POST_RENDER.register((screen, mouseX, mouseY, delta) -> {
            OVERLAY.drawScreen(MinecraftClient.getInstance(), mouseX, mouseY);
            Slot slot = ((ContainerScreenAccessor) screen).getFocusedSlot();
            if (slot != null && slot.hasStack()) {
                RenderHelper.renderToolTip(slot.getStack(), mouseX, mouseY);
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.currentScreen instanceof ContainerScreen) {
                OVERLAY.handleInput(client);
            }
        });
    }

    public static void log(Level level, String string, Object... args) {
        LOGGER.log(level, "[SurelyEnoughItems] " + string, args);
    }
}

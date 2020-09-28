package io.github.boogiemonster1o1.sei;

import io.github.boogiemonster1o1.sei.api.ContainerScreenEvents;
import io.github.boogiemonster1o1.sei.gui.ContainerOverlay;
import io.github.boogiemonster1o1.sei.mixin.ContainerScreenAccessor;
import io.github.boogiemonster1o1.sei.mixin.ScreenAccessor;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;

import net.fabricmc.api.ModInitializer;

public class SurelyEnoughItems implements ModInitializer {
    private static final Logger LOGGER = LogManager.getLogger(SurelyEnoughItems.class);
    private static final ContainerOverlay OVERLAY = new ContainerOverlay();
    public static ItemStacks ITEM_STACKS = new ItemStacks();

    @Override
    public void onInitialize() {
        log(Level.INFO,"Initializing");
        ContainerScreenEvents.INIT.register(screen -> {
            if (shouldRender(screen)) {
                OVERLAY.init(((ContainerScreenAccessor) screen).getX(), ((ContainerScreenAccessor) screen).getY(), ((ContainerScreenAccessor) screen).getContainerWidth(), ((ContainerScreenAccessor) screen).getContainerHeight(), screen.width, screen.height, ((ScreenAccessor) screen).getButtons());
            }
        });
        ContainerScreenEvents.PRE_RENDER.register((screen, mouseX, mouseY, delta) -> {
            OVERLAY.drawScreen(MinecraftClient.getInstance(), mouseX, mouseY);
        });
        ContainerScreenEvents.POST_RENDER.register((screen, mouseX, mouseY, delta) -> {
            OVERLAY.drawTooltips(MinecraftClient.getInstance(), mouseX, mouseY);
        });
        ContainerScreenEvents.BUTTON_PRESSED_SIMPLE.register((screen, button) -> {
            OVERLAY.actionPerformed(button);
        });
    }

    public static void log(Level level, String string, Object... args) {
        LOGGER.log(level, "[SurelyEnoughItems] " + string, args);
    }

    private static boolean shouldRender(ContainerScreen gui) {
        return (gui != null);
    }
}

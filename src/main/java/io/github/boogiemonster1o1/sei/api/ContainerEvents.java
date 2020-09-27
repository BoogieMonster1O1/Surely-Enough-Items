package io.github.boogiemonster1o1.sei.api;

import java.util.function.Consumer;

import net.minecraft.client.gui.screen.ingame.ContainerScreen;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class ContainerEvents {
    private ContainerEvents() {
    }

    public static final Event<Consumer<ContainerScreen>> INIT = EventFactory.createArrayBacked(Consumer.class, (listeners) -> screen -> {
        for (Consumer<ContainerScreen> consumer : listeners) {
            consumer.accept(screen);
        }
    });

    public static final Event<QuadConsumer<ContainerScreen, Integer, Integer, Float>> RENDER = EventFactory.createArrayBacked(QuadConsumer.class, (listeners) -> (screen, x, y, delta) -> {
        for (QuadConsumer<ContainerScreen, Integer, Integer, Float> consumer : listeners) {
            consumer.accept(screen, x, y, delta);
        }
    });
}

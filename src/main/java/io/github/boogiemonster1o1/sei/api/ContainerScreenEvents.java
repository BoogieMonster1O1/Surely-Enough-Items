package io.github.boogiemonster1o1.sei.api;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class ContainerScreenEvents {
    private ContainerScreenEvents() {
    }

    public static final Event<Consumer<ContainerScreen>> INIT = EventFactory.createArrayBacked(Consumer.class, (listeners) -> screen -> {
        for (Consumer<ContainerScreen> consumer : listeners) {
            consumer.accept(screen);
        }
    });

    public static final Event<TetraConsumer<ContainerScreen, Integer, Integer, Float>> POST_RENDER = EventFactory.createArrayBacked(TetraConsumer.class, (listeners) -> (screen, x, y, delta) -> {
        for (TetraConsumer<ContainerScreen, Integer, Integer, Float> consumer : listeners) {
            consumer.accept(screen, x, y, delta);
        }
    });

    public static final Event<TetraConsumer<ContainerScreen, Integer, Integer, Float>> PRE_RENDER = EventFactory.createArrayBacked(TetraConsumer.class, (listeners) -> (screen, x, y, delta) -> {
        for (TetraConsumer<ContainerScreen, Integer, Integer, Float> consumer : listeners) {
            consumer.accept(screen, x, y, delta);
        }
    });

    public static final Event<BiConsumer<ContainerScreen, ButtonWidget>> BUTTON_PRESSED_SIMPLE = EventFactory.createArrayBacked(BiConsumer.class, (listeners) -> (screen, button) -> {
        for (BiConsumer<ContainerScreen, ButtonWidget> consumer : listeners) {
            consumer.accept(screen, button);
        }
    });
}

package io.github.boogiemonster1o1.sei;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SurelyEnoughItems implements ModInitializer {

    static final Logger LOGGER = LogManager.getLogger(SurelyEnoughItems.class);
    public static ItemStacks ITEM_STACKS;

    @Override
    public void onInitialize() {
        LOGGER.info("[{}] Initializing","SurelyEnoughItems");
    }
}

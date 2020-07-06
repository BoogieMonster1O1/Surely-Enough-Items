package io.github.boogiemonster1o1.sei.mixin;

import io.github.boogiemonster1o1.sei.util.SEIInventory;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(CreativeInventoryScreen.class)
public class CreativeInventoryScreenMixin implements SEIInventory {
    @Inject(method="keyPressed",at=@At("HEAD"))
    private void keyPress(char character, int code,CallbackInfo ci){
        if(code == Keyboard.KEY_LEFT){
            if(this.getPageNum() >= 1){
                this.setPageNum(this.getPageNum() - 1);
                this.updateButtonEnabled();
            }
        }

        if(code == Keyboard.KEY_RIGHT){
            if(this.getPageNum() <= 4){
                this.setPageNum(this.getPageNum() + 1);
                this.updateButtonEnabled();
            }
        }
    }
}

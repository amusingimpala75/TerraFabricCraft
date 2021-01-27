package com.github.amusingimpala75.terrafabriccraft.mixin;

import com.github.amusingimpala75.terrafabriccraft.screenhandler.KnappingScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ScreenHandler.class)
public class CancelShiftClickKnapping {

    @Redirect(method = "method_30010", at=@At(value = "INVOKE", target = "Lnet/minecraft/screen/ScreenHandler;transferSlot(Lnet/minecraft/entity/player/PlayerEntity;I)Lnet/minecraft/item/ItemStack;"))
    public ItemStack removeForKnapping(ScreenHandler handler, PlayerEntity player, int i) {
        if ((Object)this instanceof KnappingScreenHandler) {
            return ItemStack.EMPTY;
        }
        return ((ScreenHandler)(Object)this).transferSlot(player, i);
    }
}

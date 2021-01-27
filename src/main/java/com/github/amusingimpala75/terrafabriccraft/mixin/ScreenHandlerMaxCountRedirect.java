package com.github.amusingimpala75.terrafabriccraft.mixin;

import com.github.amusingimpala75.terrafabriccraft.screenhandler.LogPileScreenHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(ScreenHandler.class)
public class ScreenHandlerMaxCountRedirect {

    @Shadow @Final public List<Slot> slots;
    private int index = 0;

    /*@Redirect(method = "insertItem", at=@At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getMaxCount()I", ordinal = 0))
    public int changeItemMaxToSlotMaxIfLess0(ItemStack stack) {
        return change(stack);
    }
    @Redirect(method = "insertItem", at=@At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getMaxCount()I", ordinal = 1))
    public int changeItemMaxToSlotMaxIfLess1(ItemStack stack) {
        return change(stack);
    }
    @Redirect(method = "insertItem", at=@At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getMaxCount()I", ordinal = 2))
    public int changeItemMaxToSlotMaxIfLess2(ItemStack stack) {
        return change(stack);
    }
    @Redirect(method = "insertItem", at=@At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getMaxCount()I", ordinal = 3))
    public int changeItemMaxToSlotMaxIfLess3(ItemStack stack) {
        return change(stack);
    }*/
    @Redirect(method = "insertItem", at=@At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getMaxCount()I"))
    public int changeItemMaxToSlotMaxIfLess2(ItemStack stack) {
        return change(stack);
    }

    @ModifyArg(method = "insertItem", at=@At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;"), index = 0)
    public int getIndex(int original) {
        this.index = original;
        return original;
    }

    private int change(ItemStack stack) {
        Slot slot = this.slots.get(index);
        if ((ScreenHandler)(Object)this instanceof LogPileScreenHandler && slot.getMaxItemCount() < stack.getMaxCount()) {
            return slot.getMaxItemCount();
        } else {
            return stack.getMaxCount();
        }
    }
}

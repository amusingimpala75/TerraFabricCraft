package com.github.amusingimpala75.terrafabriccraft.screenhandler;

import com.github.amusingimpala75.terrafabriccraft.item.LogBlockItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class LogPileSlot extends Slot {

    public LogPileSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.getItem() instanceof LogBlockItem;
    }

    @Override
    public int getMaxItemCount() {
        return 4;
    }
}

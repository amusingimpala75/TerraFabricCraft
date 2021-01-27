package com.github.amusingimpala75.terrafabriccraft.screenhandler;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class KnappingResultSlot extends Slot {

    @Override
    protected void onTake(int amount) {
        super.onTake(amount);
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                ((KnappingScreenHandler)this.inventory).clickSquare(x, y);
            }
        }
        ((KnappingScreenHandler)this.inventory).reCheckRecipes();
    }

    public KnappingResultSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }
}

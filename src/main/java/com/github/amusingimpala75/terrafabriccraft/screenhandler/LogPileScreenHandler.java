package com.github.amusingimpala75.terrafabriccraft.screenhandler;

import com.github.amusingimpala75.terrafabriccraftcore.screenhandler.AbstractLogPileScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.slot.Slot;

public class LogPileScreenHandler extends AbstractLogPileScreenHandler {

    private final Inventory inv;

    public LogPileScreenHandler(int syncId, PlayerInventory playerInv) {
        this(syncId, playerInv, new SimpleInventory(4));
    }

    public LogPileScreenHandler(int syncId, PlayerInventory playerInv, Inventory inv) {
        super(TerraFabricCraftScreenHandlers.LOG_PILE_HANDLER, syncId);
        this.inv = inv;
        int m;
        int l;
        int index = 0;
        for (m = 0; m < 2; ++m) {
            for (l = 0; l < 2; ++l) {
                this.addSlot(new LogPileSlot(inv, index, (8 + l * 18)+63, ((84 + m * 18)-50)-18));
                //this.addSlot(new Slot(inv, index, (8 + l * 18)+63, ((84 + m * 18)-50)-18));
                index++;
            }
        }
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInv, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInv, m, 8 + m * 18, 142));
        }
        inv.onOpen(playerInv.player);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inv.canPlayerUse(player);
    }

    @Override
    public void close(PlayerEntity player) {
        inv.onClose(player);
    }
}

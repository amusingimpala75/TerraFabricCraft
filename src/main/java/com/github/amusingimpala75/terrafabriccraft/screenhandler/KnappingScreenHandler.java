package com.github.amusingimpala75.terrafabriccraft.screenhandler;

import com.github.amusingimpala75.terrafabriccraft.item.KnappingItem;
import com.github.amusingimpala75.terrafabriccraft.recipe.KnappingRecipe;
import com.github.amusingimpala75.terrafabriccraft.recipe.KnappingType;
import com.github.amusingimpala75.terrafabriccraft.recipe.TerraFabricCraftRecipes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Optional;

//TODO: Fix s2c and c2s
public class KnappingScreenHandler extends ScreenHandler implements Inventory {

    private ItemStack outputSlot;
    private boolean[][] clickedSquares = new boolean[5][5];
    private final KnappingType knappingType;
    public final KnappingItem item;
    public PlayerEntity player;
    private boolean destroyedRock = false;
    private ScreenHandlerContext ctx;

    public KnappingScreenHandler(int syncId, PlayerInventory playerInv) {
        this(syncId, playerInv, KnappingType.STONE, ScreenHandlerContext.EMPTY);
    }

    public KnappingScreenHandler(int syncId, PlayerInventory playerInv, KnappingType type, ScreenHandlerContext ctx) {
        super(TerraFabricCraftScreenHandlers.KNAPPING_HANDLER, syncId);
        this.knappingType = type;
        this.addSlot(new KnappingResultSlot(this, 0, 128, 35));
        int m;
        int l;
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInv, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInv, m, 8 + m * 18, 142));
        }
        this.item = (KnappingItem) playerInv.player.getMainHandStack().getItem();
        this.outputSlot = ItemStack.EMPTY;
        this.player = playerInv.player;
        this.ctx = ctx;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return outputSlot.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return outputSlot;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ArrayList<ItemStack> list = new ArrayList<>();
        list.add(this.outputSlot);
        return Inventories.splitStack(list, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        ArrayList<ItemStack> list = new ArrayList<>();
        list.add(this.outputSlot);
        return Inventories.removeStack(list, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.outputSlot = stack;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        outputSlot = ItemStack.EMPTY;
    }

    public void clickSquare(int x, int y) {
        clickedSquares[x][y] = true;
    }

    public boolean[][] getClickedSquares() {
        return clickedSquares;
    }

    public KnappingType getKnappingType() {
        return knappingType;
    }

    public boolean getClickedSquareAt(int x, int y) {
        return clickedSquares[x][y];
    }

    public boolean isRockDestroyed() {
        return destroyedRock;
    }

    private static void destroyRockSided(KnappingScreenHandler inv, PlayerEntity player) {
        if (player.world.isClient) {
            return;
        }
        inv.destroyedRock = true;
        player.getMainHandStack().decrement(1);
    }

    private static void reCheckRecipesSided(KnappingScreenHandler inv, PlayerEntity player) {
        World world = player.world;
        System.out.println("Checking for recipes!");
        if (!world.isClient()) {
            // Or use .getAllMatches if you want all of the matches
            String row = "";
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    char toAdd = inv.clickedSquares[x][y] ? ' ' : 'O';
                    row = row + toAdd;
                }
                System.out.println(row);
                row = "";
            }
            Optional<KnappingRecipe> match = world.getRecipeManager()
                    .getFirstMatch(TerraFabricCraftRecipes.KNAPPING_RECIPE_TYPE, inv, world);

            match.ifPresent(knappingRecipe -> {
                inv.slots.get(0).setStack(knappingRecipe.getOutput());
                System.out.println(knappingRecipe.getOutput().getItem());
            });
        }
    }

    public void reCheckRecipes() {
        System.out.println("Running ctx for recipes");
        this.ctx.run((world, blockPos) -> {
            reCheckRecipesSided(this, this.player);
        });
    }

    public void destroyRock() {
        System.out.println("Running ctx for smashing");
        this.ctx.run((world, blockPos) -> {
            destroyRockSided(this, this.player);
        });
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        return super.onButtonClick(player, id);
    }
}

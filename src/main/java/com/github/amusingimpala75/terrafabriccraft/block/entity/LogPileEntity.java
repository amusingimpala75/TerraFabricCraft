package com.github.amusingimpala75.terrafabriccraft.block.entity;

import com.github.amusingimpala75.terrafabriccraft.screenhandler.LogPileScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Nullable;

public class LogPileEntity extends BlockEntity implements Inventory, NamedScreenHandlerFactory {

    private DefaultedList<ItemStack> inventory;
    private int viewers = 0;

    public LogPileEntity() {
        super(TerraFabricCraftBlockEntities.LOG_PILE_ENTITY);
        this.inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return this.inventory.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.inventory.set(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return player.squaredDistanceTo((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("container.log_pile");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new LogPileScreenHandler(syncId, inv, this);
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        Inventories.fromTag(tag, this.inventory);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        Inventories.toTag(tag, this.inventory);
        return tag;
    }

    @Override
    public int getMaxCountPerStack() {
        return 4;
    }

    @Override
    public void onClose(PlayerEntity entity) {
        viewers--;
        System.out.println(viewers);
        if(viewers == 0 && inventory.get(0).equals(ItemStack.EMPTY) && inventory.get(1).equals(ItemStack.EMPTY) && inventory.get(2).equals(ItemStack.EMPTY) && inventory.get(3).equals(ItemStack.EMPTY)) {
            world.breakBlock(this.getPos(), false);
        }
    }

    @Override
    public void onOpen(PlayerEntity player) {
        this.viewers++;
    }
}

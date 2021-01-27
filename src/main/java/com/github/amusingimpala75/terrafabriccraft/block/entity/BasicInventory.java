package com.github.amusingimpala75.terrafabriccraft.block.entity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface BasicInventory extends Inventory {

    DefaultedList<ItemStack> getInventory();

    @Override
    default int size() {
        return getInventory().size();
    }

    @Override
    default boolean isEmpty() {
        return getInventory().isEmpty();
    }

    @Override
    default ItemStack removeStack(int slot, int amount) {
        ItemStack result = Inventories.splitStack(getInventory(), slot, amount);
        if (!isEmpty()) {
            markDirty();
        }
        return result;
    }

    @Override
    default ItemStack removeStack(int slot) {
        return Inventories.removeStack(getInventory(), slot);
    }

    @Override
    default void setStack(int slot, ItemStack stack) {
        getInventory().set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }
    }

    @Override
    default boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    default ItemStack getStack(int slot) {
        return getInventory().get(slot);
    }

    @Override
    default void clear() {
        getInventory().clear();
    }
}

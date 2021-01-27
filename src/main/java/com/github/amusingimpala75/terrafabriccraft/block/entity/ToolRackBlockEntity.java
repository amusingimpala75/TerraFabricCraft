package com.github.amusingimpala75.terrafabriccraft.block.entity;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ToolRackBlockEntity extends BlockEntity implements BasicInventory {

    private DefaultedList<ItemStack> inventory;

    public ToolRackBlockEntity() {
        super(TerraFabricCraftBlockEntities.TOOL_RACK_BLOCK_ENTITY);
        this.inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    }
    public String getTextureId() {
        String wood = Registry.BLOCK.getId(this.world.getBlockState(this.pos).getBlock()).getPath();
        String type = wood.substring(0, wood.indexOf("_tool_rack"));
        return "blocks/wood/"+type+"_plank";
    }

    @Override
    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    public void onUse(PlayerEntity player, World world, Hand hand) {

    }

}

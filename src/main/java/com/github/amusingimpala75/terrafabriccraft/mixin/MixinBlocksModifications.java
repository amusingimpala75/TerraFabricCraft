package com.github.amusingimpala75.terrafabriccraft.mixin;

import com.github.amusingimpala75.terrafabriccraft.block.TerraFabricCraftBlocks;
import com.github.amusingimpala75.terrafabriccraft.ducks.BlockDuck;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Blocks.class)
public class MixinBlocksModifications {

    @Inject(at=@At("HEAD"), method = "register", cancellable = true)
    private static void modifyCertainBlocks(String id, Block block, CallbackInfoReturnable<Block> info) {
        switch (id) {
            case "cobblestone":
            case "dirt":
                info.setReturnValue(Registry.register(Registry.BLOCK, id, ((BlockDuck) (((BlockDuck) (block)).canCollapse())).doesInstantlyCollapse()));
                break;
            case "stone":
                info.setReturnValue(Registry.register(Registry.BLOCK, id, ((BlockDuck) (((BlockDuck) (block)).canCollapse())).collapsesToOtherBlock(Blocks.COBBLESTONE)));
                break;
            case "andesite":
                info.setReturnValue(Registry.register(Registry.BLOCK, id, ((BlockDuck) (((BlockDuck) (block)).canCollapse())).collapsesToOtherBlock(TerraFabricCraftBlocks.ANDESITE_COBBLE)));
                break;
            case "diorite":
                info.setReturnValue(Registry.register(Registry.BLOCK, id, ((BlockDuck) (((BlockDuck) (block)).canCollapse())).collapsesToOtherBlock(TerraFabricCraftBlocks.DIORITE_COBBLE)));
                break;
            case "granite":
                info.setReturnValue(Registry.register(Registry.BLOCK, id, ((BlockDuck) (((BlockDuck) (block)).canCollapse())).collapsesToOtherBlock(TerraFabricCraftBlocks.GRANITE_COBBLE)));
                break;
            case "grass_block":
                info.setReturnValue(Registry.register(Registry.BLOCK, id, ((BlockDuck) (((BlockDuck) (((BlockDuck) (block)).canCollapse())).collapsesToOtherBlock(Blocks.DIRT))).doesInstantlyCollapse()));
                break;
        }
    }
}

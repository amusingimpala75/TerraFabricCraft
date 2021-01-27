package com.github.amusingimpala75.terrafabriccraft.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.PillarBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Blocks.class)
public interface BlocksLogInvoker {

    @Invoker
    static PillarBlock invokeCreateLogBlock(MaterialColor topMaterialColor, MaterialColor sideMaterialColor) {
        throw new AssertionError();
    }

}

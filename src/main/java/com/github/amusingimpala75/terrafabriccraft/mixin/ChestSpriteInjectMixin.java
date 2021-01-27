package com.github.amusingimpala75.terrafabriccraft.mixin;

import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraft;
import com.github.amusingimpala75.terrafabriccraft.block.entity.TFCChestEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.client.render.TexturedRenderLayers.CHEST_ATLAS_TEXTURE;

@Mixin(TexturedRenderLayers.class)
public class ChestSpriteInjectMixin {
    @Inject(at=@At("HEAD"), method = "getChestTexture(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/block/enums/ChestType;Z)Lnet/minecraft/client/util/SpriteIdentifier;", cancellable = true)
    private static void getChestTexture(BlockEntity blockEntity, ChestType type, boolean christmas, CallbackInfoReturnable<SpriteIdentifier> ci) {
        if (blockEntity instanceof TFCChestEntity) {
            String variant = ((TFCChestEntity)blockEntity).getTexturePath();
            ci.setReturnValue(new SpriteIdentifier(CHEST_ATLAS_TEXTURE, TerraFabricCraft.getTextureId(variant)));
        }
    }
}

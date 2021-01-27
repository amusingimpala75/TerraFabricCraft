package com.github.amusingimpala75.terrafabriccraft.mixin;

import com.github.amusingimpala75.terrafabriccraft.item.ModdedArmorItem;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorFeatureRenderer.class)
public class ArmourRenderWidenerMixin {
    @Inject(at=@At("HEAD"), method = "getArmorTexture", cancellable = true)
    public void widenDirectory(ArmorItem armorItem, boolean bl, String string, CallbackInfoReturnable<Identifier> info) {
        if (armorItem instanceof ModdedArmorItem) {
            info.setReturnValue(((ModdedArmorItem) armorItem).getResLoc((bl ? 2 : 1)));
        }
    }

}

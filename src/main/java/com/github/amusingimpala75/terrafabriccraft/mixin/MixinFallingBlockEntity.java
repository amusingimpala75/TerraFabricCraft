package com.github.amusingimpala75.terrafabriccraft.mixin;

import com.github.amusingimpala75.terrafabriccraft.config.TerraFabricCraftConfig;
import com.github.amusingimpala75.terrafabriccraft.ducks.BlockDuck;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FallingBlockEntity.class)
public abstract class MixinFallingBlockEntity extends Entity {

    @Shadow private BlockState block;

    public MixinFallingBlockEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (((BlockDuck)(this.block.getBlock())).collapses() && this.fallDistance > 3) {
            int damage = AutoConfig.getConfigHolder(TerraFabricCraftConfig.class).getConfig().rockDamage;
            player.damage(DamageSource.FALLING_BLOCK, (((float)damage)/3)*(this.fallDistance-3));
        }
    }
}

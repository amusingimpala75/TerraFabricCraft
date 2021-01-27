package com.github.amusingimpala75.terrafabriccraft.item;

import com.github.amusingimpala75.terrafabriccraft.material.StoneToolMaterials;
import com.github.amusingimpala75.terrafabriccraft.recipe.KnappingType;
import com.github.amusingimpala75.terrafabriccraft.screenhandler.KnappingScreenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PebbleItem extends Item implements KnappingItem, NamedScreenHandlerFactory {
    private final StoneToolMaterials material;
    private final String textureLocation;

    public PebbleItem(Settings settings, StoneToolMaterials material, String textureLocation) {
        super(settings);
        this.material = material;
        this.textureLocation = textureLocation;
    }

    public StoneToolMaterials getMaterial() {
        return material;
    }

    @Override
    public String getTextureLocation() {
        return textureLocation;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getMainHandStack().getItem() instanceof PebbleItem && user.getMainHandStack().getCount() > 1) {
            user.openHandledScreen(this);
        }
        return super.use(world, user, hand);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("container.knapping");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new KnappingScreenHandler(syncId, inv, KnappingType.STONE, ScreenHandlerContext.create(player.world, player.getBlockPos()));
    }
}

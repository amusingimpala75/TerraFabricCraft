package com.github.amusingimpala75.terrafabriccraft.item;

import com.github.amusingimpala75.terrafabriccraft.block.propertyenums.OreQualityEnum;
import net.minecraft.item.Item;

public class OreItem extends Item {
    private final OreQualityEnum quality;
    public OreItem(Settings settings, OreQualityEnum quality) {
        super(settings);
        this.quality = quality;
    }
}

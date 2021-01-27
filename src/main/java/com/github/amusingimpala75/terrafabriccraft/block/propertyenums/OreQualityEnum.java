package com.github.amusingimpala75.terrafabriccraft.block.propertyenums;

import net.minecraft.util.StringIdentifiable;

public enum OreQualityEnum implements StringIdentifiable {
    UNITS_10("units_10"),
    UNITS_15("units_15"),
    UNITS_25("units_25"),
    UNITS_35("units_35");

    public final String name;

    OreQualityEnum(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }
}

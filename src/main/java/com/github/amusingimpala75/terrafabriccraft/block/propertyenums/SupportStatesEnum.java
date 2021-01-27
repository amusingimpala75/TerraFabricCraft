package com.github.amusingimpala75.terrafabriccraft.block.propertyenums;

import net.minecraft.util.StringIdentifiable;

public enum SupportStatesEnum implements StringIdentifiable {
    VERTICAL("vertical"),
    NORTH("north"),
    EAST("east"),
    SOUTH("south"),
    WEST("west"),
    NORTH_SOUTH("north_south"),
    EAST_WEST("east_west");

    private final String name;

    SupportStatesEnum(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

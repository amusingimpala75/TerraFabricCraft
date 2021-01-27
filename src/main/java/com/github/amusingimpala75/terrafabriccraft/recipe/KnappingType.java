package com.github.amusingimpala75.terrafabriccraft.recipe;

import net.minecraft.util.StringIdentifiable;

public enum KnappingType implements StringIdentifiable {
    STONE("stone", null, null),
    CLAY("clay", "textures/items/pottery/clay_flat_light.png", "textures/items/pottery/clay_flat_dark.png"),
    LEATHER("leather", "textures/items/tools/flat_leather.png", null),
    FIRE_CLAY("fire_clay", "textures/items/pottery/clay_flat_fire.png", "textures/items/pottery/clay_flat_dark_fire.png");

    private final String name;
    private final String clickedTextureLocation;
    private final String unclickedTextureLocation;

    KnappingType(String name, String unclickedTextureLocation, String clickedTextureLocation) {
        this.name = name;
        this.clickedTextureLocation = clickedTextureLocation;
        this.unclickedTextureLocation = unclickedTextureLocation;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static KnappingType fromString(String stringFrom) {
        switch (stringFrom) {
            case "leather": return LEATHER;
            case "clay": return CLAY;
            case "fire_clay": return FIRE_CLAY;
            default: return STONE;
        }
    }

    public String getClickedTextureLocation() {
        return this.clickedTextureLocation;
    }

    public String getUnclickedTextureLocation() {
        return this.unclickedTextureLocation;
    }
}

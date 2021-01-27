package com.github.amusingimpala75.terrafabriccraft.item;

public enum DamageTypeEnum {
    CRUSHING("crushing"),
    SLASHING("slashing"),
    PIERCING("piercing");

    private final String name;

    DamageTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

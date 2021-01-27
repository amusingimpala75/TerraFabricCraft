package com.github.amusingimpala75.terrafabriccraft.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.util.Identifier;

public class ModdedArmorItem extends ArmorItem {

    private final Identifier resLoc;

    public ModdedArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings, Identifier resourceLocation) {
        super(material, slot, settings);
        this.resLoc = resourceLocation;
    }

    @Environment(EnvType.CLIENT)
    public Identifier getResLoc(int layer) {
        String namespace = resLoc.getNamespace();
        String path = resLoc.getPath();
        path = path+"_"+layer+".png";
        return new Identifier(namespace, path);
    }
}

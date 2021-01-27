package com.github.amusingimpala75.terrafabriccraft.datagen;

import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraft;
import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraftConstants;
import com.swordglowsblue.artifice.api.Artifice;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class TerraFabricCraftDataGen {

    public static void registerData() {
        if (TerraFabricCraftConstants.datagen) {
            Artifice.registerDataPack(TerraFabricCraft.getId("terrafabriccraft-data"), dataBuilder -> {
                dataBuilder.addBlockTag(new Identifier("wooden_fences"), tag -> {
                    tag.replace(false);
                    List<Identifier> values = new ArrayList<>();
                    for (String wood : TerraFabricCraftConstants.WOOD_TYPES) {
                        values.add(TerraFabricCraft.getId(wood+"_fence"));
                    }
                    Identifier[] values2 = values.toArray(new Identifier[0]);
                    tag.values(values2);
                });
            });
        }
    }
}

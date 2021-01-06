package com.github.amusingimpala75.terrafabriccraft.client;

import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraft;
import com.github.amusingimpala75.terrafabriccraft.block.TerraFabricCraftBlocks;
import com.github.amusingimpala75.terrafabriccraft.config.TerraFabricCraftConfig;
import com.swordglowsblue.artifice.api.Artifice;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Environment(EnvType.CLIENT)
//TODO: Turn datagen off, copy dump to assets with every release
public class TerraFabricCraftClient implements ClientModInitializer {

    private static final Boolean datagen = true;

    public static final String[] VANILLA_SUPPORT_LIST = {
            "oak",
            "spruce",
            "birch",
            "jungle",
            "acacia",
            "dark_oak",
            "crimson",
            "warped"
    };

    public static final String[] VANILLA_COBBLE_LIST = {
            "andesite",
            "diorite",
            "granite"
    };

    @Override
    public void onInitializeClient() {
        TerraFabricCraftConfig config = TerraFabricCraft.getConfig();
        if (datagen) {
            String[] possibleDirectionsConnecting = {"north", "east", "south", "west"};
            String[] possibleDirectionsHorizontal = {"north_south", "east_west"};
            Artifice.registerAssetPack(TerraFabricCraft.getId("vanilla_tfc_caveins_resources"), rpBuilder -> {

                for (String currentName : VANILLA_SUPPORT_LIST) {

                    rpBuilder.addBlockState(TerraFabricCraft.getId("vanilla_" + currentName + "_support"), state -> {
                        state.variant("support_state=vertical", model -> model.
                                model(TerraFabricCraft.getId("block/vertical_vanilla_" + currentName + "_support"))
                        );

                        for (int k = 0; k < possibleDirectionsConnecting.length; k++) {
                            int finalK = k;

                            state.variant("support_state=" + possibleDirectionsConnecting[k], model -> model
                                    .model(TerraFabricCraft.getId("block/connecting_vanilla_" + currentName + "_support"))
                                    .rotationY(finalK * 90)
                            );
                        }
                        for (int j = 0; j < possibleDirectionsHorizontal.length; j++) {
                            int finalJ = j;

                            state.variant("support_state=" + possibleDirectionsHorizontal[j], model -> model
                                    .model(TerraFabricCraft.getId("block/horizontal_vanilla_" + currentName + "_support"))
                                    .rotationY(finalJ == 0 ? 0 : 90)
                            );
                        }
                    });

                    Identifier logId = new Identifier("minecraft", "block/" + currentName + (currentName.equals("crimson") || currentName.equals("warped") ? "_stem" : "_log"));

                    rpBuilder.addBlockModel(TerraFabricCraft.getId("connecting_vanilla_" + currentName + "_support"), model -> model
                            .parent(TerraFabricCraft.getId("block/connecting_support"))
                            .texture("texture", logId));

                    rpBuilder.addBlockModel(TerraFabricCraft.getId("horizontal_vanilla_" + currentName + "_support"), model -> model
                            .parent(TerraFabricCraft.getId("block/horizontal_support"))
                            .texture("texture", logId));

                    rpBuilder.addBlockModel(TerraFabricCraft.getId("vertical_vanilla_" + currentName + "_support"), model -> model
                            .parent(TerraFabricCraft.getId("block/vertical_support"))
                            .texture("texture", logId));

                    rpBuilder.addItemModel(TerraFabricCraft.getId("vanilla_" + currentName + "_support"), model -> model
                            .parent(TerraFabricCraft.getId("block/vertical_vanilla_" + currentName + "_support")));
                }

                for (String currentName : VANILLA_COBBLE_LIST) {

                    rpBuilder.addBlockState(TerraFabricCraft.getId("vanilla_" + currentName + "_cobblestone"), state -> state
                            .variant("", model -> model.model(TerraFabricCraft.getId("block/vanilla_" + currentName + "_cobblestone")))
                    );

                    rpBuilder.addBlockModel(TerraFabricCraft.getId("vanilla_" + currentName + "_cobblestone"), model -> model
                            .parent(new Identifier("block/cube_all"))
                            .texture("all", TerraFabricCraft.getId("block/vanilla_" + currentName + "_cobblestone"))
                    );

                    rpBuilder.addItemModel(TerraFabricCraft.getId("vanilla_" + currentName + "_cobblestone"), model -> model
                            .parent(TerraFabricCraft.getId("block/vanilla_" + currentName + "_cobblestone"))
                    );

                }

                for (Map.Entry<String, List<String>> STONE_ORE_MAP : TerraFabricCraftBlocks.STONE_ORE_MAP.entrySet()) {
                    //Make Stone and Cobblestone models, blockstates, etc.
                    String stone_type = STONE_ORE_MAP.getKey();
                    rpBuilder.addBlockState(TerraFabricCraft.getId(stone_type + "_cobblestone"), state -> state
                            .variant("", model -> model.model(TerraFabricCraft.getId("block/"+stone_type + "_cobblestone")))
                    );

                    rpBuilder.addBlockModel(TerraFabricCraft.getId(stone_type + "_cobblestone"), model -> model
                            .parent(new Identifier("block/cube_all"))
                            .texture("all", TerraFabricCraft.getTextureId("blocks/rocks/"+stone_type + "_cobble"))
                    );

                    rpBuilder.addItemModel(TerraFabricCraft.getId(stone_type + "_cobblestone"), model -> model
                            .parent(TerraFabricCraft.getId("block/"+stone_type + "_cobblestone"))
                    );

                    rpBuilder.addBlockState(TerraFabricCraft.getId(stone_type), state -> state
                            .variant("", model -> model.model(TerraFabricCraft.getId("block/" + stone_type)))
                    );

                    rpBuilder.addBlockModel(TerraFabricCraft.getId(stone_type), model -> model
                            .parent(new Identifier("block/cube_all"))
                            .texture("all", TerraFabricCraft.getTextureId("blocks/rocks/" + stone_type + "_raw"))
                    );


                    rpBuilder.addItemModel(TerraFabricCraft.getId(stone_type), model -> model
                            .parent(TerraFabricCraft.getId("block/" + stone_type))
                    );
                    //Make all ore blockstates, models, etc.
                    for (String ore : STONE_ORE_MAP.getValue()) {
                        rpBuilder.addBlockState(TerraFabricCraft.getId(ore + "_" + stone_type), state -> state
                                .variant("", model -> model.model(TerraFabricCraft.getId("block/" + ore + "_" + stone_type)))
                        );
                        rpBuilder.addBlockModel(TerraFabricCraft.getId(ore + "_" + stone_type), model -> model
                                .parent(TerraFabricCraft.getId("block/ore"))
                                .texture("rock", TerraFabricCraft.getTextureId("blocks/rocks/" + stone_type + "_raw"))
                                .texture("ore", TerraFabricCraft.getTextureId("blocks/ores/" + ore + "_ore"))
                        );
                        rpBuilder.addItemModel(TerraFabricCraft.getId(ore + "_" + stone_type), model -> model
                                .parent(TerraFabricCraft.getId("block/" + ore+ "_" + stone_type))
                        );
                    }
                    rpBuilder.addItemModel(TerraFabricCraft.getId(stone_type+"_pebble"), model -> model
                            .parent(new Identifier("minecraft", "item/generated"))
                            .texture("layer0", TerraFabricCraft.getTextureId("items/rocks/"+stone_type+"_rock"))
                    );
                }

                rpBuilder.addTranslations(TerraFabricCraft.getId("en_us"), lang -> {
                    for (String currentName : VANILLA_SUPPORT_LIST) {
                        //Will have to manually fix Dark_oak -> Dark Oak
                        lang.entry("block.tfccaveins.vanilla_" + currentName + "_support", capitalizeName(currentName) + " Support");
                    }
                    for (String currentName : VANILLA_COBBLE_LIST) {
                        lang.entry("block.tfccaveins.vanilla_" + currentName + "_cobblestone", capitalizeName(currentName) + " Cobblestone");
                    }
                });

                try {
                    rpBuilder.dumpResources("tfcCaveInsDataGenDump", "assets");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        for (Map.Entry<String, List<String>> entry : TerraFabricCraftBlocks.STONE_ORE_MAP.entrySet()) {
            String stone = entry.getKey();
            for (String ore : entry.getValue()) {
                BlockRenderLayerMap.INSTANCE.putBlock(Registry.BLOCK.get(TerraFabricCraft.getId(ore+"_"+stone)), RenderLayer.getCutout());
            }
        }
    }

    public static String capitalizeName(String oldName) {
        return String.valueOf(oldName.charAt(0)).toUpperCase(Locale.ROOT) + oldName.substring(1);
    }
}

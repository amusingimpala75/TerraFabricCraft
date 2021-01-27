package com.github.amusingimpala75.terrafabriccraft.client;

import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraft;
import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraftConstants;
import com.github.amusingimpala75.terrafabriccraft.block.TerraFabricCraftBlocks;
import com.github.amusingimpala75.terrafabriccraft.block.entity.TerraFabricCraftBlockEntities;
import com.github.amusingimpala75.terrafabriccraft.client.screen.KnappingScreen;
import com.github.amusingimpala75.terrafabriccraft.client.screen.LogPileScreen;
import com.github.amusingimpala75.terrafabriccraft.config.TerraFabricCraftConfig;
import com.github.amusingimpala75.terrafabriccraft.item.*;
import com.github.amusingimpala75.terrafabriccraft.item.vanillawrapper.AxeItem;
import com.github.amusingimpala75.terrafabriccraft.item.vanillawrapper.HoeItem;
import com.github.amusingimpala75.terrafabriccraft.item.vanillawrapper.PickaxeItem;
import com.github.amusingimpala75.terrafabriccraft.material.MetalToolMaterials;
import com.github.amusingimpala75.terrafabriccraft.material.StoneToolMaterials;
import com.github.amusingimpala75.terrafabriccraft.screenhandler.TerraFabricCraftScreenHandlers;
import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderingRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Environment(EnvType.CLIENT)
//TODO: Turn datagen off, copy dump to assets with every release
public class TerraFabricCraftClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        TerraFabricCraftConfig config = TerraFabricCraft.getConfig();
        if (TerraFabricCraftConstants.datagen) {
            Artifice.registerAssetPack(TerraFabricCraft.getId("terrafabriccraft_resources"), rpBuilder -> {

                registerStoneTypesAndOres(rpBuilder);
                registerMetalThings(rpBuilder);
                registerLogPile(rpBuilder);
                registerWoodThings(rpBuilder);
                registerGems(rpBuilder);

                try {
                    rpBuilder.dumpResources("TerraFabricCraftResGenDump", "assets");
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
        ScreenRegistry.register(TerraFabricCraftScreenHandlers.LOG_PILE_HANDLER, LogPileScreen::new);
        ScreenRegistry.register(TerraFabricCraftScreenHandlers.KNAPPING_HANDLER, KnappingScreen::new);
        BlockEntityRendererRegistry.INSTANCE.register(TerraFabricCraftBlockEntities.CHEST_BLOCK_ENTITY, ChestBlockEntityRenderer::new);
        ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register(
                (atlas, registry) -> TerraFabricCraftBlocks.CHESTS.stream().forEach((chest) ->
                        registry.register(TerraFabricCraft.getTextureId(chest.getTextureId())))
        );
        ArmorRenderingRegistry.registerModel((entity, stack, slot, defaultModel) -> {
            return ;
        });
    }

    public static String capitalizeName(String oldName) {
        return String.valueOf(oldName.charAt(0)).toUpperCase(Locale.ROOT) + oldName.substring(1);
    }

    public static void registerSupport(String currentName, ArtificeResourcePack.ClientResourcePackBuilder rpBuilder) {
        String[] possibleDirectionsConnecting = {"north", "east", "south", "west"};
        String[] possibleDirectionsHorizontal = {"north_south", "east_west"};
        rpBuilder.addBlockState(TerraFabricCraft.getId(currentName + "_support"), state -> {
            state.variant("support_state=vertical", model -> model.
                    model(TerraFabricCraft.getId("block/vertical_" + currentName + "_support"))
            );
            for (int k = 0; k < possibleDirectionsConnecting.length; k++) {
                int finalK = k;
                state.variant("support_state=" + possibleDirectionsConnecting[k], model -> model
                        .model(TerraFabricCraft.getId("block/connecting_" + currentName + "_support"))
                        .rotationY(finalK * 90)
                );
            }
            for (int j = 0; j < possibleDirectionsHorizontal.length; j++) {
                int finalJ = j;
                state.variant("support_state=" + possibleDirectionsHorizontal[j], model -> model
                        .model(TerraFabricCraft.getId("block/horizontal_" + currentName + "_support"))
                        .rotationY(finalJ == 0 ? 0 : 90)
                );
            }
        });
        Identifier logId = TerraFabricCraft.getTextureId("blocks/wood/woodsheet/" + currentName);
        rpBuilder.addBlockModel(TerraFabricCraft.getId("connecting_" + currentName + "_support"), model -> model
                .parent(TerraFabricCraft.getId("block/connecting_support"))
                .texture("texture", logId));
        rpBuilder.addBlockModel(TerraFabricCraft.getId("horizontal_" + currentName + "_support"), model -> model
                .parent(TerraFabricCraft.getId("block/horizontal_support"))
                .texture("texture", logId));
        rpBuilder.addBlockModel(TerraFabricCraft.getId("vertical_" + currentName + "_support"), model -> model
                .parent(TerraFabricCraft.getId("block/vertical_support"))
                .texture("texture", logId));
        rpBuilder.addItemModel(TerraFabricCraft.getId(currentName + "_support"), model -> model
                .parent(TerraFabricCraft.getId("block/vertical_" + currentName + "_support")));
    }

    public static void registerLogPile(ArtificeResourcePack.ClientResourcePackBuilder rpBuilder) {
        rpBuilder.addBlockState(TerraFabricCraft.getId("log_pile"), state -> state
                .variant("direction=z", model -> model
                        .model(TerraFabricCraft.getId("block/log_pile"))
                        .rotationY(0))
                .variant("direction=x", model -> model
                        .model(TerraFabricCraft.getId("block/log_pile"))
                        .rotationY(90))
        );

        rpBuilder.addBlockModel(TerraFabricCraft.getId("log_pile"), model -> model
                .parent(new Identifier("block/cube"))
                .texture("north", TerraFabricCraft.getTextureId("blocks/devices/log_pile_end"))
                .texture("east", TerraFabricCraft.getTextureId("blocks/devices/log_pile_side_0"))
                .texture("south", TerraFabricCraft.getTextureId("blocks/devices/log_pile_end"))
                .texture("west", TerraFabricCraft.getTextureId("blocks/devices/log_pile_side_0"))
                .texture("up", TerraFabricCraft.getTextureId("blocks/devices/log_pile_side_1"))
                .texture("down", TerraFabricCraft.getTextureId("blocks/devices/log_pile_side_1"))
                .texture("particle", TerraFabricCraft.getTextureId("blocks/devices/log_pile_end"))
        );

        rpBuilder.addItemModel(TerraFabricCraft.getId("log_pile"), model -> model
                .parent(TerraFabricCraft.getId("block/log_pile"))
        );
    }

    public static void registerStoneTypesAndOres(ArtificeResourcePack.ClientResourcePackBuilder rpBuilder) {
        for (Map.Entry<String, List<String>> STONE_ORE_MAP : TerraFabricCraftBlocks.STONE_ORE_MAP.entrySet()) {
            //Make Stone and Cobblestone models, blockstates, etc.
            String stone_type = STONE_ORE_MAP.getKey();
            registerCube(rpBuilder, stone_type + "_cobblestone", "blocks/rocks/"+stone_type + "_cobble");

            registerCube(rpBuilder, stone_type, "blocks/rocks/" + stone_type + "_raw");

            //Make all ore blockstates, models, etc.
            for (String ore : STONE_ORE_MAP.getValue()) {
                /*rpBuilder.addBlockState(TerraFabricCraft.getId(ore + "_" + stone_type), state -> state
                        .variant("", model -> model.model(TerraFabricCraft.getId("block/" + ore + "_" + stone_type)))
                );
                rpBuilder.addBlockModel(TerraFabricCraft.getId(ore + "_" + stone_type), model -> model
                        .parent(TerraFabricCraft.getId("block/ore"))
                        .texture("rock", TerraFabricCraft.getTextureId("blocks/rocks/" + stone_type + "_raw"))
                        .texture("ore", TerraFabricCraft.getTextureId("blocks/ores/" + ore + "_ore"))
                );
                rpBuilder.addItemModel(TerraFabricCraft.getId(ore + "_" + stone_type), model -> model
                        .parent(TerraFabricCraft.getId("block/" + ore+ "_" + stone_type))
                );*/
                registerCubeOverlay(rpBuilder, ore + "_" + stone_type, "blocks/rocks/" + stone_type + "_raw", "blocks/ores/" + ore + "_ore");
            }
            rpBuilder.addItemModel(TerraFabricCraft.getId(stone_type+"_pebble"), model -> model
                    .parent(new Identifier("item/generated"))
                    .texture("layer0", TerraFabricCraft.getTextureId("items/rocks/"+stone_type+"_rock"))
            );
            //Bricks things
            registerCube(rpBuilder, stone_type + "_bricks", "blocks/rocks/"+stone_type + "_brick");
            registerCube(rpBuilder, stone_type+"_clay", "blocks/clay/clay_"+stone_type);
            registerCube(rpBuilder, stone_type+"_dirt", "blocks/soil/dirt_"+stone_type);
            registerCube(rpBuilder, stone_type+"_sand", "blocks/sand/sand_"+stone_type);
            registerCube(rpBuilder, stone_type+"_gravel", "blocks/soil/gravel_"+stone_type);
            registerCubeGrass(rpBuilder, stone_type+"_clay_grass", "blocks/clay/clay_"+stone_type);
            registerCubeGrass(rpBuilder, stone_type+"_grass", "blocks/soil/dirt_"+stone_type);
        }
        for (Pair<String, Boolean> pair : TerraFabricCraftConstants.ORES) {
            String ore = pair.getLeft();
            if (pair.getRight()) {
                rpBuilder.addItemModel(TerraFabricCraft.getId(ore+"_small"), model -> model
                        .parent(new Identifier("item/generated"))
                        .texture("layer0", TerraFabricCraft.getTextureId("items/ore/"+ore+"_small_ore"))
                );
            }
            rpBuilder.addItemModel(TerraFabricCraft.getId(ore+"_poor"), model -> model
                    .parent(new Identifier("item/generated"))
                    .texture("layer0", TerraFabricCraft.getTextureId("items/ore/poor_"+ore+"_ore"))
            );
            rpBuilder.addItemModel(TerraFabricCraft.getId(ore+"_average"), model -> model
                    .parent(new Identifier("item/generated"))
                    .texture("layer0", TerraFabricCraft.getTextureId("items/ore/"+ore+"_ore"))
            );
            rpBuilder.addItemModel(TerraFabricCraft.getId(ore+"_rich"), model -> model
                    .parent(new Identifier("item/generated"))
                    .texture("layer0", TerraFabricCraft.getTextureId("items/ore/rich_"+ore+"_ore"))
            );
        }
    }

    public static void registerWoodThings(ArtificeResourcePack.ClientResourcePackBuilder rpBuilder) {
        for (String wood : TerraFabricCraftConstants.WOOD_TYPES) {
            registerDoor(wood, rpBuilder);
            //Supports
            registerSupport(wood, rpBuilder);

            //Planks
            registerCube(rpBuilder, wood+"_planks", "blocks/wood/"+wood+"_plank");

            //Logs
            rpBuilder.addBlockState(TerraFabricCraft.getId(wood+"_log"), state -> state
                    .variant("axis=x", model -> model
                            .model(TerraFabricCraft.getId("block/"+wood+"_log_horizontal"))
                            .rotationY(90)
                            .rotationX(90))
                    .variant("axis=y", model -> model
                            .model(TerraFabricCraft.getId("block/"+wood+"_log")))
                    .variant("axis=z", model -> model
                            .model(TerraFabricCraft.getId("block/"+wood+"_log_horizontal"))
                            .rotationX(90))
            );
            rpBuilder.addBlockModel(TerraFabricCraft.getId(wood+"_log"), model -> model
                    .parent(new Identifier("block/cube_column"))
                    .texture("end", TerraFabricCraft.getTextureId("blocks/wood/trees/"+wood+"_log_top"))
                    .texture("side", TerraFabricCraft.getTextureId("blocks/wood/trees/"+wood+"_log"))
            );
            rpBuilder.addBlockModel(TerraFabricCraft.getId(wood+"_log_horizontal"), model -> model
                    .parent(new Identifier("block/cube_column_horizontal"))
                    .texture("end", TerraFabricCraft.getTextureId("blocks/wood/trees/"+wood+"_log_top"))
                    .texture("side", TerraFabricCraft.getTextureId("blocks/wood/trees/"+wood+"_log"))
            );
            rpBuilder.addItemModel(TerraFabricCraft.getId(wood+"_log"), model -> model
                    .parent(new Identifier("item/generated")).texture("layer0", TerraFabricCraft.getTextureId("items/wood/"+wood+"_log"))
            );

            //Fence
            rpBuilder.addBlockState(TerraFabricCraft.getId(wood+"_fence"), state -> state
                    .multipartCase(multi -> multi
                            .when("north", "true")
                            .apply(model -> model
                                    .model(TerraFabricCraft.getId("block/"+wood+"_fence_side"))
                                    .uvlock(true)))
                    .multipartCase(multi -> multi
                            .apply(model -> model
                                    .model(TerraFabricCraft.getId("block/"+wood+"_fence_post"))))
                    .multipartCase(multi -> multi
                            .when("east", "true")
                            .apply(model -> model
                                    .model(TerraFabricCraft.getId("block/"+wood+"_fence_side"))
                                    .rotationY(90)
                                    .uvlock(true)))
                    .multipartCase(multi -> multi
                            .when("south", "true")
                            .apply(model -> model.model(TerraFabricCraft.getId("block/"+wood+"_fence_side"))
                                    .rotationY(180)
                                    .uvlock(true)))
                    .multipartCase(multi -> multi
                            .when("west", "true")
                            .apply(model -> model.model(TerraFabricCraft.getId("block/"+wood+"_fence_side"))
                                    .rotationY(270)
                                    .uvlock(true)))
            );
            //TODO: Fix fence models
            rpBuilder.addBlockModel(TerraFabricCraft.getId(wood+"_fence_post"), model -> model
                    .parent(new Identifier("block/fence_post"))
                    .texture("texture", TerraFabricCraft.getTextureId("blocks/wood/"+wood+"_fence"))
            );
            rpBuilder.addBlockModel(TerraFabricCraft.getId(wood+"_fence_side"), model -> model
                    .parent(new Identifier("block/fence_side"))
                    .texture("texture", TerraFabricCraft.getTextureId("blocks/wood/"+wood+"_fence"))
            );
            rpBuilder.addBlockModel(TerraFabricCraft.getId(wood+"_fence_inventory"), model -> model
                    .parent(new Identifier("block/fence_inventory"))
                    .texture("texture", TerraFabricCraft.getTextureId("blocks/wood/"+wood+"_fence"))
            );
            rpBuilder.addItemModel(TerraFabricCraft.getId(wood + "_fence"), model -> model
                    .parent(TerraFabricCraft.getId("block/" + wood + "_fence_inventory"))
            );
            //TODO: Fence gates
            rpBuilder.addItemModel(TerraFabricCraft.getId(wood+"_lumber"), model -> model
                    .parent(new Identifier("item/generated"))
                    .texture("layer0", TerraFabricCraft.getTextureId("items/wood/"+wood+"_plank")));
            //Chests
            //TODO: Fix chest textures
            rpBuilder.addBlockState(TerraFabricCraft.getId(wood+"_chest"), state -> state
                    .variant("", model -> model
                            .model(TerraFabricCraft.getId("block/"+wood+"_chest")))
            );
            rpBuilder.addBlockModel(TerraFabricCraft.getId(wood+"_chest"), model -> model
                    .texture("particle", TerraFabricCraft.getTextureId("models/chest/normal_"+wood))
            );
            rpBuilder.addItemModel(TerraFabricCraft.getId(wood+"_chest"), model -> model
                    .parent(new Identifier("builtin/entity"))
                    .texture("particle", TerraFabricCraft.getTextureId("models/chest/normal_"+wood))
                    .display("gui", gui -> gui
                            .rotation(30, 45, 0)
                            .scale(0.625F, 0.625F, 0.625F)
                            .translation(0, 0, 0))
                    .display("ground", ground -> ground
                            .translation(0, 3, 0)
                            .scale(0.25F, 0.25F, 0.25F)
                            .rotation(0 , 0, 0))
                    .display("head", head -> head
                            .rotation(0, 180, 0)
                            .translation(0, 0, 0)
                            .scale(1, 1, 1))
                    .display("fixed", fixed -> fixed
                            .scale(0.5F, 0.5F, 0.5F)
                            .translation(0, 0, 0)
                            .rotation(0, 180, 0))
                    .display("thirdperson_righthand", thirdP -> thirdP
                            .rotation(75, 315, 0)
                            .translation(0, 2.5F, 0)
                            .scale(0.375F, 0.375F, 0.375F))
                    .display("firstperson_righthand", firstP -> firstP
                            .rotation(0, 315, 0)
                            .translation(0, 0, 0)
                            .scale(0.4F, 0.4F, 0.4F))
            );
        }
    }

    public static void registerDoor(String name, ArtificeResourcePack.ClientResourcePackBuilder rpBuilder) {
        //TODO
    }

    public static void registerGems(ArtificeResourcePack.ClientResourcePackBuilder rpBuilder) {
        for (String gem : TerraFabricCraftConstants.GEMS) {
            rpBuilder.addItemModel(TerraFabricCraft.getId(gem+"_chipped"), model -> model
                    .parent(new Identifier("item/generated"))
                    .texture("layer0", TerraFabricCraft.getTextureId("items/gems/chipped_"+gem))
            );
            rpBuilder.addItemModel(TerraFabricCraft.getId(gem+"_flawed"), model -> model
                    .parent(new Identifier("item/generated"))
                    .texture("layer0", TerraFabricCraft.getTextureId("items/gems/flawed_"+gem))
            );
            rpBuilder.addItemModel(TerraFabricCraft.getId(gem+"_normal"), model -> model
                    .parent(new Identifier("item/generated"))
                    .texture("layer0", TerraFabricCraft.getTextureId("items/gems/normal_"+gem))
            );
            rpBuilder.addItemModel(TerraFabricCraft.getId(gem+"_flawless"), model -> model
                    .parent(new Identifier("item/generated"))
                    .texture("layer0", TerraFabricCraft.getTextureId("items/gems/flawless_"+gem))
            );
            rpBuilder.addItemModel(TerraFabricCraft.getId(gem+"_exquisite"), model -> model
                    .parent(new Identifier("item/generated"))
                    .texture("layer0", TerraFabricCraft.getTextureId("items/gems/exquisite_"+gem))
            );
        }
    }
    public static void registerCube(ArtificeResourcePack.ClientResourcePackBuilder rpBuilder, String name, String texturePath) {
        rpBuilder.addBlockState(TerraFabricCraft.getId(name), state -> state
                .variant("", model -> model.model(TerraFabricCraft.getId("block/"+name)))
        );

        rpBuilder.addBlockModel(TerraFabricCraft.getId(name), model -> model
                .parent(new Identifier("block/cube_all"))
                .texture("all", TerraFabricCraft.getTextureId(texturePath))
        );

        rpBuilder.addItemModel(TerraFabricCraft.getId(name), model -> model
                .parent(TerraFabricCraft.getId("block/"+name))
        );
    }

    public static void registerMetalThings(ArtificeResourcePack.ClientResourcePackBuilder rpBuilder) {
        for (String ingot : TerraFabricCraftConstants.INGOTS) {
            registerItem(rpBuilder, ingot + "_ingot", "items/ingots/"+ingot+"_ingot");
            if (!ingot.equals("unknown")) {
                registerItem(rpBuilder, ingot + "_double_ingot", "items/ingots/" + ingot + "_double_ingot");
                registerItem(rpBuilder, ingot + "_sheet", "items/ingots/" + ingot + "_sheet");
                registerItem(rpBuilder, ingot + "_double_sheet", "items/ingots/" + ingot + "_double_sheet");
            }
        }

        for (StoneToolMaterials material : StoneToolMaterials.values()) {
            String name = material.name().toLowerCase(Locale.ROOT);
            for (Pair<String, Boolean> toolTypePair : TerraFabricCraftConstants.TOOL_TYPES) {
                String name2 = toolTypePair.getLeft();
                if (toolTypePair.getRight()) {
                    registerItem(rpBuilder, name + "_" + name2 + "_head", "items/toolheads/stone_"+name2+((name2.equals("sword") || name2.equals("scythe") || name2.equals("knife") || name2.equals("saw")) ? "_blade" : "_head"));
                }
            }
            registerItemHandheld(rpBuilder, name+"_axe", "items/tools/stone_axe");
            registerItemHandheld(rpBuilder, name+"_hammer", "items/tools/stone_hammer");
            registerItemHandheld(rpBuilder, name+"_hoe", "items/tools/stone_hoe");
            registerItemHandheld(rpBuilder, name+"_javelin", "items/tools/stone_javelin");
            registerItemHandheld(rpBuilder, name+"_knife", "items/tools/stone_knife");
            registerItemHandheld(rpBuilder, name+"_shovel", "items/tools/stone_shovel");
        }

        for (MetalToolMaterials material : MetalToolMaterials.values()) {
            String name = material.name().toLowerCase(Locale.ROOT);
            for (Pair<String, Boolean> toolTypePair : TerraFabricCraftConstants.TOOL_TYPES) {
                String name2 = toolTypePair.getLeft();
                registerItem(rpBuilder, name + "_" + name2 + "_head", "items/toolheads/"+name+"_"+name2+((name2.equals("sword") || name2.equals("scythe") || name2.equals("knife") || name2.equals("saw")) ? "_blade" : "_head"));
            }
            registerItemHandheld(rpBuilder, name+"_axe", "items/tools/"+name+"_axe");
            registerItemHandheld(rpBuilder, name+"_hammer", "items/tools/"+name+"_hammer");
            registerItemHandheld(rpBuilder, name+"_hoe", "items/tools/"+name+"_hoe");
            registerItemHandheld(rpBuilder, name+"_javelin", "items/tools/"+name+"_javelin");
            registerItemHandheld(rpBuilder, name+"_knife", "items/tools/"+name+"_knife");
            registerItemHandheld(rpBuilder, name+"_shovel", "items/tools/"+name+"_shovel");
            registerItemHandheld(rpBuilder, name+"_propick", "items/tools/"+name+"_propick");
            registerItemHandheld(rpBuilder, name+"_chisel", "items/tools/"+name+"_chisel");
            registerItemHandheld(rpBuilder, name+"_pick", "items/tools/"+name+"_pick");
            registerItemHandheld(rpBuilder, name+"_saw", "items/tools/"+name+"_saw");
            registerItemHandheld(rpBuilder, name+"_scythe", "items/tools/"+name+"_scythe");
            registerItemHandheld(rpBuilder, name+"_mace", "items/tools/"+name+"_mace");
            registerItemHandheld(rpBuilder, name+"_sword", "items/tools/"+name+"_sword");

            registerItem(rpBuilder, name+"_helmet", "items/armor/"+name+"_helmet");
            registerItem(rpBuilder, name+"_chestplate", "items/armor/"+name+"_chestplate");
            registerItem(rpBuilder, name+"_leggings", "items/armor/"+name+"_leggings");
            registerItem(rpBuilder, name+"_boots", "items/armor/"+name+"_boots");
        }

        for (Pair<String, Boolean> toolTypePair : TerraFabricCraftConstants.TOOL_TYPES) {
            String name = toolTypePair.getLeft();
            registerItem(rpBuilder, name + "_mold", "items/pottery/ceramic_mold_"+name);
        }

    }

    public static void registerItem(ArtificeResourcePack.ClientResourcePackBuilder rpBuilder, String name, String texturePath) {
        rpBuilder.addItemModel(TerraFabricCraft.getId(name), model -> model
                .parent(new Identifier("item/generated"))
                .texture("layer0", TerraFabricCraft.getTextureId(texturePath))
        );
    }

    public static void registerItemHandheld(ArtificeResourcePack.ClientResourcePackBuilder rpBuilder, String name, String texturePath) {
        rpBuilder.addItemModel(TerraFabricCraft.getId(name), model -> model
                .parent(new Identifier("item/handheld"))
                .texture("layer0", TerraFabricCraft.getTextureId(texturePath))
        );
    }

    public static void registerCubeOverlay(ArtificeResourcePack.ClientResourcePackBuilder rpBuilder, String name, String texturePathBottom, String texturePathOverlay) {
        rpBuilder.addBlockState(TerraFabricCraft.getId(name), state -> state
                .variant("", model -> model.model(TerraFabricCraft.getId("block/" + name)))
        );
        rpBuilder.addBlockModel(TerraFabricCraft.getId(name), model -> model
                .parent(TerraFabricCraft.getId("block/ore"))
                .texture("rock", TerraFabricCraft.getTextureId(texturePathBottom))
                .texture("ore", TerraFabricCraft.getTextureId(texturePathOverlay))
        );
        rpBuilder.addItemModel(TerraFabricCraft.getId(name), model -> model
                .parent(TerraFabricCraft.getId("block/" + name))
        );
    }

    public static void registerCubeGrass(ArtificeResourcePack.ClientResourcePackBuilder rpBuilder, String name, String texturePathBottom) {
        rpBuilder.addBlockState(TerraFabricCraft.getId(name), state -> state
                .variant("", model -> model.model(TerraFabricCraft.getId("block/" + name)))
        );
        rpBuilder.addBlockModel(TerraFabricCraft.getId(name), model -> model
                .parent(TerraFabricCraft.getId("block/with_grass_as_overlay"))
                .texture("block", TerraFabricCraft.getTextureId(texturePathBottom))
                .texture("grass_top", TerraFabricCraft.getTextureId("blocks/grass_top"))
                .texture("grass_side", TerraFabricCraft.getTextureId("blocks/grass_side"))
        );
        rpBuilder.addItemModel(TerraFabricCraft.getId(name), model -> model
                .parent(TerraFabricCraft.getId("block/" + name))
        );
    }
}

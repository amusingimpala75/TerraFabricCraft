package com.github.amusingimpala75.terrafabriccraft;

import com.github.amusingimpala75.terrafabriccraft.block.TerraFabricCraftBlocks;
import com.github.amusingimpala75.terrafabriccraft.config.TerraFabricCraftConfig;
import com.github.amusingimpala75.terrafabriccraft.item.TerraFabricCraftItems;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TerraFabricCraft implements ModInitializer {
    public static final String modid = "terrafabriccraft";
    public static final Logger LOGGER = LogManager.getLogger("TerraFabricCraft - CaveIns");

    @Override
    public void onInitialize(){
        LOGGER.info("Mining just got a bit more dangerous");
        AutoConfig.register(TerraFabricCraftConfig.class, GsonConfigSerializer::new);
        TerraFabricCraftConfig config = getConfig();
        TerraFabricCraftBlocks.registerBlocks(config);
        TerraFabricCraftItems.registerItems(config);
    }

    public static Identifier getId(String name) {
        return new Identifier(modid, name);
    }

    public static Identifier getTextureId(String name) {
        return new Identifier("terrafabriccraft-core", name);
    }

    public static TerraFabricCraftConfig getConfig() {
        return AutoConfig.getConfigHolder(TerraFabricCraftConfig.class).getConfig();
    }
}

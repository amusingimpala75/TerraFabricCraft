package com.github.amusingimpala75.terrafabriccraft.config;

import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraft;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;

/* Basic Modmenu integration for config */
@SuppressWarnings("deprecation")
public class ModMenuIntegration implements ModMenuApi {

    @Override
    public String getModId() {
        return TerraFabricCraft.modid;
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(TerraFabricCraftConfig.class, parent).get();
    }
}
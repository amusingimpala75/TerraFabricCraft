package com.github.amusingimpala75.terrafabriccraft.screenhandler;

import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraft;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;

public class TerraFabricCraftScreenHandlers {

    public static final ScreenHandlerType<LogPileScreenHandler> LOG_PILE_HANDLER = ScreenHandlerRegistry.registerSimple(TerraFabricCraft.getId("log_pile_handler"), LogPileScreenHandler::new);
    public static final ScreenHandlerType<KnappingScreenHandler> KNAPPING_HANDLER = ScreenHandlerRegistry.registerSimple(TerraFabricCraft.getId("knapping_hanler"), KnappingScreenHandler::new);

    public static void registerScreenHandlers() {

    }
}

package com.github.amusingimpala75.terrafabriccraft.client.screen;

import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraft;
import com.github.amusingimpala75.terrafabriccraft.screenhandler.LogPileScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class LogPileScreen extends HandledScreen<LogPileScreenHandler> {

    public static final Identifier TEXTURE = TerraFabricCraft.getTextureId("textures/gui/gui_logpile.png");
    public static final Identifier PLAYER_INV = TerraFabricCraft.getTextureId("textures/gui/gui_inventory_lower.png");

    public LogPileScreen(LogPileScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        client.getTextureManager().bindTexture(TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y-9, 0, 0, backgroundWidth, backgroundHeight);
        client.getTextureManager().bindTexture(PLAYER_INV);
        x = (width - backgroundWidth) / 2;
        y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y+79, 0, 0, backgroundWidth, backgroundHeight-80);
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        //titleY -= 20;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        this.textRenderer.draw(matrices, this.title, (float)this.titleX, (float)this.titleY, 4210752);
    }
}

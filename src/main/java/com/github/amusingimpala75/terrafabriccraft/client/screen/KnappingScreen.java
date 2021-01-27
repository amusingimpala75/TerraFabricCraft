package com.github.amusingimpala75.terrafabriccraft.client.screen;

import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraft;
import com.github.amusingimpala75.terrafabriccraft.screenhandler.KnappingScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

//TODO: Fix c2s and s2c
public class KnappingScreen extends HandledScreen<KnappingScreenHandler> {

    private static final Identifier TEXTURE = TerraFabricCraft.getTextureId("textures/gui/gui_knapping.png");
    private static final Identifier PLAYER_INV = TerraFabricCraft.getTextureId("textures/gui/gui_inventory_lower.png");

    public KnappingScreen(KnappingScreenHandler handler, PlayerInventory inventory, Text title) {
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
    protected void init() {
        super.init();
        int startX = this.width/2 - 83;
        int startY = this.height/2 - 87;
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                int finalX = x;
                int finalY = y;
                KnappingButton button = new KnappingButton((x*16)+startX, (y*16)+startY, handler.getKnappingType(), buttonAct -> {
                    if (!((KnappingButton)buttonAct).clicked) {
                        System.out.println("KnappingButton clicked!");
                        this.handler.clickSquare(finalX, finalY);
                        ((KnappingButton)buttonAct).clicked = true;
                        if (!this.handler.isRockDestroyed()) {
                            this.handler.destroyRock();
                        }
                        this.handler.reCheckRecipes();
                    }
                }, this.handler.item);
                if (handler.getClickedSquareAt(x, y)) {
                    button.clicked = true;
                }
                this.addButton(button);
            }
        }
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        this.textRenderer.draw(matrices, this.title, (float)this.titleX+76, (float)this.titleY-8, 4210752);
    }
}

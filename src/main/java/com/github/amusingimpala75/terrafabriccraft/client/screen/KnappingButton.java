package com.github.amusingimpala75.terrafabriccraft.client.screen;

import com.github.amusingimpala75.terrafabriccraft.TerraFabricCraft;
import com.github.amusingimpala75.terrafabriccraft.item.KnappingItem;
import com.github.amusingimpala75.terrafabriccraft.recipe.KnappingType;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class KnappingButton extends TexturedButtonWidget {

    public boolean clicked;
    private final KnappingType type;
    private final KnappingItem item;

    public KnappingButton(int x, int y, KnappingType type, PressAction onPress, KnappingItem item) {
        //super(x, y, 16, 16, new LiteralText(""), onPress);
        super(x, y, 16, 16, 0, 0, 0, new Identifier(""), 16, 16, onPress);
        this.type = type;
        this.item = item;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        String textureLocation;
        if (clicked) {
            textureLocation = this.type.getClickedTextureLocation();
        } else if (this.type.getUnclickedTextureLocation() == null) {
            textureLocation = item.getTextureLocation();
        } else {
            textureLocation = this.type.getUnclickedTextureLocation();
        }
        if (textureLocation != null) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableDepthTest();
            MinecraftClient.getInstance().getTextureManager().bindTexture(TerraFabricCraft.getTextureId(textureLocation));
            drawTexture(matrices, this.x, this.y, 0, 0, 16, 16, 16, 16);
        }
            /*Identifier texture = TerraFabricCraft.getTextureId(textureLocation);
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            minecraftClient.getTextureManager().bindTexture(texture);
            int i = 0;
            if (this.isHovered()) {
                i += 0;
            }

            RenderSystem.enableDepthTest();
            drawTexture(matrices, this.x, this.y, (float) 0, (float) i, this.width, this.height, 16, 16);
            if (this.isHovered()) {
                this.renderToolTip(matrices, mouseX, mouseY);
            }*/
        //}
    }
}

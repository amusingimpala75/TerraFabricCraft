package com.github.amusingimpala75.terrafabriccraft.client.render;

import net.minecraft.client.render.entity.model.BipedEntityModel;

public class OverlayArmourModel extends BipedEntityModel {
    public OverlayArmourModel(float scale) {
        super(scale);
        this.leftArm.addCuboid();
    }

}

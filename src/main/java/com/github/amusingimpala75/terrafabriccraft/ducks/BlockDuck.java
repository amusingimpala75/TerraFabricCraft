package com.github.amusingimpala75.terrafabriccraft.ducks;

import net.minecraft.block.Block;

public interface BlockDuck extends com.github.amusingimpala75.terrafabriccraftcore.duck.BlockDuck {

    Block canCollapse();

    Block doesInstantlyCollapse();

    Block collapsesToOtherBlock(Block block);

    boolean collapses();

    boolean instaCollapses();

    Block getCollapseBlock();
}

package net.tarantel.chickenroost.item.base;

import net.minecraft.block.Block;

public class ChickenBlockItem extends RoostUltimateItem {
    /**
     * @deprecated
     */
    @Deprecated
    private final Block block;

    public ChickenBlockItem(Block block, Properties properties, int currentmaxxp) {
        super(properties);
        this.block = block;
    }
}
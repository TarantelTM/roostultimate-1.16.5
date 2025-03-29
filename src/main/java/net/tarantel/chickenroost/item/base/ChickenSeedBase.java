package net.tarantel.chickenroost.item.base;


import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class ChickenSeedBase extends ChickenBlockItem {


    public static int currentmaxxpp;
    private Block block;

    public ChickenSeedBase(Block p_41579_, Item.Properties p_41580_, int currentmaxxp) {
        super(p_41579_, p_41580_, currentmaxxp);
        currentmaxxpp = currentmaxxp;
        this.block = p_41579_;
    }

    /**
     * Returns the unlocalized name of this item.
     */
        public String getDescriptionId() {
            return this.getOrCreateDescriptionId();
        }
}

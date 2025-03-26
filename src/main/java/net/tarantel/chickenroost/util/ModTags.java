package net.tarantel.chickenroost.util;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.tarantel.chickenroost.ChickenRoostMod;

public class ModTags {
    //public static final Tag<EntityType<?>> ROOSTCHICKENS = of("roostultimate");
    //public static final Tag<EntityType<?>> VANILLA = of("vanilla");
    private ModTags() {

    }
    public static class Items {
        private static Tags.IOptionalNamedTag<Item> tag(String name) {
            return ItemTags.createOptional(new ResourceLocation("forge", name));
        }
    }
   // private static Tag<EntityType<?>> of(String id) {
       // return Tag.create(new ResourceLocation("forge",id));
    //}


    public static class Blocks {

        public static final Tags.IOptionalNamedTag<Block> FIRESTONE_CLICKABLE_BLOCKS =
                createTag("firestone_clickable_blocks");

        private static Tags.IOptionalNamedTag<Block> createTag(String name) {
            return BlockTags.createOptional(new ResourceLocation(ChickenRoostMod.MODID, name));
        }

        private static Tags.IOptionalNamedTag<Block> createForgeTag(String name) {
            return BlockTags.createOptional(new ResourceLocation("forge", name));
        }
    }
}

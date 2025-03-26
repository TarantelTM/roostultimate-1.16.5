package net.tarantel.chickenroost.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.tarantel.chickenroost.ChickenRoostMod;

public class WrenchTool {
    public static void execute(World world, int x, int y, int z) {
        BlockState oldblock = Blocks.AIR.defaultBlockState();
        if ((world.getBlockState(new BlockPos(x, y, z))).is(BlockTags.createOptional(new ResourceLocation(ChickenRoostMod.MODID,"blocks/all")))) {
            oldblock = (world.getBlockState(new BlockPos(x, y, z)));
            world.setBlock(new BlockPos(x, y, z), Blocks.AIR.defaultBlockState(), 3);
        }
    }
}
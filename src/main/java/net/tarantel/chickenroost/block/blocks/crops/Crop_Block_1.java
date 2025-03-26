//package net.tarantel.chickenroost.block.blocks.crops;
//
//import net.minecraft.block.AbstractPlantBlock;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.Blocks;
//import net.minecraft.state.IntegerProperty;
//import net.minecraft.state.properties.BlockStateProperties;
//import net.tarantel.chickenroost.item.ModItems;
//
//public class Crop_Block_1 extends AbstractPlantBlock {
//    public static final int MAX_AGE = 7;
//    Blocks.WHEAT
//    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_7;
//
//    public Crop_Block_1(Properties pProperties) {
//        super(pProperties);
//    }
//
//    @Override
//    protected ItemLike getBaseSeedId() {
//        return ModItems.CHICKEN_FOOD_TIER_1.get();
//    }
//
//    @Override
//    public IntegerProperty getAgeProperty() {
//        return AGE;
//    }
//
//    @Override
//    public int getMaxAge() {
//        return MAX_AGE;
//    }
//
//    @Override
//    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
//        pBuilder.add(AGE);
//    }
//}
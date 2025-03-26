//package net.tarantel.chickenroost.block.blocks;
//
//
//import net.minecraft.block.HorizontalBlock;
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.item.BlockItemUseContext;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.nbt.ListNBT;
//import net.minecraft.state.DirectionProperty;
//import net.minecraft.state.StateContainer;
//import net.minecraft.state.properties.BlockStateProperties;
//import net.minecraft.tileentity.TileEntity;
//import net.tarantel.chickenroost.block.tile.Breeder_Tile;
//import net.tarantel.chickenroost.block.tile.ChickenStorage_Tile;
//import net.tarantel.chickenroost.block.tile.ModBlockEntities;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.particles.BlockParticleData;
//import net.minecraft.particles.ParticleTypes;
//import net.minecraft.util.ActionResultType;
//import net.minecraft.util.Hand;
//import net.minecraft.util.SoundCategory;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.BlockRayTraceResult;
//import net.minecraft.world.World;
//
//import javax.annotation.Nullable;
//
//public class ChickenStorage_Block extends HorizontalBlock {
//    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
//
//
//    public ChickenStorage_Block(Properties properties) {
//        super(properties);
//    }
//
//
//
//    @Override
//    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
//        builder.add(FACING);
//    }
//
//    @Nullable
//    @Override
//    public BlockState getStateForPlacement(BlockItemUseContext context) {
//        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
//    }
//    public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
//        super.setPlacedBy(world, pos, state, placer, stack);
//
//        if (!stack.hasTag()) {
//            return;
//        }
//
//        CompoundNBT nbt = stack.getTag();
//        if (nbt == null || !nbt.contains("Items")) {
//            return;
//        }
//
//        TileEntity blockEntity = world.getBlockEntity(pos);
//        if (!(blockEntity instanceof Breeder_Tile)) {
//            return;
//        }
//
//        Breeder_Tile tile = (Breeder_Tile) blockEntity;
//
//        ListNBT items = nbt.getList("Items", 10); // 10 is the type for CompoundNBT
//        for (int i = 0; i < items.size(); i++) {
//            CompoundNBT itemTag = items.getCompound(i);
//            int slot = itemTag.getInt("Slot");
//            ItemStack itemStack = ItemStack.areItemStackTagsEqual(itemTag);
//            if (!itemStack.isEmpty()) {
//                tile.itemHandler.setStackInSlot(slot, itemStack);
//            }
//        }
//    }
//
//    @Nullable
//    @Override
//    public TileEntity newBlockEntity(BlockPos pos, BlockState state) {
//        return new Breeder_Tile(pos, state);
//    }
//
//    @Nullable
//    @Override
//    public <T extends TileEntity> net.minecraftforge.common.util.LazyOptional<T> getTicker(World level, BlockState state, TileEntityType<T> type) {
//        return createTickerHelper(type, ModBlockEntities.BREEDER.get(), Breeder_Tile::tick);
//    }
//
//}
//
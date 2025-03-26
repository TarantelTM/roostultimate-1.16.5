package net.tarantel.chickenroost.block.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;
import net.tarantel.chickenroost.block.tile.Breeder_Tile;
import net.tarantel.chickenroost.block.tile.Extractor_Tile;
import net.tarantel.chickenroost.block.tile.ModTileEntities;
import net.tarantel.chickenroost.block.tile.Trainer_Tile;
import net.tarantel.chickenroost.container.Breeder_Container;
import net.tarantel.chickenroost.container.Extractor_Container;
import net.tarantel.chickenroost.container.Trainer_Container;

import javax.annotation.Nullable;

public class Trainer_Block extends Block {
    public Trainer_Block(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }
    public static final DirectionProperty FACING = HorizontalBlock.FACING;



    @Override
    public BlockState getStateForPlacement(BlockItemUseContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }
    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos,
                                PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isClientSide) {
            TileEntity tileEntity = worldIn.getBlockEntity(pos);

            if(tileEntity instanceof Trainer_Tile) {
                INamedContainerProvider containerProvider = createContainerProvider(worldIn, pos);

                NetworkHooks.openGui(((ServerPlayerEntity)player), containerProvider, tileEntity.getBlockPos());
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return ActionResultType.SUCCESS;
    }



    private INamedContainerProvider createContainerProvider(World worldIn, BlockPos pos) {
        return new INamedContainerProvider() {
            @Override
            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("screen.tutorialmod.lightning_channeler");
            }

            @Nullable
            @Override
            public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                return new Trainer_Container(i, worldIn, pos, playerInventory, playerEntity);
            }
        };
    }
    @Override
    public void setPlacedBy(World pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);

        // Early exit if the stack doesn't have NBT data
        if (!pStack.hasTag()) {
            return;
        }

        // Get the NBT data from the stack
        CompoundNBT nbt = pStack.getTag();
        if (nbt == null || !nbt.contains("Items")) {
            return; // Exit early if the NBT data doesn't contain the expected key
        }

        // Get the block entity at the position
        TileEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (!(blockEntity instanceof Trainer_Tile)) {
            return; // Exit early if the block entity is not of the expected type
        }

        Trainer_Tile tile = (Trainer_Tile) blockEntity;

        // Get the list of items from the NBT data
        ListNBT items = nbt.getList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < items.size(); i++) {
            CompoundNBT itemTag = items.getCompound(i);
            int slot = itemTag.getInt("Slot");
            ItemStack itemStack = ItemStack.of(itemTag);
            if (!itemStack.isEmpty()) {
                tile.itemHandler.setStackInSlot(slot, itemStack);
            }
        }
    }


    /*@Override
    public void playerDestroy(World pLevel, PlayerEntity pPlayer, BlockPos pPos, BlockState pState, @Nullable TileEntity pTe, ItemStack pStack) {
        pPlayer.awardStat(Stats.BLOCK_MINED.get(this));
        pPlayer.causeFoodExhaustion(0.005F);
        //dropResources(pState, pLevel, pPos, pTe, pPlayer, pStack);
        TileEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof Breeder_Tile) {
            ((Breeder_Tile) blockEntity).drops();
        }
    }*/
    @Override
    public void onRemove(BlockState pState, World pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            TileEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof Trainer_Tile) {
                ((Trainer_Tile) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
    /*@Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.BREEDER_TILE.get().create();
    }*/

    /*@Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }*/

    /*@Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader p_196283_1_) {
        return ModTileEntities.BREEDER_TILE.get().create();
    }*/

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new Trainer_Tile();
    }
}
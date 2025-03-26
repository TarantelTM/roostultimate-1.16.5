package net.tarantel.chickenroost.block.tile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.base.ChickenItemBase;
import net.tarantel.chickenroost.recipes.Breeder_Recipe;
import net.tarantel.chickenroost.recipes.ModRecipeTypes;
import net.tarantel.chickenroost.recipes.Roost_Recipe;
import net.tarantel.chickenroost.util.Config;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import java.util.*;
public class Roost_Tile extends TileEntity implements ITickableTileEntity {

    public final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public int progress = 0;
    public int maxProgress = ( Config.breed_speed_tick.get() * 20);

    public Roost_Tile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public Roost_Tile() {
        this(ModTileEntities.ROOST_TILE.get());
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        progress = nbt.getInt("roost.progress");
        sendUpdates();
        super.load(state, nbt);
    }
    /*@Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        super.handleUpdateTag(state, tag);
        itemHandler.deserializeNBT(tag.getCompound("inv"));
    }*/

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(getBlockPos(), 1, itemHandler.serializeNBT());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt=super.getUpdateTag();
        nbt.put("inv", itemHandler.serializeNBT());
        return nbt;
    }
    /*@Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT tag = pkt.getTag();
        handleUpdateTag(getBlockState(), tag);
    }*/
    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        compound.putInt("roost.progress", this.progress);
        return super.save(compound);
    }
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        setChanged();
        if(!level.isClientSide()) {
            setChanged();
        }

    }
    public void sendUpdates() {
        if (this.level == null) return;
        this.level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        this.level.updateNeighborsAt(getBlockPos(), this.getBlockState().getBlock());
        setChanged();
    }
    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }
    private ItemStackHandler createHandler() {
        return new ItemStackHandler(3) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                if(slot == 0){
                    return stack.getItem() instanceof ChickenItemBase;
                } else if (slot == 2) {
                    return stack.getItem() instanceof ChickenItemBase;
                } else if (slot == 1) {
                    return  ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation("c:seeds/tiered")).contains(stack.getItem());
                }
                else {
                    return false;
                }

            }

            @Override
            public int getSlotLimit(int slot)
            {
                if(slot == 1){
                    return 64;
                }
                return 1;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(!isItemValid(slot, stack)) {
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }

        return super.getCapability(cap, side);
    }

    public void drops() {
        // Create a SimpleContainer to hold the items from the item handler
        Inventory inventory = new Inventory(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        // Create an ItemStack for the block
        ItemStack itemStack = new ItemStack(ModBlocks.ROOST.get());

        // Save the inventory contents to the ItemStack's NBT
        CompoundNBT nbt = new CompoundNBT();
        ListNBT itemsTag = new ListNBT();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                CompoundNBT itemTag = new CompoundNBT();
                itemTag.putInt("Slot", i);
                stack.save(itemTag);
                itemsTag.add(itemTag);
            }
        }
        nbt.put("Items", itemsTag);
        itemStack.setTag(nbt);

        // Create a SimpleContainer to hold the block's ItemStack
        Inventory block = new Inventory(1);
        block.setItem(0, itemStack.copy());

        // Drop the contents in the world
        InventoryHelper.dropContents(Objects.requireNonNull(this.level), this.worldPosition, block);
    }
    public ItemStack getRenderStack1() {
        ItemStack stack;

        if(!itemHandler.getStackInSlot(0).isEmpty()) {
            stack = itemHandler.getStackInSlot(0);
        } else {
            stack = ItemStack.EMPTY;
        }

        return stack;
    }

    public ItemStack getRenderStack2() {
        ItemStack stack;

        if(!itemHandler.getStackInSlot(2).isEmpty()) {
            stack = itemHandler.getStackInSlot(2);
        } else {
            stack = ItemStack.EMPTY;
        }

        return stack;
    }

    public ItemStack getRenderStack3() {
        ItemStack stack;

        if(!itemHandler.getStackInSlot(1).isEmpty()) {
            stack = itemHandler.getStackInSlot(1);
        } else {
            stack = ItemStack.EMPTY;
        }

        return stack;
    }

    protected final IIntArray dataAccess = new IIntArray() {
        public int get(int pIndex) {
            switch(pIndex) {
                case 0:
                    return Roost_Tile.this.progress;
                case 1:
                    return Roost_Tile.this.maxProgress;
                default:
                    return 0;
            }
        }

        public void set(int pIndex, int pValue) {
            switch(pIndex) {
                case 0:
                    Roost_Tile.this.progress = pValue;
                    break;
                case 1:
                    Roost_Tile.this.maxProgress = pValue;
                    break;
            }

        }

        public int getCount() {
            return 2;
        }
    };

    public IIntArray getDataAccess() {
        return this.dataAccess;
    }
    public void craft() {
        Inventory inv = new Inventory(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }

        Optional<Roost_Recipe> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.ROOST_RECIPE, inv, level);

        recipe.ifPresent(iRecipe -> {
            ItemStack output = iRecipe.getResultItem();
            craftTheItem(output);


            setChanged();
        });
    }
    private void resetProgress() {

        this.progress = 0;
    }
    private void craftTheItem(ItemStack output) {
        itemHandler.extractItem(1, 1, false);
        itemHandler.insertItem(3, output, false);
    }
    static ItemStack ChickenOutput = ItemStack.EMPTY;
    static String outpit;

    private static void craftItem(Roost_Tile pEntity) {
        World level = pEntity.level;
        Inventory inventory = new Inventory(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        List<Roost_Recipe> recipe = pEntity.getLevel().getRecipeManager()
                .getRecipesFor(ModRecipeTypes.ROOST_RECIPE, inventory, pEntity.getLevel());
        if (level != null) {
            if (hasRecipe(pEntity)) {
                Random ran = new Random();
                int RandomOutputs = ran.nextInt(recipe.size());
                ChickenOutput = new ItemStack(recipe.get(RandomOutputs).output.getItem());
                //ChickenOutput = new ItemStack(recipe.get(RandomOutputs).value().output.getItem());
                if (pEntity.itemHandler.getStackInSlot(3) == ItemStack.EMPTY) {
                    pEntity.itemHandler.extractItem(0, 0, true);
                    pEntity.itemHandler.extractItem(2, 0, true);
                    pEntity.itemHandler.extractItem(1, 1, false);
                    pEntity.itemHandler.setStackInSlot(3, ChickenOutput);
                    pEntity.resetProgress();
                } else if (pEntity.itemHandler.getStackInSlot(4) == ItemStack.EMPTY) {
                    pEntity.itemHandler.extractItem(2, 0, true);
                    pEntity.itemHandler.extractItem(0, 0, true);
                    pEntity.itemHandler.extractItem(1, 1, false);
                    pEntity.itemHandler.setStackInSlot(4, ChickenOutput);
                    pEntity.resetProgress();
                } else if (pEntity.itemHandler.getStackInSlot(5) == ItemStack.EMPTY) {
                    pEntity.itemHandler.extractItem(0, 0, true);
                    pEntity.itemHandler.extractItem(2, 0, true);
                    pEntity.itemHandler.extractItem(1, 1, false);
                    pEntity.itemHandler.setStackInSlot(5, ChickenOutput);
                    pEntity.resetProgress();
                } else if (pEntity.itemHandler.getStackInSlot(6) == ItemStack.EMPTY) {
                    pEntity.itemHandler.extractItem(0, 0, true);
                    pEntity.itemHandler.extractItem(2, 0, true);
                    pEntity.itemHandler.extractItem(1, 1, false);
                    pEntity.itemHandler.setStackInSlot(6, ChickenOutput);
                    pEntity.resetProgress();
                } else if (pEntity.itemHandler.getStackInSlot(7) == ItemStack.EMPTY) {
                    pEntity.itemHandler.extractItem(0, 0, true);
                    pEntity.itemHandler.extractItem(2, 0, true);
                    pEntity.itemHandler.extractItem(1, 1, false);
                    pEntity.itemHandler.setStackInSlot(7, ChickenOutput);
                    pEntity.resetProgress();
                } else if (pEntity.itemHandler.getStackInSlot(8) == ItemStack.EMPTY) {
                    pEntity.itemHandler.extractItem(0, 0, true);
                    pEntity.itemHandler.extractItem(2, 0, true);
                    pEntity.itemHandler.extractItem(1, 1, false);
                    pEntity.itemHandler.setStackInSlot(8, ChickenOutput);
                    pEntity.resetProgress();
                } else if (pEntity.itemHandler.getStackInSlot(9) == ItemStack.EMPTY) {
                    pEntity.itemHandler.extractItem(0, 0, true);
                    pEntity.itemHandler.extractItem(2, 0, true);
                    pEntity.itemHandler.extractItem(1, 1, false);
                    pEntity.itemHandler.setStackInSlot(9, ChickenOutput);
                    pEntity.resetProgress();
                } else if (pEntity.itemHandler.getStackInSlot(10) == ItemStack.EMPTY) {
                    pEntity.itemHandler.extractItem(0, 0, true);
                    pEntity.itemHandler.extractItem(2, 0, true);
                    pEntity.itemHandler.extractItem(1, 1, false);
                    pEntity.itemHandler.setStackInSlot(10, ChickenOutput);
                    pEntity.resetProgress();
                } else {
                    pEntity.resetProgress();
                }
            } else {
                pEntity.resetProgress();
            }
        }
    }
    @Override
    public void tick() {
        if(level.isClientSide)
            return;
        getDataAccess();
        BlockPos pos = this.getBlockPos();
        BlockState state = this.getBlockState();

        Roost_Tile pEntity = this;


        setChanged();
        if(hasRecipe(pEntity) && (pEntity.itemHandler.getStackInSlot(3) == ItemStack.EMPTY
                || pEntity.itemHandler.getStackInSlot(4) == ItemStack.EMPTY ||
                pEntity.itemHandler.getStackInSlot(5) == ItemStack.EMPTY ||
                pEntity.itemHandler.getStackInSlot(6) == ItemStack.EMPTY ||
                pEntity.itemHandler.getStackInSlot(7) == ItemStack.EMPTY ||
                pEntity.itemHandler.getStackInSlot(8) == ItemStack.EMPTY ||
                pEntity.itemHandler.getStackInSlot(9) == ItemStack.EMPTY ||
                pEntity.itemHandler.getStackInSlot(10) == ItemStack.EMPTY)) {

            pEntity.progress++;
            setChanged();
            if(pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        } else {
            pEntity.resetProgress();

            setChanged();
        }


    }

    private static boolean hasRecipe(Roost_Tile entity) {
        World level = entity.level;
        Inventory inventory = new Inventory(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<Roost_Recipe> recipe = Optional.empty();
        if (level != null) {
            // Get the recipe manager and find a matching recipe
            RecipeManager recipeManager = level.getRecipeManager();
            recipe = recipeManager.getRecipeFor(ModRecipeTypes.ROOST_RECIPE, inventory, level);

            // If a recipe is found, set the max progress based on the recipe's time
            if (recipe.isPresent()) {
                entity.maxProgress = Config.roost_speed_tick.get() * recipe.get().getTime();
            }
        }

        return recipe.isPresent();
    }
}
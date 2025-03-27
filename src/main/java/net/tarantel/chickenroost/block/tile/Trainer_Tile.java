package net.tarantel.chickenroost.block.tile;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.tarantel.chickenroost.block.blocks.ModBlocks;
import net.tarantel.chickenroost.item.base.ChickenItemBase;
import net.tarantel.chickenroost.item.base.ChickenSeedBase;
import net.tarantel.chickenroost.network.AnimationUpdatePacket;
import net.tarantel.chickenroost.network.NetworkHandler;
import net.tarantel.chickenroost.recipes.ModRecipeTypes;
import net.tarantel.chickenroost.recipes.Trainer_Recipe;
import net.tarantel.chickenroost.util.Config;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

public class Trainer_Tile extends TileEntity implements ITickableTileEntity, IAnimatable {

    public final ItemStackHandler itemHandler = createHandler();
    boolean triggerAnim = false;
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public int progress = 0;
    public int maxProgress = ( Config.training_speed_tick.get() * 20);

    public Trainer_Tile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public Trainer_Tile() {
        this(ModTileEntities.TRAINER_TILE.get());
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        progress = nbt.getInt("trainer.progress");
        triggerAnim = nbt.getBoolean("triggerAnim"); // Add this
        sendUpdates();
        super.load(state, nbt);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("inv", itemHandler.serializeNBT());
        nbt.putInt("trainer.progress", this.progress);
        nbt.putBoolean("triggerAnim", this.triggerAnim);
        return new SUpdateTileEntityPacket(getBlockPos(), 1, nbt);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        nbt.put("inv", itemHandler.serializeNBT());
        nbt.putInt("trainer.progress", this.progress);
        nbt.putBoolean("triggerAnim", this.triggerAnim);
        return nbt;
    }
    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        compound.putInt("trainer.progress", this.progress);
        compound.putBoolean("triggerAnim", this.triggerAnim); // Add this
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
        return new ItemStackHandler(2) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                if(slot == 0){
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
        ItemStack itemStack = new ItemStack(ModBlocks.TRAINER.get());

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
                    return Trainer_Tile.this.progress;
                case 1:
                    return Trainer_Tile.this.maxProgress;
                default:
                    return 0;
            }
        }

        public void set(int pIndex, int pValue) {
            switch(pIndex) {
                case 0:
                    Trainer_Tile.this.progress = pValue;
                    break;
                case 1:
                    Trainer_Tile.this.maxProgress = pValue;
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

        Optional<Trainer_Recipe> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.TRAINER_RECIPE, inv, level);

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

    private static void craftItem(Trainer_Tile pEntity) {
        ChickenItem = (ChickenItemBase) pEntity.itemHandler.getStackInSlot(0).getItem();
        MyChicken = pEntity.itemHandler.getStackInSlot(0);
        FoodItem = (ChickenSeedBase) pEntity.itemHandler.getStackInSlot(1).getItem().getDefaultInstance().getItem();
        World level = pEntity.level;
        Inventory inventory = new Inventory(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }
        int ChickenXP = MyChicken.getOrCreateTag().getInt("roost_xp");

        if (pEntity.itemHandler.getStackInSlot(0).getItem() instanceof ChickenItemBase) {

            if (MyChicken.getOrCreateTag().getInt("roost_lvl") < LevelList[ChickenItem.currentchickena]) {
                if (pEntity.itemHandler.getStackInSlot(1).getItem() instanceof ChickenSeedBase) {
                    if (ChickenXP + (XPAmountList[FoodItem.currentmaxxpp] / 10) >= XPList[ChickenItem.currentchickena]) {
                        MyChicken.getOrCreateTag().putInt("roost_lvl", (MyChicken.getOrCreateTag().getInt("roost_lvl") + 1));
                        MyChicken.getOrCreateTag().putInt("roost_xp", 0);
                    } else {
                        MyChicken.getOrCreateTag().putInt("roost_xp", (int) (ChickenXP + ((int) XPAmountList[FoodItem.currentmaxxpp])));
                    }
                }

                pEntity.itemHandler.extractItem(1, 1, false);
                pEntity.itemHandler.extractItem(0, 0, false);
                pEntity.itemHandler.setStackInSlot(0, MyChicken);

                pEntity.resetProgress();
            }
            else
            {
                pEntity.resetProgress();
            }
        }
    }
    public static ItemStack MyChicken;
    public static ChickenSeedBase FoodItem;
    public static ChickenItemBase ChickenItem;
    public static int[] LevelList = {Config.maxlevel_tier_1.get().intValue(), Config.maxlevel_tier_2.get().intValue(), Config.maxlevel_tier_3.get().intValue(),Config.maxlevel_tier_4.get().intValue(),Config.maxlevel_tier_5.get().intValue(),Config.maxlevel_tier_6.get().intValue(),Config.maxlevel_tier_7.get().intValue(),Config.maxlevel_tier_8.get().intValue(),Config.maxlevel_tier_9.get().intValue()};
    public static int[] XPList = {Config.xp_tier_1.get().intValue(), Config.xp_tier_2.get().intValue(), Config.xp_tier_3.get().intValue(),Config.xp_tier_4.get().intValue(),Config.xp_tier_5.get().intValue(),Config.xp_tier_6.get().intValue(),Config.xp_tier_7.get().intValue(),Config.xp_tier_8.get().intValue(),Config.xp_tier_9.get().intValue()};
    public static int[] XPAmountList = {Config.food_xp_tier_1.get().intValue(), Config.food_xp_tier_2.get().intValue(), Config.food_xp_tier_3.get().intValue(), Config.food_xp_tier_4.get().intValue(), Config.food_xp_tier_5.get().intValue(), Config.food_xp_tier_6.get().intValue(), Config.food_xp_tier_7.get().intValue(), Config.food_xp_tier_8.get().intValue(), Config.food_xp_tier_9.get().intValue()};


    @Override
    public void tick() {
        if (level.isClientSide()) {
            return;
        }

        boolean wasTraining = triggerAnim;
        boolean shouldTrain = false;

        if (this.itemHandler.getStackInSlot(0).getItem().asItem() instanceof ChickenItemBase &&
                this.itemHandler.getStackInSlot(1).getItem().asItem() instanceof ChickenSeedBase) {

            ChickenItem = (ChickenItemBase) this.itemHandler.getStackInSlot(0).getItem();
            MyChicken = this.itemHandler.getStackInSlot(0);
            FoodItem = (ChickenSeedBase) this.itemHandler.getStackInSlot(1).getItem().getDefaultInstance().getItem();
            int ChickenLevel = MyChicken.getOrCreateTag().getInt("roost_lvl");

            if(ChickenLevel < LevelList[ChickenItem.currentchickena]){
                this.progress++;
                shouldTrain = true;
                this.sendAnimationState(ANIM_START_OPEN);
                if (this.progress >= this.maxProgress) {
                    craftItem(this);
                }
            }
        } else {
            this.sendAnimationState(ANIM_START_CLOSE);
            this.resetProgress();
        }

        // Only update and sync if state changed
        if (shouldTrain != wasTraining) {
            this.triggerAnim = shouldTrain ? true : false;
            setChanged();
            sendUpdates();
        }


        //System.out.println(this.triggerAnim);
    }

    private void sendAnimationState(int state) {
        NetworkHandler.INSTANCE.send(
                PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(getBlockPos())),
                new AnimationUpdatePacket(getBlockPos(), state)
        );
    }
    public static final int ANIM_START_OPEN = 1;
    public static final int ANIM_START_CLOSE = 2;
    public void setClientAnimationState(int state){
        switch (state){
            case ANIM_START_OPEN: {
                this.triggerAnim = true;
                break;
            }
            case ANIM_START_CLOSE: {
                this.triggerAnim = false;
                break;
            }
        }
    }

    private static boolean hasRecipe(Trainer_Tile entity) {
        World level = entity.level;
        Inventory inventory = new Inventory(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<Trainer_Recipe> recipe = Optional.empty();
        if (level != null) {
            // Get the recipe manager and find a matching recipe
            RecipeManager recipeManager = level.getRecipeManager();
            recipe = recipeManager.getRecipeFor(ModRecipeTypes.TRAINER_RECIPE, inventory, level);

            // If a recipe is found, set the max progress based on the recipe's time
            if (recipe.isPresent()) {
                entity.maxProgress = Config.training_speed_tick.get() * recipe.get().getTime();
            }
        }

        return recipe.isPresent();
    }
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private static final AnimationBuilder IDLE = new AnimationBuilder().loop("idle");
    private static final AnimationBuilder TRAINING = new AnimationBuilder().loop("training");

    private <E extends Trainer_Tile & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        boolean triggerAnim1 = event.getAnimatable().triggerAnim;
        if(triggerAnim1) {
            //event.getController().markNeedsReload();
            event.getController().setAnimation(TRAINING);
            //System.out.println("Training");
            //return PlayState.CONTINUE;
            return PlayState.CONTINUE;
        }else {
            //event.getController().markNeedsReload();
            event.getController().setAnimation(IDLE);
            //System.out.println("IDLE");
            //return PlayState.CONTINUE;
            return PlayState.CONTINUE;
        }


    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        if (lazyItemHandler != null)
            lazyItemHandler.invalidate();
    }

    @Override
    public void registerControllers(AnimationData data) {
        //data.addAnimationController(new AnimationController<Trainer_Tile>(this, "controller", 0, this::predicate));
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
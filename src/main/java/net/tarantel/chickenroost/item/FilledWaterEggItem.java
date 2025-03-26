package net.tarantel.chickenroost.item;

import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class FilledWaterEggItem extends AbstractBucketItem {

    private final FlowingFluid fluids;


    public FilledWaterEggItem(Properties builder, FlowingFluid fluids) {
        super(Fluids.WATER.delegate, builder);
        this.fluids = fluids;
    }

    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable CompoundNBT nbt) {
        return new FilledCeramicBucketFluidHandler(stack);
    }

    public ItemStack getFilledInstance(@Nonnull Fluid fluid, @Nullable ItemStack oldStack) {
        ItemStack stack = new ItemStack(this);
        if (oldStack != null) {
            copyNBTWithoutBucketContent(oldStack, stack);
        }
        return fill(stack, new FluidStack(fluid, FluidAttributes.BUCKET_VOLUME));
    }

    @Nonnull
    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getDefaultInstance() {
        return this.getFilledInstance(Fluids.WATER, null);
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    public void fillItemCategory(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
        if (this.allowdedIn(group)) {
            ArrayList<Fluid> addedFluids = new ArrayList<>();
            Fluid fluid = Fluids.WATER;
            Item bucket = fluid.getBucket();
            if (bucket instanceof BucketItem) {
                Fluid bucketFluid = ((BucketItem) bucket).getFluid();
                if (!addedFluids.contains(bucketFluid)) {
                    items.add(getFilledInstance(bucketFluid, null));
                    addedFluids.add(bucketFluid);
                }
            }
        }
    }

    @Override
    @Nonnull
    public ITextComponent getName(@Nonnull ItemStack stack) {
        if (getFluid(stack) == Fluids.EMPTY) {
            return new TranslationTextComponent("item.chicken_roost.water_egg");
        } else {
            ITextComponent fluidText = new TranslationTextComponent(getFluid(stack).getAttributes().getTranslationKey());
            return new TranslationTextComponent("item.chicken_roost.water_egg.filled", fluidText);
        }
    }

}
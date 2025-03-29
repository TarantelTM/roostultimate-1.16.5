package net.tarantel.chickenroost.item.base;


import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;
import net.tarantel.chickenroost.util.Config;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;

public class AnimatedChicken_6 extends ChickenItemBase implements IAnimatable {

    private String localpath;
    public int currentchickena;

    public AnimationFactory factory = GeckoLibUtil.createFactory(this);
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
    private static final AnimationBuilder IDLE = new AnimationBuilder().loop("idle");
    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        event.getController().setAnimation(IDLE);
        return PlayState.CONTINUE;
    }


    public AnimatedChicken_6(Properties properties, String path, int currentchicken) {
        super(properties, currentchicken);
        this.localpath = path;
        this.currentchickena = currentchicken;
    }
    public String getLocalpath() {
        return localpath;
    }


    public static BlockPos rightposi(BlockPos blockPos, Direction direction)
    {
        final int[] XSide = new int[]{0, 0, 0, 0, -1, 1};
        final int[] YSide = new int[]{-1, 1, 0, 0, 0, 0};
        final int[] ZSide = new int[]{0, 0, -1, 1, 0, 0};

        int X = blockPos.getX() + XSide[direction.ordinal()];
        int Y = blockPos.getY() + YSide[direction.ordinal()];
        int Z = blockPos.getZ() + ZSide[direction.ordinal()];

        return new BlockPos(X, Y, Z);
    }



    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World level = context.getLevel();
        PlayerEntity player = context.getPlayer();
        if (!(level instanceof ServerWorld)) {
            return ActionResultType.SUCCESS;
        } else {
            ItemStack itemstack = context.getItemInHand();
            BlockPos blockpos = rightposi(context.getClickedPos(), context.getClickedFace());
            Direction direction = context.getClickedFace();
            BlockState blockstate = level.getBlockState(blockpos);
            BlockPos blockpos1;
            if (blockstate.getCollisionShape(level, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.relative(direction);
            }
            if (ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation("c:roost/mobs")).contains(itemstack.getItem())) {
                EntityType<?> entitytype = (ForgeRegistries.ENTITIES.getValue(context.getItemInHand().getItem().getRegistryName()));
                Entity entity = entitytype.spawn((ServerWorld)level, itemstack, context.getPlayer(), blockpos, SpawnReason.SPAWN_EGG, false, false);
                if (entity == null) {
                    return ActionResultType.PASS;
                } else {
                    if(entity.getPersistentData().contains("roost_lvl")){
                        entity.getPersistentData().putInt("roost_lvl", itemstack.getOrCreateTag().getInt("roost_lvl"));
                    }
                    if(entity.getPersistentData().contains("roost_xp")){
                        entity.getPersistentData().putInt("roost_xp", itemstack.getOrCreateTag().getInt("roost_xp"));
                    }
                    if(!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    context.getPlayer().awardStat(Stats.ITEM_USED.get(this));
                    //entity.setUUID(UUID.randomUUID()); // Ensure a unique UUID
                    level.addFreshEntity(entity);
                }


            }
            else {
                EntityType<?> entitytype = (ForgeRegistries.ENTITIES.getValue(new ResourceLocation("minecraft:chicken")));
                Entity entity = entitytype.spawn((ServerWorld)level, itemstack, context.getPlayer(), blockpos, SpawnReason.SPAWN_EGG, false, false);
                if (entity == null) {
                    return ActionResultType.PASS;
                } else {
                    if(entity.getPersistentData().contains("roost_lvl")){
                        entity.getPersistentData().putInt("roost_lvl", itemstack.getOrCreateTag().getInt("roost_lvl"));
                    }
                    if(entity.getPersistentData().contains("roost_xp")){
                        entity.getPersistentData().putInt("roost_xp", itemstack.getOrCreateTag().getInt("roost_xp"));
                    }
                    if(!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    context.getPlayer().awardStat(Stats.ITEM_USED.get(this));
                    //entity.setUUID(UUID.randomUUID()); // Ensure a unique UUID
                    level.addFreshEntity(entity);
                    System.out.println("UseOn");
                }
            }

            return ActionResultType.CONSUME;
        }
    }

    @Override
    public void appendHoverText(ItemStack itemstack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {

        list.add(ITextComponent.nullToEmpty("\u00A71" + "Tier: " + "\u00A79" + "6"));
        list.add(ITextComponent.nullToEmpty((("\u00A7e") + "Level: " + "\u00A79" + ((itemstack).getOrCreateTag().getInt("roost_lvl")) + "/" + (((int) Config.maxlevel_tier_6.get())))));
        list.add(ITextComponent.nullToEmpty((("\u00A7a") + "XP: " + "\u00A79" + ((itemstack).getOrCreateTag().getInt("roost_xp")) + "/" + (((int) Config.xp_tier_6.get())))));
        super.appendHoverText(itemstack, world, list, flag);
    }

}
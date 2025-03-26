package net.tarantel.chickenroost.item.base;


import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.util.Config;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.SoundKeyframeEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class AnimatedChicken_5 extends ChickenItemBase implements IAnimatable, ISyncable {

    private String localpath;
    public int currentchickena;
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private static final String CONTROLLER_NAME = "popupController";
    private static final int ANIM_OPEN = 0;



    public AnimatedChicken_5(Properties properties, String path, int currentchicken) {
        super(properties, currentchicken);
        this.localpath = path;
        this.currentchickena = currentchicken;
        //GeckoLibNetwork.registerSyncable(this);
    }
    public String getLocalpath() {
        return localpath;
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<AnimatedChicken_5> controller = new AnimationController<AnimatedChicken_5>(this, CONTROLLER_NAME, 20, this::predicate);

        controller.registerSoundListener(this::soundListener);
        data.addAnimationController(controller);
    }

    private <ENTITY extends IAnimatable> void soundListener(SoundKeyframeEvent<ENTITY> event) {
        ClientPlayerEntity player = Minecraft.getInstance().player;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
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

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        // Not setting an animation here as that's handled below
        return PlayState.CONTINUE;
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
                    entity.getPersistentData().putInt("roost_lvl", itemstack.getOrCreateTag().getInt("roost_lvl"));
                    entity.getPersistentData().putInt("roost_xp", itemstack.getOrCreateTag().getInt("roost_xp"));
                    if(!player.isCreative()) {
                            itemstack.shrink(1);
                        }
                    context.getPlayer().awardStat(Stats.ITEM_USED.get(this));
                    entity.setUUID(UUID.randomUUID()); // Ensure a unique UUID
                    level.addFreshEntity(entity);
                }


            }
            else {
                EntityType<?> entitytype = (ForgeRegistries.ENTITIES.getValue(new ResourceLocation("minecraft:chicken")));
                Entity entity = entitytype.spawn((ServerWorld)level, itemstack, context.getPlayer(), blockpos, SpawnReason.SPAWN_EGG, false, false);
                if (entity == null) {
                    return ActionResultType.PASS;
                } else {
                    entity.getPersistentData().putInt("roost_lvl", itemstack.getOrCreateTag().getInt("roost_lvl"));
                    entity.getPersistentData().putInt("roost_xp", itemstack.getOrCreateTag().getInt("roost_xp"));
                    if(!player.isCreative()) {
                            itemstack.shrink(1);
                        }
                    context.getPlayer().awardStat(Stats.ITEM_USED.get(this));
                    entity.setUUID(UUID.randomUUID()); // Ensure a unique UUID
                    level.addFreshEntity(entity);
                }
            }
            //EntityType<?> entitytype = (BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(context.getItemInHand().getItem().getDefaultInstance().getItemHolder().getRegisteredName().toString())));


            return ActionResultType.CONSUME;
        }
    }

    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockRayTraceResult blockhitresult = getPlayerPOVHitResult(level, player, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (blockhitresult.getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.pass(itemstack);
        } else if (!(level instanceof ServerWorld)) {
            return ActionResult.success(itemstack);
        } else {
            BlockPos blockpos = new BlockPos(blockhitresult.getLocation());
            if (!(level.getBlockState(blockpos).getBlock() instanceof FlowingFluidBlock)) {
                return ActionResult.pass(itemstack);
            } else if (level.mayInteract(player, blockpos) && player.mayUseItemAt(blockpos, blockhitresult.getDirection(), itemstack)) {
                if (ItemTags.getAllTags().getTagOrEmpty(new ResourceLocation("c:roost/mobs")).contains(itemstack.getItem())) {
                    EntityType<?> entitytype = (ForgeRegistries.ENTITIES.getValue(player.getItemInHand(hand).getItem().getRegistryName()));
                    Entity entity = entitytype.spawn((ServerWorld) level, itemstack, player, blockpos, SpawnReason.SPAWN_EGG, false, false);
                    if (entity == null) {
                        return ActionResult.pass(itemstack);
                    } else {
                        entity.getPersistentData().putInt("roost_lvl", itemstack.getOrCreateTag().getInt("roost_lvl"));
                        entity.getPersistentData().putInt("roost_xp", itemstack.getOrCreateTag().getInt("roost_xp"));
                        if(!player.isCreative()) {
                            itemstack.shrink(1);
                        }
                        player.awardStat(Stats.ITEM_USED.get(this));
                        //level.gameEvent(player, GameEvent.ENTITY_PLACE, entity.position());
                        CriteriaTriggers.SUMMONED_ENTITY.trigger((ServerPlayerEntity) player, entity);
                        entity.setUUID(UUID.randomUUID()); // Ensure a unique UUID
                        level.addFreshEntity(entity);
                        return ActionResult.consume(itemstack);
                    }
                }
                else {
                    EntityType<?> entitytype = (ForgeRegistries.ENTITIES.getValue(new ResourceLocation("minecraft:chicken")));
                    Entity entity = entitytype.spawn((ServerWorld) level, itemstack, player, blockpos, SpawnReason.SPAWN_EGG, false, false);
                    if (entity == null) {
                        return ActionResult.pass(itemstack);
                    } else {
                        entity.getPersistentData().putInt("roost_lvl", itemstack.getOrCreateTag().getInt("roost_lvl"));
                        entity.getPersistentData().putInt("roost_xp", itemstack.getOrCreateTag().getInt("roost_xp"));
                        if(!player.isCreative()) {
                            itemstack.shrink(1);
                        }
                        CriteriaTriggers.SUMMONED_ENTITY.trigger((ServerPlayerEntity) player, entity);
                        entity.setUUID(UUID.randomUUID()); // Ensure a unique UUID
                        level.addFreshEntity(entity);
                        //level.gameEvent(player, GameEvent.ENTITY_PLACE, entity.position());
                        return ActionResult.consume(itemstack);
                    }
                }
            } else {
                return ActionResult.fail(itemstack);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack itemstack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {

        list.add(ITextComponent.nullToEmpty("\u00A71" + "Tier: " + "\u00A79" + "5"));
        list.add(ITextComponent.nullToEmpty((("\u00A7e") + "Level: " + "\u00A79" + ((itemstack).getOrCreateTag().getInt("roost_lvl")) + "/" + (((int) Config.maxlevel_tier_5.get())))));
        list.add(ITextComponent.nullToEmpty((("\u00A7a") + "XP: " + "\u00A79" + ((itemstack).getOrCreateTag().getInt("roost_xp")) + "/" + (((int) Config.xp_tier_5.get())))));
        super.appendHoverText(itemstack, world, list, flag);
    }


    @Override
    public void onAnimationSync(int id, int state) {
        if (state == ANIM_OPEN) {
            // Always use GeckoLibUtil to get AnimationControllers when you don't have
            // access to an AnimationEvent
            final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, CONTROLLER_NAME);

            if (controller.getAnimationState() == AnimationState.Stopped) {
                final ClientPlayerEntity player = Minecraft.getInstance().player;
                // If you don't do this, the popup animation will only play once because the
                // animation will be cached.
                controller.markNeedsReload();
                // Set the animation to open the JackInTheBoxItem which will start playing music
                // and
                // eventually do the actual animation. Also sets it to not loop
                controller.setAnimation(new AnimationBuilder().addAnimation("Soaryn_chest_popup", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
            }
        }
    }
}
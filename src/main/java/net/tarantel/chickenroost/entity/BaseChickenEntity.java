package net.tarantel.chickenroost.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import net.tarantel.chickenroost.util.ChickenConfig;

public class BaseChickenEntity extends ChickenEntity {
    public int eggTime;

    public ItemStack dropStack;
    public Boolean IS_FIRE;
    public Boolean IS_PROJECTILE;
    public Boolean IS_EXPLOSION;
    public Boolean IS_FALL;
    public Boolean IS_DROWNING;
    public Boolean IS_FREEZING;
    public Boolean IS_LIGHTNING;
    public Boolean IS_WITHER;
    //private final Ingredient FOOD_ITEMS = Ingredient.of(new IItemProvider[]{dropStack.getItem()});

    public BaseChickenEntity(EntityType<BaseChickenEntity> type, World world) {
        super(type, world);
        this.xpReward = 0;
        this.setNoAi(false);
        this.setPersistenceRequired();

        // Initialize from static configuration
        this.dropStack = ChickenConfig.getDropStack(type);
        this.eggTime = ChickenConfig.getEggTime(type);

        this.IS_FIRE = ChickenConfig.getIsFire(type);
        this.IS_PROJECTILE = ChickenConfig.getIsProjectile(type);
        this.IS_EXPLOSION = ChickenConfig.getIsExplosion(type);
        this.IS_FALL = ChickenConfig.getIsFall(type);
        this.IS_DROWNING = ChickenConfig.getIsDrowning(type);
        this.IS_FREEZING = ChickenConfig.getIsFreezing(type);
        this.IS_LIGHTNING = ChickenConfig.getIsLightning(type);
        this.IS_WITHER = ChickenConfig.getIsWither(type);
        this.setPathfindingMalus(PathNodeType.WATER, 0.0F);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4));
        //this.goalSelector.addGoal(3, new TemptGoal(this, (double)1.0F, false, FOOD_ITEMS));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, (double)1.0F));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }
    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        System.out.println("ItemName: " + dropStack.getItem().getRegistryName());
        this.spawnAtLocation(dropStack.getStack());
    }

    public void aiStep() {
        super.aiStep();
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed = (float)((double)this.flapSpeed + (double)(this.onGround ? -1 : 4) * 0.3);
        this.flapSpeed = MathHelper.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping = (float)((double)this.flapping * 0.9);
        Vector3d lvt_1_1_ = this.getDeltaMovement();
        if (!this.onGround && lvt_1_1_.y < (double)0.0F) {
            this.setDeltaMovement(lvt_1_1_.multiply((double)1.0F, 0.6, (double)1.0F));
        }

        this.flap += this.flapping * 2.0F;
        if (!this.level.isClientSide && this.isAlive() && !this.isBaby() && !this.isChickenJockey() && --this.eggTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(Items.EGG);
            this.eggTime = this.random.nextInt(6000) + 6000;
        }

    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (damageSource == (DamageSource.ON_FIRE) || damageSource == (DamageSource.LAVA) || damageSource == (DamageSource.IN_FIRE)){
            if(IS_FIRE){
                return super.hurt(damageSource, amount);
            }
            else {
                return false;
            }
        }
        else if (damageSource == (DamageSource.FALL)){
            if(IS_FALL){
                return super.hurt(damageSource, amount);
            }
            else {
                return false;
            }
        }
        else if (damageSource == (DamageSource.DROWN)){
            if(IS_DROWNING){
                return super.hurt(damageSource, amount);
            }
            else {
                return false;
            }
        }
        else if (damageSource == (DamageSource.LIGHTNING_BOLT)) {
            if(IS_LIGHTNING){
                return super.hurt(damageSource, amount);
            }
            else {
                return false;
            }
        }
        else if (damageSource == (DamageSource.WITHER)) {
            if(IS_WITHER){
                return super.hurt(damageSource, amount);
            }
            else {
                return false;
            }
        }
        System.out.println("DamageSource: " + damageSource.toString());
        return super.hurt(damageSource, amount);
    }

}
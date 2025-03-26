package net.tarantel.chickenroost.item;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.UUID;

public class RoostThrownEgg extends ProjectileItemEntity {


    private EntityType customtype;

    /*protected Item getDefaultItem() {
        return ModItems.LAVA_EGG.get();
    }*/


    public RoostThrownEgg(EntityType<? extends EggEntity> entity, World level) {
        super(entity, level);
        this.customtype = entity;
    }

    public RoostThrownEgg(World level, LivingEntity entity) {
        super(EntityType.EGG, entity, level);

    }

    public RoostThrownEgg(World level, double p_i1781_2_, double p_i1781_4_, double p_i1781_6_) {
        super(EntityType.EGG, p_i1781_2_, p_i1781_4_, p_i1781_6_, level);
    }

    @OnlyIn(Dist.CLIENT)
    public void handleEntityEvent(byte p_70103_1_) {
        if (p_70103_1_ == 3) {
            double lvt_2_1_ = 0.08;

            for(int lvt_4_1_ = 0; lvt_4_1_ < 8; ++lvt_4_1_) {
                this.level.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - (double)0.5F) * 0.08, ((double)this.random.nextFloat() - (double)0.5F) * 0.08, ((double)this.random.nextFloat() - (double)0.5F) * 0.08);
            }
        }

    }

    protected void onHitEntity(EntityRayTraceResult p_213868_1_) {
        super.onHitEntity(p_213868_1_);
        p_213868_1_.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);
    }

    protected void onHit(RayTraceResult p_70227_1_) {
        super.onHit(p_70227_1_);
        if (!this.level.isClientSide) {
            if (this.random.nextInt(8) == 0) {
                int lvt_2_1_ = 1;
                if (this.random.nextInt(32) == 0) {
                    lvt_2_1_ = 4;
                }

                for(int lvt_3_1_ = 0; lvt_3_1_ < lvt_2_1_; ++lvt_3_1_) {
                    Entity chicken = customtype.create(this.level);
                    chicken.moveTo(this.getX(), this.getY(), this.getZ(), this.yRot, 0.0F);
                    chicken.setUUID(UUID.randomUUID()); // Ensure a unique UUID
                    this.level.addFreshEntity(chicken);
                }
            }

            this.level.broadcastEntityEvent(this, (byte)3);
            this.remove();
        }

    }

    @Override
    protected Item getDefaultItem() {
        return Items.EGG;
    }
}
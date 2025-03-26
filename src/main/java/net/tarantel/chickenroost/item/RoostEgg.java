package net.tarantel.chickenroost.item;

import net.minecraft.client.audio.SoundSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EggItem;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.UUID;

public class RoostEgg extends EggItem {
    public EntityType entity;
    public ResourceLocation resourceLocation;

    public RoostEgg(ResourceLocation resourceLocation, Properties properties) {
        super( properties);
        this.resourceLocation = resourceLocation;
    }
    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        level.playSound((PlayerEntity)null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        if (!level.isClientSide) {
            EntityType entityType = EntityType.byString(this.resourceLocation.toString()).orElse(EntityType.CHICKEN);
            RoostThrownEgg thrownegg = new RoostThrownEgg(entityType, level);
            thrownegg.setItem(itemstack);
            thrownegg.shootFromRotation(player, player.getRotationVector().x, player.getRotationVector().y, 0.0F, 1.5F, 1.0F);
            thrownegg.setUUID(UUID.randomUUID()); // Ensure a unique UUID
            level.addFreshEntity(thrownegg);

        }

        player.awardStat(Stats.ITEM_USED.get(this));
        itemstack.shrink(1);
        return ActionResult.sidedSuccess(itemstack, level.isClientSide());
    }
}

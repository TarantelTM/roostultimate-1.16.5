package net.tarantel.chickenroost.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class ChickenStickTool {

    public static void execute(World world, double x, double y, double z, Entity entity) {
        if (entity == null)
            return;

        ItemStack itemchicken = ItemStack.EMPTY;
        //if (entity.getType().is(ModTags.ROOSTCHICKENS)) {
            if(EntityTypeTags.getAllTags().getTagOrEmpty(new ResourceLocation("forge:roostultimate")).contains(entity.getType())){
            String newentity = entity.getEncodeId().toLowerCase();
            ResourceLocation myEntity = ResourceLocation.tryParse(newentity.toString());

            itemchicken = new ItemStack(ForgeRegistries.ITEMS.getValue((myEntity)));
            itemchicken.getOrCreateTag().putInt("roost_lvl", entity.getPersistentData().getInt("roost_lvl"));
            itemchicken.getOrCreateTag().putInt("roost_xp", entity.getPersistentData().getInt("roost_xp"));
            if (world instanceof World && !world.isClientSide) {
                ItemEntity entityToSpawn = new ItemEntity(world, x, y, z, itemchicken);
                entityToSpawn.setPickUpDelay(10);
                world.addFreshEntity(entityToSpawn);
            }

            if (!entity.level.isClientSide())
                entity.remove();
        }
        if(EntityTypeTags.getAllTags().getTagOrEmpty(new ResourceLocation("forge:vanilla")).contains(entity.getType())){
            String newentity = ("chicken_roost:c_vanilla");
            ResourceLocation myEntity = ResourceLocation.tryParse(newentity.toString());

            itemchicken = new ItemStack(ForgeRegistries.ITEMS.getValue((myEntity)));
            itemchicken.getOrCreateTag().putInt("roost_lvl", entity.getPersistentData().getInt("roost_lvl"));
            itemchicken.getOrCreateTag().putInt("roost_xp", entity.getPersistentData().getInt("roost_xp"));
            if (world instanceof World && !world.isClientSide) {
                ItemEntity entityToSpawn = new ItemEntity(world, x, y, z, itemchicken);
                entityToSpawn.setPickUpDelay(10);
                world.addFreshEntity(entityToSpawn);
            }

            if (!entity.level.isClientSide())
                entity.remove();
        }
    }
}
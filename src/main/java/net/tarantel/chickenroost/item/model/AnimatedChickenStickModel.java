package net.tarantel.chickenroost.item.model;

import net.minecraft.util.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.ChickenStickItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;
public class AnimatedChickenStickModel extends AnimatedGeoModel<ChickenStickItem> {
    @Override
    public ResourceLocation getModelLocation(ChickenStickItem animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "geo/chicken_stick.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ChickenStickItem animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "textures/item/chicken_stick.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ChickenStickItem animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "animations/chicken_stick.animation.json");
    }
}
package net.tarantel.chickenroost.item.model;

import net.minecraft.util.ResourceLocation;
import net.tarantel.chickenroost.ChickenRoostMod;
import net.tarantel.chickenroost.item.base.AnimatedChicken_8;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AnimatedChickenModel_8 extends AnimatedGeoModel<AnimatedChicken_8> {
    @Override
    public ResourceLocation getModelLocation(AnimatedChicken_8 animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "geo/renderchicken.geo.json");
    }


    @Override
    public ResourceLocation getTextureLocation(AnimatedChicken_8 animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "textures/block/" + animatable.getLocalpath() + ".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AnimatedChicken_8 animatable) {
        return new ResourceLocation(ChickenRoostMod.MODID, "animations/renderchicken.animation.json");
    }
}
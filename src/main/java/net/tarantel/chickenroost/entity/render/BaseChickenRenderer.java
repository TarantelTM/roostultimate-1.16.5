package net.tarantel.chickenroost.entity.render;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.tarantel.chickenroost.entity.BaseChickenEntity;
import net.tarantel.chickenroost.util.Modelchicken;

public class BaseChickenRenderer extends MobRenderer<BaseChickenEntity, Modelchicken<BaseChickenEntity>> {
    public String texture;
    public BaseChickenRenderer(EntityRendererManager context, String texture) {
        super(context, new Modelchicken(), 0.5f);
        this.texture = texture;
    }

    @Override
    public ResourceLocation getTextureLocation(BaseChickenEntity entity) {
        return new ResourceLocation("chicken_roost:textures/entities/" + this.texture + ".png");
    }
}

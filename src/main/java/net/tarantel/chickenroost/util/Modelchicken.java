package net.tarantel.chickenroost.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.tarantel.chickenroost.entity.BaseChickenEntity;

// Made with Blockbench 4.2.4
// Exported for Minecraft version 1.16.5 with Mojang mappings
// Paste this class into your mod and generate all required imports
public class Modelchicken<T extends Entity> extends EntityModel<BaseChickenEntity> {
    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer leg0;
    private final ModelRenderer leg1;
    private final ModelRenderer wing0;
    private final ModelRenderer wing1;

    public Modelchicken() {
        texWidth = 64;
        texHeight = 32;

        body = new ModelRenderer(this);
        body.setPos(0.0F, 16.0F, 0.0F);
        body.texOffs(0, 9).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F, false);
        //body.setRotationPoint(1.5708F, 0.0F, 0.0F);

        head = new ModelRenderer(this);
        head.setPos(0.0F, 15.0F, -4.0F);
        head.texOffs(0, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, 0.0F, false);

        ModelRenderer comb = new ModelRenderer(this);
        comb.setPos(0.0F, 0.0F, 0.0F);
        head.addChild(comb);
        comb.texOffs(14, 4).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        ModelRenderer beak = new ModelRenderer(this);
        beak.setPos(0.0F, 0.0F, 0.0F);
        head.addChild(beak);
        beak.texOffs(14, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, 0.0F, false);

        leg0 = new ModelRenderer(this);
        leg0.setPos(-2.0F, 19.0F, 1.0F);
        leg0.texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);

        leg1 = new ModelRenderer(this);
        leg1.setPos(1.0F, 19.0F, 1.0F);
        leg1.texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);

        wing0 = new ModelRenderer(this);
        wing0.setPos(-3.0F, 13.0F, 0.0F);
        wing0.texOffs(24, 13).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);

        wing1 = new ModelRenderer(this);
        wing1.setPos(3.0F, 13.0F, 0.0F);
        wing1.texOffs(24, 13).addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, false);
    }



    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        head.render(matrixStack, buffer, packedLight, packedOverlay);
        leg0.render(matrixStack, buffer, packedLight, packedOverlay);
        leg1.render(matrixStack, buffer, packedLight, packedOverlay);
        wing0.render(matrixStack, buffer, packedLight, packedOverlay);
        wing1.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public void setupAnim(BaseChickenEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        this.head.yRot = netHeadYaw / (180F / (float) Math.PI);
        this.head.xRot = headPitch / (180F / (float) Math.PI);
        this.leg0.xRot = MathHelper.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
        this.leg1.xRot = MathHelper.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
        this.wing1.zRot = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount;
        this.wing0.zRot = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
    }
}
package net.tarantel.chickenroost.block.tile.render;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.VertexBuilderUtils;
import net.java.games.input.Component;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.client.model.pipeline.VertexBufferConsumer;
import net.tarantel.chickenroost.block.blocks.Breeder_Block;
import net.tarantel.chickenroost.block.blocks.model.AnimatedTrainerModel;
import net.tarantel.chickenroost.block.tile.Breeder_Tile;
import net.tarantel.chickenroost.block.tile.Trainer_Tile;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

import javax.annotation.Nullable;

public class AnimatedTrainerRenderer extends GeoBlockRenderer<Trainer_Tile> {

    public AnimatedTrainerRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new AnimatedTrainerModel());

    }

    @Override
    public RenderType getRenderType(Trainer_Tile animatable, float partialTicks, MatrixStack stack,
                                    IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        //System.out.println("getRenderType: " + getTextureLocation(animatable));
        return RenderType.entityTranslucent(textureLocation);
    }


    @Override
    public void render(Trainer_Tile tile, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn) {
        System.out.println("render created");
        World level = Minecraft.getInstance().level;
        ItemStack itemStack2 = tile.getRenderStack1();
        MatrixStack poseStack2 = matrixStackIn;
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

        poseStack2.pushPose();

        poseStack2.scale(1.0f, 1.0f, 1.0f);
        poseStack2.mulPose(Vector3f.XP.rotationDegrees(0));

        switch (tile.getBlockState().getValue(Breeder_Block.FACING)) {
            case NORTH:
                poseStack2.translate(0.37f, 0.6f, 0.88f);
                poseStack2.mulPose(Vector3f.ZP.rotationDegrees(0));
                poseStack2.mulPose(Vector3f.YP.rotationDegrees(0));
                break;
            case EAST:
                poseStack2.translate(0.55f, 0.6f, 0.366f);
                poseStack2.mulPose(Vector3f.ZP.rotationDegrees(0));
                poseStack2.mulPose(Vector3f.YP.rotationDegrees(-90));
                break;
            case SOUTH:
                poseStack2.translate(1.062f, 0.6f, 0.465f);
                poseStack2.mulPose(Vector3f.ZP.rotationDegrees(0));
                poseStack2.mulPose(Vector3f.YP.rotationDegrees(-180));
                break;
            case WEST:
                poseStack2.translate(0.963f, 0.6f, 1.06f);
                poseStack2.mulPose(Vector3f.ZP.rotationDegrees(0));
                poseStack2.mulPose(Vector3f.XP.rotationDegrees(0));
                poseStack2.mulPose(Vector3f.YP.rotationDegrees(+90));
                break;
        }

        renderer.renderStatic(itemStack2, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, 0, matrixStackIn, bufferIn);
        poseStack2.popPose();
    }
}
package net.tarantel.chickenroost.block.tile.render;


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.vector.Vector3f;
import net.tarantel.chickenroost.block.blocks.Breeder_Block;
import net.tarantel.chickenroost.block.blocks.Trainer_Block;
import net.tarantel.chickenroost.block.blocks.model.AnimatedTrainerModel;
import net.tarantel.chickenroost.block.tile.Trainer_Tile;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class AnimatedTrainerRenderer extends GeoBlockRenderer<Trainer_Tile> {

    public AnimatedTrainerRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new AnimatedTrainerModel());
    }
    /*@Override
    public void renderEarly(Trainer_Tile tile, MatrixStack stack, float ticks, IRenderTypeBuffer renderTypeBuffer,  IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        ItemStack itemStack2 = tile.getRenderStack1();
        MatrixStack poseStack2 = stack;
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        poseStack2.pushPose();
        poseStack2.translate(0.0f, 0.5f, 0.0f);
        poseStack2.scale(1.0f, 1.0f, 1.0f);
        poseStack2.mulPose(Vector3f.ZP.rotationDegrees(0));
        poseStack2.mulPose(Vector3f.XP.rotationDegrees(0));
        poseStack2.mulPose(Vector3f.YP.rotationDegrees(0));
        renderer.renderStatic(itemStack2, ItemCameraTransforms.TransformType.FIXED, packedLightIn, 0, stack, renderTypeBuffer);
        poseStack2.popPose();
    }*/

    @Override
    public void render(TileEntity tile, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
            Trainer_Tile trainerTile = (Trainer_Tile) tile;
            ItemStack itemStack2 = trainerTile.getRenderStack1();
            MatrixStack poseStack2 = matrixStackIn;
            ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
            poseStack2.pushPose();
            //poseStack2.translate(0.0f, 0.5f, 0.0f);
            poseStack2.scale(1.0f, 1.0f, 1.0f);
            //poseStack2.mulPose(Vector3f.ZP.rotationDegrees(0));
            //poseStack2.mulPose(Vector3f.XP.rotationDegrees(0));
            poseStack2.mulPose(Vector3f.YP.rotationDegrees(0));
        switch (trainerTile.getBlockState().getValue(Trainer_Block.FACING)) {
            case NORTH:
                poseStack2.translate(0.5f, 0.5f, 0.5f);
                poseStack2.mulPose(Vector3f.ZP.rotationDegrees(0));
                poseStack2.mulPose(Vector3f.YP.rotationDegrees(0));
                break;
            case EAST:
                poseStack2.translate(0.5f, 0.5f, 0.5f);
                poseStack2.mulPose(Vector3f.ZP.rotationDegrees(0));
                poseStack2.mulPose(Vector3f.XP.rotationDegrees(0));
                poseStack2.mulPose(Vector3f.YP.rotationDegrees(-90));
                break;
            case SOUTH:
                poseStack2.translate(0.5f, 0.5f, 0.5f);
                poseStack2.mulPose(Vector3f.ZP.rotationDegrees(0));
                poseStack2.mulPose(Vector3f.YP.rotationDegrees(-180));
                break;
            case WEST:
                poseStack2.translate(0.5f, 0.5f, 0.5f);
                poseStack2.mulPose(Vector3f.ZP.rotationDegrees(0));
                poseStack2.mulPose(Vector3f.XP.rotationDegrees(0));
                poseStack2.mulPose(Vector3f.YP.rotationDegrees(90));
                break;
        }
            renderer.renderStatic(itemStack2, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn);
            poseStack2.popPose();


            this.render(trainerTile, partialTicks, matrixStackIn, bufferIn, combinedLightIn);


    }
}
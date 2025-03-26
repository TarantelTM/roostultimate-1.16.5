package net.tarantel.chickenroost.block.tile.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.tarantel.chickenroost.block.blocks.Breeder_Block;
import net.tarantel.chickenroost.block.tile.Breeder_Tile;

public class BreederChickenRender extends TileEntityRenderer<Breeder_Tile> {
    public BreederChickenRender(TileEntityRendererDispatcher dispatcher) {

        super(dispatcher);
    }

    @Override
    public void render(Breeder_Tile pBlockEntity, float partialTicks, MatrixStack matrix, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        //NonNullList<ItemStack> contents = pBlockEntity.getContents();
        World level = Minecraft.getInstance().level;
        ItemStack itemStack1 = pBlockEntity.getRenderStack1();
        ItemStack itemStack2 = pBlockEntity.getRenderStack2();
        ItemStack itemStack3 = pBlockEntity.getRenderStack3();
        MatrixStack poseStack1 = matrix;
        MatrixStack poseStack2 = matrix;
        MatrixStack poseStack3 = matrix;
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

        poseStack2.pushPose();

        poseStack2.scale(0.7f, 0.7f, 0.7f);
        poseStack2.mulPose(Vector3f.XP.rotationDegrees(0));

        switch (pBlockEntity.getBlockState().getValue(Breeder_Block.FACING)) {
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

        renderer.renderStatic(itemStack2, ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrix, buffer);
        poseStack2.popPose();

        poseStack1.pushPose();
        poseStack1.scale(0.7f, 0.7f, 0.7f);
        poseStack1.mulPose(Vector3f.XP.rotationDegrees(0));

        switch (pBlockEntity.getBlockState().getValue(Breeder_Block.FACING)) {
            case NORTH:
                poseStack1.translate(1.06f, 0.6f, 0.88f);
                poseStack1.mulPose(Vector3f.ZP.rotationDegrees(0));
                poseStack1.mulPose(Vector3f.YP.rotationDegrees(0));
                break;
                case EAST:
                poseStack1.translate(0.55f, 0.6f, 1.06f);
                poseStack1.mulPose(Vector3f.ZP.rotationDegrees(0));
                poseStack1.mulPose(Vector3f.YP.rotationDegrees(-90));
                break;
                case SOUTH:
                poseStack1.translate(0.362f, 0.6f, 0.465f);
                poseStack1.mulPose(Vector3f.ZP.rotationDegrees(0));
                poseStack1.mulPose(Vector3f.YP.rotationDegrees(-180));
                break;
                case WEST:
                poseStack1.translate(0.963f, 0.6f, 0.365f);
                poseStack1.mulPose(Vector3f.ZP.rotationDegrees(0));
                poseStack1.mulPose(Vector3f.XP.rotationDegrees(0));
                poseStack1.mulPose(Vector3f.YP.rotationDegrees(+90));
                break;
        }

        renderer.renderStatic(itemStack1, ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrix, buffer);
        poseStack1.popPose();

        poseStack3.pushPose();
        poseStack3.scale(0.5f, 0.5f, 0.5f);
        poseStack3.mulPose(Vector3f.XP.rotationDegrees(90));

        switch (pBlockEntity.getBlockState().getValue(Breeder_Block.FACING)) {
            case NORTH:
                poseStack3.translate(1.0f, 0.5f, -0.4f);
                poseStack3.mulPose(Vector3f.ZP.rotationDegrees(0));
                poseStack3.mulPose(Vector3f.YP.rotationDegrees(0));
                break;
                case EAST:
                poseStack3.translate(1.5f, 1.0f, -0.4f);
                poseStack3.mulPose(Vector3f.ZP.rotationDegrees(90));
                poseStack3.mulPose(Vector3f.YP.rotationDegrees(0));
                break;
                case SOUTH:
                poseStack3.translate(1.0f, 1.5f, -0.4f);
                poseStack3.mulPose(Vector3f.ZP.rotationDegrees(0));
                poseStack3.mulPose(Vector3f.YP.rotationDegrees(-180));
                break;
                case WEST:
                poseStack3.translate(0.5f, 1.0f, -0.4f);
                poseStack3.mulPose(Vector3f.ZP.rotationDegrees(0));
                poseStack3.mulPose(Vector3f.XP.rotationDegrees(0));
                poseStack3.mulPose(Vector3f.YP.rotationDegrees(0));
                break;
        }

        renderer.renderStatic(itemStack3, ItemCameraTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrix, buffer);
        poseStack3.popPose();
    }

    /*private int getLightLevel(World level, BlockPos pos) {
        int bLight = level.getBrightness(Light.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }*/
}
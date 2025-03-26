package net.tarantel.chickenroost.block.tile.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.tarantel.chickenroost.block.blocks.Breeder_Block;
import net.tarantel.chickenroost.block.blocks.model.AnimatedTrainerModel;
import net.tarantel.chickenroost.block.tile.Trainer_Tile;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class AnimatedTrainerRendererGeo extends GeoBlockRenderer<Trainer_Tile> {
    public AnimatedTrainerRendererGeo(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new AnimatedTrainerModel());

    }
    @Override
    public RenderType getRenderType(Trainer_Tile animatable, float partialTicks, MatrixStack stack,
                                    IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
                                    ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
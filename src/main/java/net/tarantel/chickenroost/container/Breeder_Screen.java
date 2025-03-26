package net.tarantel.chickenroost.container;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.tarantel.chickenroost.ChickenRoostMod;


public class Breeder_Screen extends ContainerScreen<Breeder_Container> {
    private final ResourceLocation GUI = new ResourceLocation(ChickenRoostMod.MODID,
            "textures/screens/breedergui2025.png");
    private static final ResourceLocation ARROWBACK = new ResourceLocation("chicken_roost:textures/screens/arrowback.png");
    private static final ResourceLocation ARROW = new ResourceLocation("chicken_roost:textures/screens/arrow.png");
    public Breeder_Screen(Breeder_Container screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }
    @Override
    protected void init() {
        super.init();
    }
    //int scaledProgress = menu.getScaledProgress();

    private int scaledProgress;

    @Override
    public void tick() {
        this.scaledProgress = menu.getScaledProgress();
    }
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);

        // Render the main GUI background
        this.minecraft.getTextureManager().bind(GUI);
        this.blit(matrixStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        // Render the arrow background
        this.minecraft.getTextureManager().bind(ARROWBACK);
        this.blit(matrixStack, this.leftPos + 53, this.topPos + 30, 0, 0, 40, 10, 40, 10);

        // Render the arrow (scaled based on progress)
        this.minecraft.getTextureManager().bind(ARROW);

        this.blit(matrixStack, this.leftPos + 53, this.topPos + 30, 0, 0, this.scaledProgress, 10, 40, 10);

        // Render the progress bar (if needed)
        /*int progress = menu.getProgress();
        int maxProgress = menu.getMaxProgress();
        if (maxProgress > 0) {
            int progressWidth = (int) ((float) progress / maxProgress * 24); // Adjust the width as needed
            this.blit(matrixStack, this.leftPos + 79, this.topPos + 34, 176, 0, progressWidth, 17);
        }*/

        RenderSystem.disableBlend();
    }
}
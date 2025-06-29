package yaboichips.rouge_planets.common.world;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import yaboichips.rouge_planets.RougePlanets;

public class DimensionSkybox extends DimensionSpecialEffects {
    private static final ResourceLocation SKYBOX_TEXTURE = new ResourceLocation(RougePlanets.MODID, "textures/environment/skybox.png");

    public DimensionSkybox() {
        // cloudHeight, hasGround, hasFog
        super(192.0F, true, SkyType.NORMAL, false, false);
    }

    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
        return color; // Keep it default or tweak it
    }

    @Override
    public boolean isFoggyAt(int x, int y) {
        return false;
    }

    @Override
    public boolean renderSky(ClientLevel level, int ticks, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        setupFog.run();

        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, SKYBOX_TEXTURE);

        Tesselator tess = Tesselator.getInstance();
        BufferBuilder buffer = tess.getBuilder();

        float size = 100.0F;

        for (int i = 0; i < 6; ++i) {
            poseStack.pushPose();

            switch (i) {
                case 0 -> poseStack.mulPose(Axis.YP.rotationDegrees(-90)); // Right
                case 1 -> poseStack.mulPose(Axis.YP.rotationDegrees(90));  // Left
                case 2 -> poseStack.mulPose(Axis.XP.rotationDegrees(-90)); // Top
                case 3 -> poseStack.mulPose(Axis.XP.rotationDegrees(90));  // Bottom
                case 4 -> {
                }                                               // Back (+Z)
                case 5 -> poseStack.mulPose(Axis.YP.rotationDegrees(180)); // Front (-Z)
            }

            Matrix4f matrix = poseStack.last().pose();

            buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            buffer.vertex(matrix, -size, -size, -size).uv(0, 1).endVertex();
            buffer.vertex(matrix, size, -size, -size).uv(1, 1).endVertex();
            buffer.vertex(matrix, size, size, -size).uv(1, 0).endVertex();
            buffer.vertex(matrix, -size, size, -size).uv(0, 0).endVertex();
            tess.end();

            poseStack.popPose();
        }

        RenderSystem.depthMask(true);
        return renderSky(level, ticks, partialTick, poseStack, camera, projectionMatrix, isFoggy, setupFog);
    }
}

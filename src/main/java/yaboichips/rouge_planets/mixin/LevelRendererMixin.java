package yaboichips.rouge_planets.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yaboichips.rouge_planets.RougePlanets;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Shadow
    @Final
    private Minecraft minecraft;
    @Unique
    private static final ResourceLocation SKYBOX_TEXTURE = new ResourceLocation(RougePlanets.MODID, "textures/environment/skybox.png");

    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    private void renderCustomSky(PoseStack poseStack, Matrix4f p_2525534_, float p_202426_, Camera p_202427_, boolean p_202428_, Runnable p_202429_, CallbackInfo ci) {
        if (this.minecraft.level != null) {
            if (this.minecraft.level.dimension().location().getNamespace().equals("rougeplanets")) {
                renderSky(poseStack);
                ci.cancel();
            }
        }
    }

    @Unique
    private void renderSky(PoseStack poseStack) {
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, SKYBOX_TEXTURE);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();

        for (int i = 0; i < 6; ++i) {
            poseStack.pushPose();
            if (i == 1) {
                poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(90.0F).rotateY(1.57f));
            }

            if (i == 2) {
                poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(-90.0F).rotateY(-1.57f));
            }

            if (i == 3) {
                poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(180.0F).rotateY(1.57f));
            }

            if (i == 4) {
                poseStack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(90.0F));
            }

            if (i == 5) {
                poseStack.mulPose(com.mojang.math.Axis.ZP.rotationDegrees(-90.0F).rotateY(3.14f));
            }

            Matrix4f matrix4f = poseStack.last().pose();
            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            bufferBuilder.vertex(matrix4f, -100.0F, -100.0F, -100.0F).uv(0.0F, 0.0F).color(255, 255, 255, 255).endVertex();
            bufferBuilder.vertex(matrix4f, -100.0F, -100.0F, 100.0F).uv(0.0F, 1).color(255, 255, 255, 255).endVertex();
            bufferBuilder.vertex(matrix4f, 100.0F, -100.0F, 100.0F).uv(1, 1).color(255, 255, 255, 255).endVertex();
            bufferBuilder.vertex(matrix4f, 100.0F, -100.0F, -100.0F).uv(1, 0.0F).color(255, 255, 255, 255).endVertex();
            tesselator.end();
            poseStack.popPose();
        }

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
    }
}

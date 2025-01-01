package yaboichips.rouge_planets.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import yaboichips.rouge_planets.common.entities.HumanMob;

import static yaboichips.rouge_planets.RougePlanets.MODID;

public class HumanRenderer extends MobRenderer<HumanMob, HumanoidModel<HumanMob>> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MODID, "human_model"), "main");
    public HumanRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(LAYER_LOCATION)), 1f);
    }

    @Override
    public ResourceLocation getTextureLocation(HumanMob human) {
        return human.getTextureLocation();
    }
}

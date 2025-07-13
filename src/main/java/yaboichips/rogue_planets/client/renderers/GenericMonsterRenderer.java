package yaboichips.rogue_planets.client.renderers;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import yaboichips.rogue_planets.common.entities.monsters.GenericMonster;

import static yaboichips.rogue_planets.RoguePlanets.MODID;

public class GenericMonsterRenderer extends MobRenderer<GenericMonster, HumanoidModel<GenericMonster>> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MODID, "human_model"), "main");

    public GenericMonsterRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(LAYER_LOCATION)), 0.3f);
    }

    @Override
    public ResourceLocation getTextureLocation(GenericMonster human) {
        return human.getTextureLocation();
    }
}


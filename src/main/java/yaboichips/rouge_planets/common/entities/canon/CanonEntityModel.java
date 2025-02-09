package yaboichips.rouge_planets.common.entities.canon;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

import static yaboichips.rouge_planets.RougePlanets.MODID;

public class CanonEntityModel<T extends GeoAnimatable> extends GeoModel<T> {
    @Override
    public ResourceLocation getModelResource(T t) {
        return ResourceLocation.fromNamespaceAndPath(MODID, "geo/canon.geo.json");
    }
    @Override
    public ResourceLocation getTextureResource(T t) {
        return ResourceLocation.fromNamespaceAndPath(MODID, "textures/entity/canon.png");
    }
    @Override
    public ResourceLocation getAnimationResource(T t) {
        return null;
    }
}
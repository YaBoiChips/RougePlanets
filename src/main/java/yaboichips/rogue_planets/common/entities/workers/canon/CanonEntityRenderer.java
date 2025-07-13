package yaboichips.rogue_planets.common.entities.workers.canon;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CanonEntityRenderer<T extends CanonEntity> extends GeoEntityRenderer<T> {

    public CanonEntityRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CanonEntityModel<>());
    }
}

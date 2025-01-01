package yaboichips.rouge_planets.common.entities;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

public class HumanMob extends Mob {
    public HumanMob(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    public ResourceLocation getTextureLocation(){
        return null;
    }
}

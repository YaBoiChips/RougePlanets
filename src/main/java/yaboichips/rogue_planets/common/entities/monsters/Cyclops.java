package yaboichips.rogue_planets.common.entities.monsters;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import static yaboichips.rogue_planets.RoguePlanets.MODID;

public class Cyclops extends GenericMonster{

    public Cyclops(EntityType<? extends Monster> monster, Level level) {
        super(monster, level);
    }

    @Override
    public ResourceLocation getTextureLocation() {
        return ResourceLocation.fromNamespaceAndPath(MODID, "textures/entity/cyclops.png");
    }
}

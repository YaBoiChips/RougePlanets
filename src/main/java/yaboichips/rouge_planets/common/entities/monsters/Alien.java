package yaboichips.rouge_planets.common.entities.monsters;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import static yaboichips.rouge_planets.RougePlanets.MODID;

public class Alien extends GenericMonster{
    public Alien(EntityType<? extends Monster> monster, Level level) {
        super(monster, level);
    }

    @Override
    public ResourceLocation getTextureLocation() {
        return ResourceLocation.fromNamespaceAndPath(MODID, "textures/entity/alien.png");
    }
}

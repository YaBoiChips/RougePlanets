package yaboichips.rouge_planets.core;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yaboichips.rouge_planets.common.entities.forgemaster.ForgeMaster;

import static yaboichips.rouge_planets.RougePlanets.MODID;

public class RPEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<ForgeMaster>> FORGE_MASTER = ENTITIES.register("forge_master", () -> EntityType.Builder.of(ForgeMaster::new, MobCategory.MISC).sized(0.8f, 1.8f).build("forge_master"));

}

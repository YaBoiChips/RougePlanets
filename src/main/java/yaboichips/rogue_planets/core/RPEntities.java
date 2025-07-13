package yaboichips.rogue_planets.core;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yaboichips.rogue_planets.common.entities.monsters.Alien;
import yaboichips.rogue_planets.common.entities.monsters.Cyclops;
import yaboichips.rogue_planets.common.entities.workers.augmentor.Augmentor;
import yaboichips.rogue_planets.common.entities.workers.canon.CanonEntity;
import yaboichips.rogue_planets.common.entities.workers.ceo.CEO;
import yaboichips.rogue_planets.common.entities.workers.forgemaster.ForgeMaster;
import yaboichips.rogue_planets.common.entities.workers.merchant.RPMerchant;

import static yaboichips.rogue_planets.RoguePlanets.MODID;


public class RPEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<ForgeMaster>> FORGE_MASTER = ENTITIES.register("forge_master", () -> EntityType.Builder.of(ForgeMaster::new, MobCategory.MISC).sized(0.8f, 1.8f).build("forge_master"));
    public static final RegistryObject<EntityType<RPMerchant>> RP_MERCHANT = ENTITIES.register("merchant", () -> EntityType.Builder.of(RPMerchant::new, MobCategory.MISC).sized(0.8f, 1.8f).build("merchant"));
    public static final RegistryObject<EntityType<Augmentor>> AUGMENTOR = ENTITIES.register("augmentor", () -> EntityType.Builder.of(Augmentor::new, MobCategory.MISC).sized(0.8f, 1.8f).build("augmentor"));
    public static final RegistryObject<EntityType<CEO>> CEO = ENTITIES.register("ceo", () -> EntityType.Builder.of(CEO::new, MobCategory.MISC).sized(0.8f, 1.8f).build("ceo"));
    public static final RegistryObject<EntityType<Cyclops>> CYCLOPS = ENTITIES.register("cyclops", () -> EntityType.Builder.of(Cyclops::new, MobCategory.MONSTER).sized(0.8f, 1.8f).build("cyclops"));
    public static final RegistryObject<EntityType<Alien>> ALIEN = ENTITIES.register("alien", () -> EntityType.Builder.of(Alien::new, MobCategory.MONSTER).sized(0.8f, 1.8f).build("alien"));
    public static final RegistryObject<EntityType<CanonEntity>> CANON = ENTITIES.register("canon", () -> EntityType.Builder.of(CanonEntity::new, MobCategory.MISC).sized(1, 0.5f).build("canon"));

}

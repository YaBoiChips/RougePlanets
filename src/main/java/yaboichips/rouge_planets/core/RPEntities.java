package yaboichips.rouge_planets.core;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yaboichips.rouge_planets.common.entities.augmentor.Augmentor;
import yaboichips.rouge_planets.common.entities.canon.CanonEntity;
import yaboichips.rouge_planets.common.entities.ceo.CEO;
import yaboichips.rouge_planets.common.entities.forgemaster.ForgeMaster;
import yaboichips.rouge_planets.common.entities.merchant.RPMerchant;

import static yaboichips.rouge_planets.RougePlanets.MODID;

public class RPEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<ForgeMaster>> FORGE_MASTER = ENTITIES.register("forge_master", () -> EntityType.Builder.of(ForgeMaster::new, MobCategory.MISC).sized(0.8f, 1.8f).build("forge_master"));
    public static final RegistryObject<EntityType<RPMerchant>> RP_MERCHANT = ENTITIES.register("merchant", () -> EntityType.Builder.of(RPMerchant::new, MobCategory.MISC).sized(0.8f, 1.8f).build("merchant"));
    public static final RegistryObject<EntityType<Augmentor>> AUGMENTOR = ENTITIES.register("augmentor", () -> EntityType.Builder.of(Augmentor::new, MobCategory.MISC).sized(0.8f, 1.8f).build("augmentor"));
    public static final RegistryObject<EntityType<CEO>> CEO = ENTITIES.register("ceo", () -> EntityType.Builder.of(CEO::new, MobCategory.MISC).sized(0.8f, 1.8f).build("ceo"));

    public static final RegistryObject<EntityType<CanonEntity>> CANON = ENTITIES.register("canon", () -> EntityType.Builder.of(CanonEntity::new, MobCategory.MISC).sized(1, 0.5f).build("canon"));

}

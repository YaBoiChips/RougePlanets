package yaboichips.rouge_planets.core.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import yaboichips.rouge_planets.RougePlanets;
import yaboichips.rouge_planets.common.world.features.AngledArchFeature;
import yaboichips.rouge_planets.common.world.features.BeegStoneSpike;

public class RPFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, RougePlanets.MODID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> BEEG_SPIKE = FEATURES.register("beeg_spike", () -> new BeegStoneSpike(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ANGLED_ARCH = FEATURES.register("angled_arch", () -> new AngledArchFeature(NoneFeatureConfiguration.CODEC));

}

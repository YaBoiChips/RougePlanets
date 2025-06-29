package yaboichips.rouge_planets.events;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yaboichips.rouge_planets.RougePlanets;
import yaboichips.rouge_planets.common.entities.monsters.GenericMonster;
import yaboichips.rouge_planets.core.RPEntities;

@Mod.EventBusSubscriber(modid = RougePlanets.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(RPEntities.ALIEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GenericMonster::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(RPEntities.CYCLOPS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GenericMonster::checkMobSpawnRules, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}

package yaboichips.rouge_planets;

import net.minecraft.world.SimpleContainer;
import yaboichips.rouge_planets.client.PlanetInventoryContainer;

public interface PlayerData {

    PlanetInventoryContainer getPlanetContainer();
    PlanetInventoryContainer setPlanetContainer(PlanetInventoryContainer container);

}

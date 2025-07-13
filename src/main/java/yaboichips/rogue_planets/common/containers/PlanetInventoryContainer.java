package yaboichips.rogue_planets.common.containers;

import net.minecraft.world.entity.player.Inventory;

public class PlanetInventoryContainer extends SaveableSimpleContainer {
    public PlanetInventoryContainer() {
        super(36);
    }

    public PlanetInventoryContainer(Inventory inventory) {
        super(36);
        inventory.items.forEach(this::addItem);
    }
}

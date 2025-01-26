package yaboichips.rouge_planets.common.containers;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import yaboichips.rouge_planets.common.entities.HumanMob;

import java.util.Arrays;

public class PlanetInventoryContainer extends SaveableSimpleContainer {
    public PlanetInventoryContainer() {
        super(36);
    }

    public PlanetInventoryContainer(Inventory inventory) {
        super(36);
        inventory.items.forEach(this::addItem);
    }
}

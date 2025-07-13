package yaboichips.rogue_planets.common.items.augments;

import net.minecraft.world.item.Item;
import yaboichips.rogue_planets.core.RPItems;

public enum AugmentType {
    HASTE(RPItems.PLANETEER_PICKAXE.get());

    private Item item;
    AugmentType(Item item){
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}

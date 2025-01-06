package yaboichips.rouge_planets.common.items;

import net.minecraft.world.item.Item;

public class SimpleSlotableItem extends Item implements SlotableItem{
    private final int slot;
    public SimpleSlotableItem(Properties p_41383_, int slot) {
        super(p_41383_);
        this.slot = slot;
    }

    @Override
    public int getSlot() {
        return slot;
    }
}

package yaboichips.rouge_planets.common.items.augments;

import net.minecraft.world.item.Item;
import yaboichips.rouge_planets.common.items.SlotableItem;

public class AugmentItem extends Item implements SlotableItem {
    private final AugmentType type;
    public AugmentItem(AugmentType type) {
        super(new Properties());
        this.type = type;
    }

    public AugmentType getType() {
        return type;
    }

    @Override
    public int getSlot() {
        return 5;
    }
}

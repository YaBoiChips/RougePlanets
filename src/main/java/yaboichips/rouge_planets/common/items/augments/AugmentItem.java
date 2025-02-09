package yaboichips.rouge_planets.common.items.augments;

import net.minecraft.world.item.Item;

public class AugmentItem extends Item {
    private final AugmentType type;
    public AugmentItem(AugmentType type) {
        super(new Properties());
        this.type = type;
    }

    public AugmentType getType() {
        return type;
    }

}

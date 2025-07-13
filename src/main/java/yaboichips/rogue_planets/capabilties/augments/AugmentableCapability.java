package yaboichips.rogue_planets.capabilties.augments;

import net.minecraft.nbt.CompoundTag;

public class AugmentableCapability {

    private int augmentSlots;
    private AugmentContainer augments;

    public AugmentableCapability(){
        augments = new AugmentContainer(3);
    }

    public AugmentContainer getAugments() {
        return augments;
    }

    public void setAugments(AugmentContainer augments) {
        this.augments = augments;
    }

    public int getAugmentSlots() {
        return augmentSlots;
    }

    public void setAugmentSlots(int augmentSlots) {
        this.augmentSlots = augmentSlots;
    }

    public void serializeNBT(CompoundTag tag) {
        tag.put("Augments", this.getAugments().createTag());
        tag.putInt("AugmentSlots", getAugmentSlots());
    }
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("Augments")) {
            this.augments.fromTag(tag.getList("Augments", 10));
        }
        if (tag.contains("AugmentSlots")) {
            this.setAugmentSlots(tag.getInt("AugmentSlots"));
        }
    }
}

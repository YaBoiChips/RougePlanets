package yaboichips.rouge_planets.capabilties.armor;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;

public interface IPaddingCapability {
    boolean isActivated();

    void setActivated(boolean activated);

    double modifyArmorValue(double originalValue, double modifier);
    void serializeNBT(CompoundTag tag);
    void deserializeNBT(CompoundTag tag);

    class PaddingCapability implements IPaddingCapability {
        private boolean activated = false;

        @Override
        public boolean isActivated() {
            return activated;
        }

        @Override
        public void setActivated(boolean activated) {
            this.activated = activated;
        }

        @Override
        public double modifyArmorValue(double originalValue, double modifier) {
            return originalValue + (originalValue * modifier);
        }
        @Override
        public void serializeNBT(CompoundTag tag) {
            tag.putBoolean("Activated", activated);
        }

        @Override
        public void deserializeNBT(CompoundTag tag) {
            this.activated = tag.getBoolean("Activated");
        }
    }
}



package yaboichips.rogue_planets.capabilties;

import net.minecraft.nbt.CompoundTag;

public interface ArmorData {
    boolean isActivated();

    void setActivated(boolean activated);

    double modifyArmorValue(double originalValue, double modifier);
    void serializeNBT(CompoundTag tag);
    void deserializeNBT(CompoundTag tag);

    class ArmorCapability implements ArmorData {
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



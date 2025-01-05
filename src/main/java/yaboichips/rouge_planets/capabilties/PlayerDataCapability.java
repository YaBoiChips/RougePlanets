package yaboichips.rouge_planets.capabilties;

import net.minecraft.nbt.CompoundTag;
import yaboichips.rouge_planets.client.PlanetInventoryContainer;

public class PlayerDataCapability implements PlayerData {
    public boolean isInitiated;
    public int credits;
    public int o2;
    public PlanetInventoryContainer planetInventory;

    public PlayerDataCapability() {
        this.planetInventory = new PlanetInventoryContainer();
    }

    @Override
    public PlanetInventoryContainer getPlanetContainer() {
        return this.planetInventory;
    }

    @Override
    public void setPlanetContainer(PlanetInventoryContainer container) {
        this.planetInventory = container;
    }

    @Override
    public boolean getIsInitiated() {
        return this.isInitiated;
    }

    @Override
    public void setIsInitiated(boolean isInitiated) {
        this.isInitiated = isInitiated;
    }

    @Override
    public int getPlaneteerXP() {
        return 0;
    }

    @Override
    public void setPlaneteerXP() {
    }

    @Override
    public int getPlaneteerLevel() {
        return 0;
    }

    @Override
    public void setPlaneteerLevel(int level) {

    }

    @Override
    public int getCredits() {
        return credits;
    }

    @Override
    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public void addCredits(int credits) {
        this.credits += credits;
    }

    @Override
    public void subtractCredits(int credits) {
        this.credits -= credits;
    }

    @Override
    public int getO2() {
        return o2;
    }

    @Override
    public void setO2(int o2) {
        this.o2 = o2;
    }

    @Override
    public void addO2(int o2) {
        this.o2 += o2;
    }

    @Override
    public void subO2(int o2) {
        this.o2 -= o2;
    }
    public void serializeNBT(CompoundTag tag) {
        tag.put("PlanetInventory", this.planetInventory.createTag());
        tag.putBoolean("Initiated", getIsInitiated());
        tag.putInt("Credits", getCredits());
        tag.putInt("O2", getCredits());

    }

    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("PlanetInventory")) {
            this.planetInventory.fromTag(tag.getList("PlanetInventory", 10));
        }
        if (tag.contains("Initiated")) {
            this.setIsInitiated(tag.getBoolean("Initiated"));
        }
        if (tag.contains("Credits")) {
            this.setCredits(tag.getInt("Credits"));
        }
        if (tag.contains("O2")) {
            this.setCredits(tag.getInt("O2"));
        }
    }
}

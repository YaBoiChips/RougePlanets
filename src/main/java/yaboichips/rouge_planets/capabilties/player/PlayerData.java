package yaboichips.rouge_planets.capabilties.player;

import net.minecraft.nbt.CompoundTag;
import yaboichips.rouge_planets.client.PlanetInventoryContainer;

public class PlayerData {
    public boolean isInitiated;
    public int credits;
    public int o2;

    private int pyrolithTimer;
    private int azuriumTimer;
    private int electryteTimer;
    private int chlorosynthTimer;



    public PlanetInventoryContainer planetInventory;

    public PlayerData() {
        this.planetInventory = new PlanetInventoryContainer();
    }


    public PlanetInventoryContainer getPlanetContainer() {
        return this.planetInventory;
    }


    public void setPlanetContainer(PlanetInventoryContainer container) {
        this.planetInventory = container;
    }


    public boolean getIsInitiated() {
        return this.isInitiated;
    }


    public void setIsInitiated(boolean isInitiated) {
        this.isInitiated = isInitiated;
    }


    public int getPlaneteerXP() {
        return 0;
    }


    public void setPlaneteerXP() {
    }


    public int getPlaneteerLevel() {
        return 0;
    }


    public void setPlaneteerLevel(int level) {

    }


    public int getCredits() {
        return credits;
    }


    public void setCredits(int credits) {
        this.credits = credits;
    }


    public void addCredits(int credits) {
        this.credits += credits;
    }


    public void subtractCredits(int credits) {
        this.credits -= credits;
    }


    public int getO2() {
        return o2;
    }


    public void setO2(int o2) {
        this.o2 = o2;
    }


    public void addO2(int o2) {
        this.o2 += o2;
    }


    public void subO2(int o2) {
        this.o2 -= o2;
    }


    public void serializeNBT(CompoundTag tag) {
        tag.put("PlanetInventory", this.planetInventory.createTag());
        tag.putBoolean("Initiated", getIsInitiated());
        tag.putInt("Credits", getCredits());
        tag.putInt("O2", getCredits());
        tag.putInt("PyrolithTimer", getPyrolithTimer());
        tag.putInt("AzuriumTimer", getAzuriumTimer());
        tag.putInt("ElectryteTimer", getElectryteTimer());
        tag.putInt("ChlorosynthTimer", getChlorosynthTimer());
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
            this.setO2(tag.getInt("O2"));
        }
        if (tag.contains("PyrolithTimer")) {
            this.setPyrolithTimer(tag.getInt("PyrolithTimer"));
        }
        if (tag.contains("ChlorosynthTimer")) {
            this.setChlorosynthTimer(tag.getInt("ChlorosynthTimer"));
        }
        if (tag.contains("AzuriumTimer")) {
            this.setAzuriumTimer(tag.getInt("AzuriumTimer"));
        }
        if (tag.contains("ElectryteTimer")) {
            this.setElectryteTimer(tag.getInt("ElectryteTimer"));
        }
    }

    public boolean isPyrolithActive() {
        return getPyrolithTimer() > 0;
    }

    public boolean isAzuriumActive() {
        return getAzuriumTimer() > 0;
    }

    public boolean isElectryteActive() {
        return getElectryteTimer() > 0;
    }

    public boolean isChlorosynthActive() {
        return getChlorosynthTimer() > 1;
    }

    public int getPyrolithTimer() {
        return pyrolithTimer;
    }

    public void setPyrolithTimer(int pyrolithTimer) {
        this.pyrolithTimer = pyrolithTimer;
    }

    public int getAzuriumTimer() {
        return azuriumTimer;
    }

    public void setAzuriumTimer(int azuriumTimer) {
        this.azuriumTimer = azuriumTimer;
    }

    public int getElectryteTimer() {
        return electryteTimer;
    }

    public void setElectryteTimer(int electryteTimer) {
        this.electryteTimer = electryteTimer;
    }

    public int getChlorosynthTimer() {
        return chlorosynthTimer;
    }

    public void setChlorosynthTimer(int chlorosynthTimer) {
        this.chlorosynthTimer = chlorosynthTimer;
    }
}

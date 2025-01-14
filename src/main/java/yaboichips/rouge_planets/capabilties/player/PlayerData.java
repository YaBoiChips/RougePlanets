package yaboichips.rouge_planets.capabilties.player;

import net.minecraft.nbt.CompoundTag;
import yaboichips.rouge_planets.client.PlanetInventoryContainer;

public class PlayerData {
    public boolean isInitiated;
    public int credits;
    public int o2;

    private boolean pyrolithActive;
    private boolean azuriumActive;
    private boolean electryteActive;
    private boolean chlorosynthActive;

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
        tag.putBoolean("Pyrolith", isPyrolithActive());
        tag.putBoolean("Azurium", isAzuriumActive());
        tag.putBoolean("Electryte", isElectryteActive());
        tag.putBoolean("Chlorosynth", isChlorosynthActive());

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
        if (tag.contains("Pyrolith")) {
            this.setPyrolithActive(tag.getBoolean("Pyrolith"));
        }
        if (tag.contains("Azurium")) {
            this.setAzuriumActive(tag.getBoolean("Azurium"));
        }
        if (tag.contains("Electryte")) {
            this.setElectryteActive(tag.getBoolean("Electryte"));
        }
        if (tag.contains("Chlorosynth")) {
            this.setChlorosynthActive(tag.getBoolean("Chlorosynth"));
        }
    }

    public boolean isPyrolithActive() {
        return pyrolithActive;
    }

    public void setPyrolithActive(boolean pyrolithActive) {
        this.pyrolithActive = pyrolithActive;
    }

    public boolean isAzuriumActive() {
        return azuriumActive;
    }

    public void setAzuriumActive(boolean azuriumActive) {
        this.azuriumActive = azuriumActive;
    }

    public boolean isElectryteActive() {
        return electryteActive;
    }

    public void setElectryteActive(boolean electryteActive) {
        this.electryteActive = electryteActive;
    }

    public boolean isChlorosynthActive() {
        return chlorosynthActive;
    }

    public void setChlorosynthActive(boolean chlorosynthActive) {
        this.chlorosynthActive = chlorosynthActive;
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

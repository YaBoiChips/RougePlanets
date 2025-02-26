package yaboichips.rouge_planets.capabilties.player;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import yaboichips.rouge_planets.common.containers.PlanetArmorContainer;
import yaboichips.rouge_planets.common.containers.PlanetInventoryContainer;
import yaboichips.rouge_planets.common.containers.SaveableSimpleContainer;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {
    public boolean isInitiated;
    public int credits;
    public int o2;

    private int pyrolithTimer;
    private int azuriumTimer;
    private int electryteTimer;
    private int chlorosynthTimer;

    public PlanetInventoryContainer planetInventory;
    private PlanetArmorContainer armorContainer;
    private SaveableSimpleContainer playerInventory;


    public PlayerData() {
        this.planetInventory = new PlanetInventoryContainer();
        this.armorContainer = new PlanetArmorContainer();
        this.playerInventory = new SaveableSimpleContainer(36);
    }


    public PlanetInventoryContainer getPlanetContainer() {
        return this.planetInventory;
    }

    public PlanetArmorContainer getArmorContainer() {
        return armorContainer;
    }

    public void setArmorContainer(PlanetArmorContainer armorContainer) {
        this.armorContainer = armorContainer;
    }


    public void setPlanetContainer(PlanetInventoryContainer container) {
        this.planetInventory = container;
    }

    public SaveableSimpleContainer getSavedInventory() {
        return playerInventory;
    }

    public void setSavedInventory(SaveableSimpleContainer playerInventory) {
        this.playerInventory = playerInventory;
    }
    public boolean getIsInitiated() {
        return this.isInitiated;
    }

    public void setIsInitiated(boolean isInitiated) {
        this.isInitiated = isInitiated;
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
        tag.put("ArmorPlanetInventory", this.armorContainer.createTag());
        tag.put("SavedPlayerInventory", createTag(playerInventory));
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
        if (tag.contains("ArmorPlanetInventory")) {
            this.armorContainer.fromTag(tag.getList("ArmorPlanetInventory", 10));
        }
        if (tag.contains("SavedPlayerInventory")) {
            fromTag(tag.getList("SavedPlayerInventory", 10), this.playerInventory);
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

    public void fromTag(ListTag p_40108_, Container container) {
        for (int i = 0; i < container.getContainerSize(); ++i) {
            container.setItem(i, ItemStack.EMPTY);
        }

        for (int k = 0; k < p_40108_.size(); ++k) {
            CompoundTag compoundtag = p_40108_.getCompound(k);
            int j = compoundtag.getByte("Slot") & 255;
            if (j >= 0 && j < container.getContainerSize()) {
                container.setItem(j, ItemStack.of(compoundtag));
            }
        }
    }

    public ListTag createTag(Container container) {
        ListTag listtag = new ListTag();

        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemstack = container.getItem(i);
            if (!itemstack.isEmpty()) {
                CompoundTag compoundtag = new CompoundTag();
                compoundtag.putByte("Slot", (byte) i);
                itemstack.save(compoundtag);
                listtag.add(compoundtag);
            }
        }

        return listtag;
    }

}

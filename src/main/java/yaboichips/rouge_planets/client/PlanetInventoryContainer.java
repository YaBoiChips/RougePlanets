package yaboichips.rouge_planets.client;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import yaboichips.rouge_planets.common.entities.HumanMob;

import java.util.Arrays;

public class PlanetInventoryContainer extends SimpleContainer {
    private HumanMob human;

    public PlanetInventoryContainer() {
        super(13);
    }

    public void setActiveEntity(HumanMob human) {
        this.human = human;
    }

    public boolean isActiveEntity(HumanMob human) {
        return this.human == human;
    }

    public void addItems(ItemStack... items){
        Arrays.stream(items).toList().forEach(this::addItem);
    }
    public void fromTag(ListTag p_40108_) {
        for(int i = 0; i < this.getContainerSize(); ++i) {
            this.setItem(i, ItemStack.EMPTY);
        }

        for(int k = 0; k < p_40108_.size(); ++k) {
            CompoundTag compoundtag = p_40108_.getCompound(k);
            int j = compoundtag.getByte("Slot") & 255;
            if (j >= 0 && j < this.getContainerSize()) {
                this.setItem(j, ItemStack.of(compoundtag));
            }
        }

    }

    public ListTag createTag() {
        ListTag listtag = new ListTag();

        for(int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack itemstack = this.getItem(i);
            if (!itemstack.isEmpty()) {
                CompoundTag compoundtag = new CompoundTag();
                compoundtag.putByte("Slot", (byte)i);
                itemstack.save(compoundtag);
                listtag.add(compoundtag);
            }
        }

        return listtag;
    }
}

package yaboichips.rogue_planets.common.containers;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public class SaveableSimpleContainer extends SimpleContainer {
    public SaveableSimpleContainer(int slots) {
        super(slots);
    }

    public SaveableSimpleContainer(Inventory inventory) {
        super(inventory.getContainerSize());
        inventory.items.forEach(this::addItem);
    }
    public void addItems(ItemStack... items) {
        Arrays.stream(items).toList().forEach(this::addItem);
    }

    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    public void fromTag(ListTag p_40108_) {
        for (int i = 0; i < this.getContainerSize(); ++i) {
            this.setItem(i, ItemStack.EMPTY);
        }
        for (int k = 0; k < p_40108_.size(); ++k) {
            CompoundTag compoundtag = p_40108_.getCompound(k);
            int j = compoundtag.getByte("Slot") & 255;
            if (j >= 0 && j < this.getContainerSize()) {
                this.setItem(j, ItemStack.of(compoundtag));
            }
        }
    }

    public ListTag createTag() {
        ListTag listtag = new ListTag();
        for (int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack itemstack = this.getItem(i);
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

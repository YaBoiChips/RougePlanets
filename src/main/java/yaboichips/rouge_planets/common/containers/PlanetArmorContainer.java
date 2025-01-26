package yaboichips.rouge_planets.common.containers;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import yaboichips.rouge_planets.common.entities.HumanMob;

import java.util.Arrays;

public class PlanetArmorContainer extends SaveableSimpleContainer {
    public PlanetArmorContainer() {
        super(4);
    }

}

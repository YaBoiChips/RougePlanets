package yaboichips.rogue_planets.capabilties.augments;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import yaboichips.rogue_planets.capabilties.RogueCapabilities;

public class AugCapProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    private AugmentableCapability instance = null;
    private final LazyOptional<AugmentableCapability> lazyInstance =
            LazyOptional.of(this::createInstance);

    private AugmentableCapability createInstance() {
        if (this.instance == null) {
            this.instance = new AugmentableCapability();
        }
        return this.instance;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
        return capability == RogueCapabilities.AUGMENTABLE ?
                lazyInstance.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        createInstance().serializeNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        createInstance().deserializeNBT(tag);
    }
}

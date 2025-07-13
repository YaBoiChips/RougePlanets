package yaboichips.rogue_planets.capabilties.player;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import yaboichips.rogue_planets.capabilties.RogueCapabilities;

public class PlayerDataProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private PlayerData instance = null;
    private final LazyOptional<PlayerData> lazyInstance =
            LazyOptional.of(this::createInstance);

    private PlayerData createInstance() {
        if(this.instance == null) {
            this.instance = new PlayerData();
        }
        return this.instance;
    }


    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
        return capability == RogueCapabilities.PLAYER_DATA ?
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

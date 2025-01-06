package yaboichips.rouge_planets.capabilties.handlers;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import yaboichips.rouge_planets.capabilties.PlayerData;
import yaboichips.rouge_planets.capabilties.PlayerDataCapability;
import yaboichips.rouge_planets.capabilties.RougeCapabilities;

public class CapabilityHandler {


    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof ServerPlayer) {

            ICapabilitySerializable<CompoundTag> provider = new ICapabilitySerializable<>() {

                private final PlayerDataCapability instance = new PlayerDataCapability();
                private final LazyOptional<PlayerData> lazyInstance =
                        LazyOptional.of(PlayerDataCapability::new);

                @Override
                public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction side) {
                    return capability == RougeCapabilities.PLAYER_DATA ?
                            lazyInstance.cast() : LazyOptional.empty();
                }

                @Override
                public CompoundTag serializeNBT() {
                    return instance.serializeNBT();
                }


                @Override
                public void deserializeNBT(CompoundTag tag) {
                    instance.deserializeNBT(tag);
                }
            };

            event.addCapability(RougeCapabilities.PLAYER_DATA_LOCATION, provider);
        }
    }
}
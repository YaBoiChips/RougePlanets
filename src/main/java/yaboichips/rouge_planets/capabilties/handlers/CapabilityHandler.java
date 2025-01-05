package yaboichips.rouge_planets.capabilties.handlers;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import yaboichips.rouge_planets.capabilties.PlayerData;
import yaboichips.rouge_planets.capabilties.PlayerDataCapability;
import yaboichips.rouge_planets.capabilties.RougeCapabilities;
import yaboichips.rouge_planets.client.PlanetInventoryContainer;

import java.util.Objects;

public class CapabilityHandler {


    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            event.addCapability(RougeCapabilities.PLAYER_DATA_LOCATION, new ICapabilitySerializable<CompoundTag>() {

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
                    CompoundTag tag = new CompoundTag();
                    tag.put("PlanetInventory", Objects.requireNonNullElseGet(instance.planetInventory, PlanetInventoryContainer::new).createTag());
                    tag.putBoolean("Initiated", instance.isInitiated);
                    tag.putInt("Credits", instance.credits);
                    tag.putInt("O2", instance.o2);
                    return tag;
                }

                @Override
                public void deserializeNBT(CompoundTag tag) {
                    if (tag.contains("PlanetInventory")) {
                        if (instance.getPlanetContainer() != null) {
                            instance.getPlanetContainer().fromTag(tag.getList("PlanetInventory", 10));
                        } else {
                            instance.setPlanetContainer(new PlanetInventoryContainer());
                        }
                    }
                    if (tag.contains("Initiated")) {
                        instance.setIsInitiated(tag.getBoolean("Initiated"));
                    }
                    if (tag.contains("Credits")) {
                        instance.setCredits(tag.getInt("Credits"));
                    }
                    if (tag.contains("O2")) {
                        instance.setCredits(tag.getInt("O2"));
                    }
                }
            });
        }
    }
}

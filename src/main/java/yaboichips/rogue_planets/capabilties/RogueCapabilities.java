package yaboichips.rogue_planets.capabilties;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import yaboichips.rogue_planets.capabilties.augments.AugmentableCapability;
import yaboichips.rogue_planets.capabilties.player.PlayerData;

import static yaboichips.rogue_planets.RoguePlanets.MODID;


public class RogueCapabilities {
    public static final Capability<ArmorData.ArmorCapability> PADDING = CapabilityManager.get(new CapabilityToken<>() {});

    public static final Capability<PlayerData> PLAYER_DATA = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<AugmentableCapability> AUGMENTABLE = CapabilityManager.get(new CapabilityToken<>() {});

    public static final ResourceLocation PLAYER_DATA_LOCATION = ResourceLocation.fromNamespaceAndPath(MODID, "player_data");
    public static final ResourceLocation AUGMENTABLE_LOCATION = ResourceLocation.fromNamespaceAndPath(MODID, "augmentable");

}

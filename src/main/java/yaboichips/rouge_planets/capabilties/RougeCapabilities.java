package yaboichips.rouge_planets.capabilties;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import yaboichips.rouge_planets.capabilties.player.PlayerData;

import static yaboichips.rouge_planets.RougePlanets.MODID;

public class RougeCapabilities {
    public static final Capability<ArmorData.ArmorCapability> PADDING = CapabilityManager.get(new CapabilityToken<>() {});

    public static final Capability<PlayerData> PLAYER_DATA = CapabilityManager.get(new CapabilityToken<>() {});

    public static final ResourceLocation PLAYER_DATA_LOCATION = ResourceLocation.fromNamespaceAndPath(MODID, "player_data");

}

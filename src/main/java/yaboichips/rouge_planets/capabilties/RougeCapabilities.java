package yaboichips.rouge_planets.capabilties;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import yaboichips.rouge_planets.capabilties.armor.IPaddingCapability;

public class RougeCapabilities {
    public static final Capability<IPaddingCapability.PaddingCapability> PADDING =
            CapabilityManager.get(new CapabilityToken<>() {});
}

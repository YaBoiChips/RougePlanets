package yaboichips.rouge_planets.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import yaboichips.rouge_planets.common.entities.forgemaster.ForgeMasterMenu;

import java.util.function.Supplier;

public class LevelUpItemPacket {
    public LevelUpItemPacket() {
        // No additional data needed for this packet
    }

    // Encode data to the buffer
    public void encode(FriendlyByteBuf buf) {
        // No data to write
    }

    // Decode data from the buffer
    public static LevelUpItemPacket decode(FriendlyByteBuf buf) {
        return new LevelUpItemPacket();
    }

    // Handle the packet on the server
    public static void handle(LevelUpItemPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // Ensure we are on the server side
            if (context.getSender() != null) {
                // Get the player and their open menu
                if (context.getSender().containerMenu instanceof ForgeMasterMenu menu) {
                    menu.levelUpItem();
                }
            }
        });
        context.setPacketHandled(true);
    }
}

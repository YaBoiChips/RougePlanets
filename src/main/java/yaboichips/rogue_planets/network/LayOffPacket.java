package yaboichips.rogue_planets.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import yaboichips.rogue_planets.common.entities.workers.ceo.CEOMenu;

import java.util.function.Supplier;

public class LayOffPacket {
    public LayOffPacket() {
        // No additional data needed for this packet
    }

    // Encode data to the buffer
    public void encode(FriendlyByteBuf buf) {
        // No data to write
    }

    // Decode data from the buffer
    public static LayOffPacket decode(FriendlyByteBuf buf) {
        return new LayOffPacket();
    }

    // Handle the packet on the server
    public static void handle(LayOffPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // Ensure we are on the server side
            if (context.getSender() != null) {
                // Get the player and their open menu
                if (context.getSender().containerMenu instanceof CEOMenu menu) {
                    menu.layOff();
                }
            }
        });
        context.setPacketHandled(true);
    }
}

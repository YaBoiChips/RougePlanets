package yaboichips.rouge_planets.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import yaboichips.rouge_planets.common.entities.workers.augmentor.AugmentorMenu;

import java.util.function.Supplier;

public class SendItemsToSlotPacket {
    public SendItemsToSlotPacket() {
        // No additional data needed for this packet
    }

    // Encode data to the buffer
    public void encode(FriendlyByteBuf buf) {
        // No data to write
    }

    // Decode data from the buffer
    public static SendItemsToSlotPacket decode(FriendlyByteBuf buf) {
        return new SendItemsToSlotPacket();
    }

    // Handle the packet on the server
    public static void handle(SendItemsToSlotPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // Ensure we are on the server side
            if (context.getSender() != null) {
                // Get the player and their open menu
                if (context.getSender().containerMenu instanceof AugmentorMenu menu) {
                    menu.onClose();
                }
            }
        });
        context.setPacketHandled(true);
    }
}

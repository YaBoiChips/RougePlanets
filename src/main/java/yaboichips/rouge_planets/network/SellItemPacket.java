package yaboichips.rouge_planets.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import yaboichips.rouge_planets.common.entities.workers.merchant.RPMerchantMenu;

import java.util.function.Supplier;

public class SellItemPacket {
    public SellItemPacket() {
    }

    // Encode data to the buffer
    public static void encode(SellItemPacket packet, FriendlyByteBuf buf) {
    }

    // Decode data from the buffer
    public static SellItemPacket decode(FriendlyByteBuf buf) {
        return new SellItemPacket();
    }

    // Handle the packet on the server
    public static void handle(SellItemPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (context.getSender() != null) {
                if (context.getSender().containerMenu instanceof RPMerchantMenu menu) {
                    menu.buyFromPlayer();
                }
            }
        });
        context.setPacketHandled(true);
    }
}

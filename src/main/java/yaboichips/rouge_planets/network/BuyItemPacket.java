package yaboichips.rouge_planets.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import yaboichips.rouge_planets.common.entities.workers.merchant.RPMerchantMenu;

import java.util.function.Supplier;

public class BuyItemPacket {
    private final ItemStack itemStack;
    private final int value;
    public BuyItemPacket(ItemStack itemStack, int value) {
        this.itemStack = itemStack;
        this.value = value;
    }

    // Encode data to the buffer
    public static void encode(BuyItemPacket packet, FriendlyByteBuf buf) {
        buf.writeItem(packet.itemStack);
        buf.writeInt(packet.value);
    }

    // Decode data from the buffer
    public static BuyItemPacket decode(FriendlyByteBuf buf) {
        ItemStack itemStack = buf.readItem();
        int value = buf.readInt();
        return new BuyItemPacket(itemStack, value);
    }

    // Handle the packet on the server
    public static void handle(BuyItemPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // Ensure we are on the server side
            if (context.getSender() != null) {
                // Get the player and their open menu
                if (context.getSender().containerMenu instanceof RPMerchantMenu menu) {
                    ItemStack receivedStack = packet.itemStack;
                    int price = packet.value;
                    menu.purchaseItem(receivedStack, price);
                }
            }
        });
        context.setPacketHandled(true);
    }
}

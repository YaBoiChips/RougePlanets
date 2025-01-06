package yaboichips.rouge_planets.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import yaboichips.rouge_planets.RougePlanets;
import yaboichips.rouge_planets.capabilties.player.ClientPlayerData;

import java.util.function.Supplier;

public class SendPlayerTimePacket {
    private final int o2;

    public SendPlayerTimePacket(int o2) {
        this.o2 = o2;
    }

    public static void encode(SendPlayerTimePacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.o2);
    }

    public static SendPlayerTimePacket decode(FriendlyByteBuf buf) {
        return new SendPlayerTimePacket(buf.readInt());
    }

    public static void handle(SendPlayerTimePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> ClientPlayerData.setO2(msg.o2));
        ctx.get().setPacketHandled(true);
    }
}

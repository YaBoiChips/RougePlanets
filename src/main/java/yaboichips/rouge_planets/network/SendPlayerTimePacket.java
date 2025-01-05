package yaboichips.rouge_planets.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import yaboichips.rouge_planets.RougePlanets;

import java.util.function.Supplier;

public class SendPlayerTimePacket {
    private final int time;

    public SendPlayerTimePacket(int time) {
        this.time = time;
    }

    public static void encode(SendPlayerTimePacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.time);
    }

    public static SendPlayerTimePacket decode(FriendlyByteBuf buf) {
        return new SendPlayerTimePacket(buf.readInt());
    }

    public static void handle(SendPlayerTimePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> RougePlanets.setTime(msg.time));
        ctx.get().setPacketHandled(true);
    }
}

package yaboichips.rogue_planets.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import yaboichips.rogue_planets.capabilties.player.ClientPlayerData;

import java.util.function.Supplier;

public class SendPlayerDataPacket {
    private final int o2;
    private final int credits;


    public SendPlayerDataPacket(int o2, int credits) {
        this.o2 = o2;
        this.credits = credits;
    }

    public static void encode(SendPlayerDataPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.o2);
        buf.writeInt(msg.credits);
    }

    public static SendPlayerDataPacket decode(FriendlyByteBuf buf) {
        return new SendPlayerDataPacket(buf.readInt(), buf.readInt());
    }

    public static void handle(SendPlayerDataPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
                    ClientPlayerData.setO2(msg.o2);
                    ClientPlayerData.setCredits(msg.credits);
                }
        );
        ctx.get().setPacketHandled(true);
    }
}

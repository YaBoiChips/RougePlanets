package yaboichips.rouge_planets.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import yaboichips.rouge_planets.client.screens.PlaneteerInfoScreen;

import java.util.function.Supplier;

public class OpenPlaneteerGUIPacket {
    private final int credits;
//    private final int xp;
//    private final int level;
    public OpenPlaneteerGUIPacket(int credits) {
        this.credits = credits;
//        this.xp = xp;
//        this.level = level;
    }

    // Encode packet data to buffer
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(credits);
//        buf.writeInt(xp);
//        buf.writeInt(level);
    }

    // Decode packet data from buffer
    public static OpenPlaneteerGUIPacket decode(FriendlyByteBuf buf) {
        return new OpenPlaneteerGUIPacket(buf.readInt());
    }

    // Handle the packet
    public static void handle(OpenPlaneteerGUIPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            // This code runs on the client side
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            if (player != null) {
                // Open the GUI
                mc.setScreen(new PlaneteerInfoScreen(packet.credits));
            }
        });
        context.setPacketHandled(true);
    }
}

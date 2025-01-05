package yaboichips.rouge_planets.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

import static yaboichips.rouge_planets.capabilties.RougeCapabilities.PLAYER_DATA;
import static yaboichips.rouge_planets.network.RougePackets.CHANNEL;

public class SyncPlayerDataPacket {
    private final CompoundTag data;

    public SyncPlayerDataPacket(CompoundTag data) {
        this.data = data;
    }

    public static void encode(SyncPlayerDataPacket packet, FriendlyByteBuf buf) {
        buf.writeNbt(packet.data);
    }

    public static SyncPlayerDataPacket decode(FriendlyByteBuf buf) {
        return new SyncPlayerDataPacket(buf.readNbt());
    }

    public static void handle(SyncPlayerDataPacket packet, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            Player player = context.get().getSender();
            if (player != null) {
                player.getCapability(PLAYER_DATA).ifPresent(data -> data.deserializeNBT(packet.data));
            }
        });
        context.get().setPacketHandled(true);
    }
}

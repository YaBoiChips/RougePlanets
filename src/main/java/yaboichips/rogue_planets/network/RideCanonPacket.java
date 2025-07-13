package yaboichips.rogue_planets.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;
import yaboichips.rogue_planets.common.blocks.canoncontroller.CanonControllerBE;
import yaboichips.rogue_planets.common.entities.workers.canon.CanonEntity;

import java.util.function.Supplier;

public class RideCanonPacket {
    private final BlockPos blockEntityPos;

    public RideCanonPacket(BlockPos pos) {
        this.blockEntityPos = pos;
    }

    public static void encode(RideCanonPacket packet, FriendlyByteBuf buf) {
        buf.writeBlockPos(packet.blockEntityPos);
    }

    // Decode data from the buffer
    public static RideCanonPacket decode(FriendlyByteBuf buf) {
        return new RideCanonPacket(buf.readBlockPos());
    }

    public static void handle(RideCanonPacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();

        if (player != null) {
            BlockEntity blockEntity = player.level().getBlockEntity(packet.blockEntityPos);
            if (blockEntity instanceof CanonControllerBE canonController) {
                CanonEntity canon = canonController.getLinkedCanon();
                if (canon != null) {
                    player.startRiding(canon);
                }
            }
        }
        context.setPacketHandled(true);
    }
}

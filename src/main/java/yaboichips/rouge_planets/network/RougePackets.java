package yaboichips.rouge_planets.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import static yaboichips.rouge_planets.RougePlanets.MODID;

public class RougePackets {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void registerPackets() {
        int packetId = 0;

        // Register the LevelUpItemPacket
        CHANNEL.registerMessage(
                packetId++,
                LevelUpItemPacket.class,
                LevelUpItemPacket::encode,
                LevelUpItemPacket::decode,
                LevelUpItemPacket::handle
        );
        CHANNEL.registerMessage(
                packetId++,
                OpenPlaneteerGUIPacket.class,
                OpenPlaneteerGUIPacket::encode,
                OpenPlaneteerGUIPacket::decode,
                OpenPlaneteerGUIPacket::handle
        );
        CHANNEL.registerMessage(
                packetId++,
                SendPlayerDataPacket.class,
                SendPlayerDataPacket::encode,
                SendPlayerDataPacket::decode,
                SendPlayerDataPacket::handle
        );
        CHANNEL.registerMessage(
                packetId++,
                BuyItemPacket.class,
                BuyItemPacket::encode,
                BuyItemPacket::decode,
                BuyItemPacket::handle
        );
        CHANNEL.registerMessage(
                packetId++,
                ApplyAugmentPacket.class,
                ApplyAugmentPacket::encode,
                ApplyAugmentPacket::decode,
                ApplyAugmentPacket::handle
        );
        CHANNEL.registerMessage(
                packetId++,
                SendItemsToSlotPacket.class,
                SendItemsToSlotPacket::encode,
                SendItemsToSlotPacket::decode,
                SendItemsToSlotPacket::handle
        );
        CHANNEL.registerMessage(
                packetId++,
                RideCanonPacket.class,
                RideCanonPacket::encode,
                RideCanonPacket::decode,
                RideCanonPacket::handle
        );
        CHANNEL.registerMessage(
                packetId++,
                LayOffPacket.class,
                LayOffPacket::encode,
                LayOffPacket::decode,
                LayOffPacket::handle
        );
    }
    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
    public static <MSG> void sendToServer(MSG message) {
        CHANNEL.sendToServer(message);
    }
    public static <MSG> void sendToAll(MSG packet) {
        CHANNEL.send(PacketDistributor.ALL.noArg(), packet);
    }
}

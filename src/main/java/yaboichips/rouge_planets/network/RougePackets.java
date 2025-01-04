package yaboichips.rouge_planets.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
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
    }
}
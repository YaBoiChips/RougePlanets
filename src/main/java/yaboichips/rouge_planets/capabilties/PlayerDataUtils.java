package yaboichips.rouge_planets.capabilties;

import net.minecraft.server.level.ServerPlayer;
import yaboichips.rouge_planets.client.PlanetInventoryContainer;

public class PlayerDataUtils {
    public static void setInitiated(ServerPlayer player, boolean value) {
        player.getCapability(RougeCapabilities.PLAYER_DATA).ifPresent(cap -> {
            cap.setIsInitiated(value);
        });
    }

    public static boolean getIsInitiated(ServerPlayer player) {
        return player.getCapability(RougeCapabilities.PLAYER_DATA).map(PlayerData::getIsInitiated).orElse(false);
    }

    public static void setCredits(ServerPlayer player, int value) {
        player.getCapability(RougeCapabilities.PLAYER_DATA).ifPresent(cap -> {
            cap.setCredits(value);
        });
    }

    public static int getCredits(ServerPlayer player) {
        return player.getCapability(RougeCapabilities.PLAYER_DATA).map(PlayerData::getCredits).orElse(0);
    }

    public static void addCredits(ServerPlayer player, int value) {
        player.getCapability(RougeCapabilities.PLAYER_DATA).ifPresent(cap -> {
            cap.setCredits(cap.getCredits() + value);
        });
    }

    public static void subCredits(ServerPlayer player, int value) {
        player.getCapability(RougeCapabilities.PLAYER_DATA).ifPresent(cap -> {
            cap.setCredits(cap.getCredits() - value);
        });
    }

    public static void setO2(ServerPlayer player, int value) {
        player.getCapability(RougeCapabilities.PLAYER_DATA).ifPresent(cap -> {
            cap.setO2(value);
        });
    }

    public static int getO2(ServerPlayer player) {
        return player.getCapability(RougeCapabilities.PLAYER_DATA).map(PlayerData::getO2).orElse(0);
    }
    public static void addO2(ServerPlayer player, int value) {
        player.getCapability(RougeCapabilities.PLAYER_DATA).ifPresent(cap -> {
            cap.setO2(cap.getO2() + value);
        });
    }

    public static void subO2(ServerPlayer player, int value) {
        player.getCapability(RougeCapabilities.PLAYER_DATA).ifPresent(cap -> {
            cap.setO2(cap.getO2() - value);
        });
    }

    public static void setPlanetContainer(ServerPlayer player, PlanetInventoryContainer value) {
        player.getCapability(RougeCapabilities.PLAYER_DATA).ifPresent(cap -> {
            cap.setPlanetContainer(value);
        });
    }

    public static PlanetInventoryContainer getPlanetContainer(ServerPlayer player) {
        return player.getCapability(RougeCapabilities.PLAYER_DATA).map(PlayerData::getPlanetContainer).orElse(null);
    }
}

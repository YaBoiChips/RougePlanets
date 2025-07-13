package yaboichips.rogue_planets.capabilties.player;

public class ClientPlayerData {
    private static boolean isInitiated;
    private static int credits;
    private static int o2;


    public static int getO2() {
        return o2;
    }

    public static void setO2(int o2) {
        ClientPlayerData.o2 = o2;
    }

    public static int getCredits() {
        return credits;
    }

    public static void setCredits(int credits) {
        ClientPlayerData.credits = credits;
    }

    public static boolean isIsInitiated() {
        return isInitiated;
    }

    public static void setIsInitiated(boolean isInitiated) {
        ClientPlayerData.isInitiated = isInitiated;
    }
}

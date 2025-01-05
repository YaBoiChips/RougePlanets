package yaboichips.rouge_planets.capabilties;

import yaboichips.rouge_planets.client.PlanetInventoryContainer;

public interface PlayerData {


    PlanetInventoryContainer getPlanetContainer();
    void setPlanetContainer(PlanetInventoryContainer container);

    boolean getIsInitiated();
    void setIsInitiated(boolean isInitiated);

    int getPlaneteerXP();
    void setPlaneteerXP();

    int getPlaneteerLevel();
    void setPlaneteerLevel(int level);

    int getCredits();
    void setCredits(int credits);

    void addCredits(int credits);
    void subtractCredits(int coins);

    int getO2();
    void setO2(int o2);
    void addO2(int o2);
    void subO2(int o2);
}
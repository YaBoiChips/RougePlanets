package yaboichips.rouge_planets.capabilties.tools;

import net.minecraft.nbt.CompoundTag;

public class PickaxeData {

    private boolean hasBerserk;

    private boolean climber;


    public boolean hasBerserk() {
        return hasBerserk;
    }

    public void setHasBerserk(boolean hasBerserk) {
        this.hasBerserk = hasBerserk;
    }

    public boolean isClimber() {
        return climber;
    }

    public void setClimber(boolean climber) {
        this.climber = climber;
    }



    public void serializeNBT(CompoundTag tag) {
        tag.putBoolean("Berserk", hasBerserk());
        tag.putBoolean("Climber", isClimber());

    }


    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("Berserk")) {
            this.setHasBerserk(tag.getBoolean("Berserk"));
        }
        if (tag.contains("Climber")) {
            this.setClimber(tag.getBoolean("Climber"));
        }
    }
}

package yaboichips.rouge_planets.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yaboichips.rouge_planets.PlayerData;
import yaboichips.rouge_planets.client.PlanetInventoryContainer;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin implements PlayerData {
    @Unique
    public boolean isInitiated;

    @Unique
    public int credits;
    @Unique
    public int o2;

    @Unique
    public PlanetInventoryContainer planetInventory;

    @Override
    public PlanetInventoryContainer getPlanetContainer() {
        return this.planetInventory;
    }

    @Override
    public void setPlanetContainer(PlanetInventoryContainer container) {
        this.planetInventory = container;
    }

    @Override
    public boolean getIsInitiated() {
        return this.isInitiated;
    }

    @Override
    public void setIsInitiated(boolean isInitiated) {
        this.isInitiated = isInitiated;
    }

    @Override
    public int getCredits() {
        return credits;
    }

    @Override
    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public void addCoins(int credits) {
        this.credits += credits;
    }

    @Override
    public void subtractCredits(int credits) {
        this.credits -= credits;
    }

    @Override
    public int getO2() {
        return o2;
    }

    @Override
    public void setO2(int o2) {
        this.o2 = o2;
    }

    @Override
    public void addO2(int o2) {
        this.o2 += o2;
    }

    @Override
    public void subO2(int o2) {
        this.o2 -= o2;
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void addStuffToPlayer(MinecraftServer p_254143_, ServerLevel p_254435_, GameProfile p_253651_, CallbackInfo ci) {
        setPlanetContainer(new PlanetInventoryContainer());
    }

    @Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
    public void addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.put("PlanetInventory", this.planetInventory.createTag());
        tag.putBoolean("Initiated", getIsInitiated());
        tag.putInt("Credits", getCredits());
        tag.putInt("O2", getCredits());

    }

    @Inject(method = "readAdditionalSaveData", at = @At(value = "TAIL"))
    public void readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("PlanetInventory")) {
            this.planetInventory.fromTag(tag.getList("PlanetInventory", 10));
        }
        if (tag.contains("Initiated")) {
            this.setIsInitiated(tag.getBoolean("Initiated"));
        }
        if (tag.contains("Credits")) {
            this.setCredits(tag.getInt("Credits"));
        }
        if (tag.contains("O2")) {
            this.setCredits(tag.getInt("O2"));
        }
    }
}


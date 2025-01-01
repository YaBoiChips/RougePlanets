package yaboichips.rouge_planets.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
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
    private PlanetInventoryContainer planetInventory = new PlanetInventoryContainer();


    @Override
    public PlanetInventoryContainer getPlanetContainer() {
        return this.planetInventory;
    }

    @Override
    public PlanetInventoryContainer setPlanetContainer(PlanetInventoryContainer container) {
        return this.planetInventory = container;
    }

    @Inject(method = "addAdditionalSaveData", at = @At(value = "TAIL"))
    public void addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.put("PlanetInventory", this.planetInventory.createTag());
    }

    @Inject(method = "readAdditionalSaveData", at = @At(value = "TAIL"))
    public void readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("PlanetInventory")) {
            this.planetInventory.fromTag(tag.getList("PlanetInventory", 10));
        }
    }
}


package yaboichips.rouge_planets.common.entities.canon;

import com.mojang.serialization.DynamicOps;
import commoble.infiniverse.api.InfiniverseAPI;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import yaboichips.rouge_planets.RougePlanets;
import yaboichips.rouge_planets.capabilties.player.PlayerDataUtils;
import yaboichips.rouge_planets.common.blocks.canoncontroller.CanonControllerBE;
import yaboichips.rouge_planets.common.containers.PlanetInventoryContainer;
import yaboichips.rouge_planets.common.containers.SaveableSimpleContainer;

import static yaboichips.rouge_planets.RougePlanets.MODID;


public class CanonEntity extends Mob implements GeoEntity {
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);
    private static int tickCounter = 0;
    private static ServerPlayer targetPlayer = null;
    private static boolean launchInitiated = false;
    private CanonControllerBE linkedController;

    public CanonEntity(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
        this.noPhysics = true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10000);
    }

    public void setLinkedController(CanonControllerBE controller) {
        this.linkedController = controller;
    }

    public CanonControllerBE getLinkedController() {
        return linkedController;
    }
    @Override
    protected boolean canRide(Entity p_20339_) {
        return true;
    }


    @Override
    public void tick() {
        if (!launchInitiated) {
            reset();
            return;
        }

        if (getFirstPassenger() != null) {
            tickCounter++;
        } else {
            tickCounter = 0;
        }

        if (tickCounter < 60 && this.getFirstPassenger() != null) {
            float pitch = 1.0F + (tickCounter / 20.0F) * 0.1F; // Increases pitch slightly each second
            targetPlayer.level().playSound(null, this.getFirstPassenger().blockPosition(), SoundEvents.LEVER_CLICK, SoundSource.PLAYERS, 1.0F, pitch);
        } else if (tickCounter == 60) {
            targetPlayer.stopRiding();
            targetPlayer.level().playSound(null, targetPlayer.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0F, 1.0F);
        } else if (tickCounter == 65) {
            Vec3 launchVelocity = new Vec3(0, 30, 0); // Upward velocity
            targetPlayer.connection.send(new ClientboundSetEntityMotionPacket(targetPlayer.getId(), launchVelocity)); // Send packet to sync with client
            targetPlayer.setDeltaMovement(launchVelocity); // Launch player upwards
        } else if (tickCounter == 160) {
            teleportToEnd(targetPlayer);
            reset();
        }
        super.tick();
    }

    public static void initiateLaunchSequence(ServerPlayer player) {
        targetPlayer = player;
        tickCounter = 0;
        launchInitiated = true;
    }

    private void teleportToEnd(ServerPlayer serverPlayer) {
        if (serverPlayer.level().dimension() == Level.OVERWORLD) {
            ServerLevel world = InfiniverseAPI.get().getOrCreateLevel(serverPlayer.server, ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(MODID, serverPlayer.getStringUUID() + "planet" + serverPlayer.level().random.nextInt())), () -> getWorldSettings(serverPlayer.serverLevel()));
            PlayerDataUtils.setSavedInventory(serverPlayer, new SaveableSimpleContainer(serverPlayer.getInventory()));
            loadInventoryFromCapability(serverPlayer, PlayerDataUtils.getPlanetContainer(serverPlayer));
            serverPlayer.teleportTo(world, 0, 45, 0, 0, 0);
        } else {
            serverPlayer.teleportTo(serverPlayer.getServer().getLevel(Level.OVERWORLD), 1, 100, 0, 0, 0);
            PlayerDataUtils.setPlanetContainer(serverPlayer, new PlanetInventoryContainer(serverPlayer.getInventory()));
            loadInventoryFromCapability(serverPlayer, PlayerDataUtils.getSavedInventory(serverPlayer));
        }
    }

    public LevelStem getWorldSettings(ServerLevel serverLevel) {
        MinecraftServer server = serverLevel.getServer();
        ServerLevel oldLevel = server.getLevel(RougePlanets.MINER_DIMENSION);
        DynamicOps<Tag> ops = RegistryOps.create(NbtOps.INSTANCE, server.registryAccess());
        ChunkGenerator oldChunkGenerator = oldLevel.getChunkSource().getGenerator();
        ChunkGenerator newChunkGenerator = ChunkGenerator.CODEC.encodeStart(ops, oldChunkGenerator)
                .flatMap(nbt -> ChunkGenerator.CODEC.parse(ops, nbt))
                .getOrThrow(false, s ->
                {
                    throw new CommandRuntimeException(Component.literal(String.format("Error copying dimension: %s", s)));
                });
        Holder<DimensionType> typeHolder = oldLevel.dimensionTypeRegistration();
        return new LevelStem(typeHolder, newChunkGenerator);
    }

    private void loadInventoryFromCapability(ServerPlayer player, PlanetInventoryContainer cap) {
        for (int i = 0; i < cap.getItems().size(); i++) {
            player.getInventory().setItem(i, cap.getItems().get(i).copy());
        }
    }

    private void loadInventoryFromCapability(ServerPlayer player, SaveableSimpleContainer cap) {
        for (int i = 0; i < cap.getContainerSize(); i++) {
            player.getInventory().load(cap.createTag());
        }
    }

    private static void reset() {
        tickCounter = 0;
        targetPlayer = null;
        launchInitiated = false;
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }
}

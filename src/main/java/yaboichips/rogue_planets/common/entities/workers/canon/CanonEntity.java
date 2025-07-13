package yaboichips.rogue_planets.common.entities.workers.canon;

import com.mojang.serialization.DynamicOps;
import commoble.infiniverse.api.InfiniverseAPI;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import yaboichips.rogue_planets.RoguePlanets;
import yaboichips.rogue_planets.capabilties.player.PlayerDataUtils;
import yaboichips.rogue_planets.common.containers.PlanetInventoryContainer;
import yaboichips.rogue_planets.common.containers.SaveableSimpleContainer;
import yaboichips.rogue_planets.common.items.LevelableItem;
import yaboichips.rogue_planets.common.world.parties.PartyData;
import yaboichips.rogue_planets.core.RPEntities;

import static yaboichips.rogue_planets.RoguePlanets.MODID;


public class CanonEntity extends Entity implements GeoEntity {
    private final AnimatableInstanceCache animatableInstanceCache = GeckoLibUtil.createInstanceCache(this);

    public CanonEntity(EntityType<? extends Entity> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
    }

    @Override
    protected boolean canRide(Entity p_20339_) {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (player instanceof ServerPlayer serverPlayer && !level().isClientSide()) {
            PartyData partyData = PartyData.get(serverPlayer.serverLevel());
            if (PlayerDataUtils.getIsInitiated(serverPlayer)) {
                if (partyData.isInParty(serverPlayer.getUUID())) {
                    if (partyData.isLeader(serverPlayer.getUUID())) {
                        partyData.getParty(serverPlayer.getUUID()).setLeaderInDimension(true);
                        scheduleLaunch(player);
                        return InteractionResult.SUCCESS;
                    } else if (partyData.getParty(serverPlayer.getUUID()).isLeaderInDimension()) {
                        scheduleLaunch(player);
                        return InteractionResult.SUCCESS;
                    } else {
                        player.sendSystemMessage(Component.literal("Party Leader must enter first"));
                        return InteractionResult.FAIL;
                    }
                } else {
                    scheduleLaunch(player);
                    return InteractionResult.SUCCESS;
                }
            } else {
                player.sendSystemMessage(Component.literal("Talk to the CEO First"));
                return InteractionResult.FAIL;
            }
        } else {
            return InteractionResult.FAIL;
        }
    }

    public void scheduleLaunch(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.startRiding(this);
            RoguePlanets.scheduleTask(27, player::stopRiding);
            RoguePlanets.scheduleTask(30, () -> launchPlayer(serverPlayer, true));
            RoguePlanets.scheduleTask(45, () -> launchPlayer(serverPlayer, false));
            RoguePlanets.scheduleTask(60, () -> teleportToLevel(serverPlayer));
        }
    }

    public void launchPlayer(ServerPlayer serverPlayer, boolean playSound) {
        Vec3 launchVelocity = new Vec3(0, 70, 0); // Upward velocity
        serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer.getId(), launchVelocity)); // Send packet to sync with client
        serverPlayer.setDeltaMovement(launchVelocity); // Launch player upwards
        if (playSound) {
            serverPlayer.playSound(SoundEvents.GENERIC_EXPLODE);
        }
    }

    private void teleportToLevel(ServerPlayer serverPlayer) {
        PartyData partyData = PartyData.get(serverPlayer.serverLevel());
        if (serverPlayer.level().dimension() == Level.OVERWORLD) {
            if (partyData.isInParty(serverPlayer.getUUID()) && !partyData.isLeader(serverPlayer.getUUID())) {
                PlayerDataUtils.setO2(serverPlayer, 19000);
                serverPlayer.teleportTo(this.getServer().getPlayerList().getPlayer(partyData.getParty(serverPlayer.getUUID()).leader).serverLevel(), serverPlayer.getX(), 145, serverPlayer.getZ(), 0, 0);
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 120));
                loadPlanetInventory(serverPlayer, PlayerDataUtils.getPlanetContainer(serverPlayer));
            } else {
                ServerLevel world = InfiniverseAPI.get().getOrCreateLevel(serverPlayer.server, ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(MODID, serverPlayer.getStringUUID() + "planet" + serverPlayer.level().random.nextInt())), () -> getWorldSettings(serverPlayer.serverLevel()));
                world.getServer().getWorldData().worldGenOptions().seed = random.nextInt();
                PlayerDataUtils.setSavedInventory(serverPlayer, new SaveableSimpleContainer(serverPlayer.getInventory()));
                loadPlanetInventory(serverPlayer, PlayerDataUtils.getPlanetContainer(serverPlayer));
                PlayerDataUtils.setO2(serverPlayer, 19000);
                serverPlayer.teleportTo(world, serverPlayer.getX(), 145, serverPlayer.getZ(), 0, 0);
                serverPlayer.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 120));

                CanonEntity canon = RPEntities.CANON.get().create(world);
                Vec3 canonPos = new BlockPos(serverPlayer.blockPosition().getX(), 124, serverPlayer.blockPosition().getZ()).getCenter().add(0, -.5, 0);
                canon.setPos(canonPos);
                for (int x = serverPlayer.blockPosition().getX() - 1; x <= serverPlayer.blockPosition().getX() + 1; x++) {
                    for (int z = serverPlayer.blockPosition().getZ() - 1; z <= serverPlayer.blockPosition().getZ() + 1; z++) {
                        BlockPos platformPos = new BlockPos(x, 123, z);
                        world.setBlockAndUpdate(platformPos, Blocks.SMOOTH_STONE.defaultBlockState());
                    }
                }
                canon.setGlowingTag(true);
                world.addFreshEntity(canon);
            }
        } else {
            serverPlayer.teleportTo(serverPlayer.getServer().getLevel(Level.OVERWORLD), serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), 0, 0);
            PlayerDataUtils.setPlanetContainer(serverPlayer, new PlanetInventoryContainer(serverPlayer.getInventory()));
            loadOverworldInventory(serverPlayer, PlayerDataUtils.getSavedInventory(serverPlayer));
            serverPlayer.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 420));
            RoguePlanets.scheduleTask(419, () -> checkToAddSlowFalling(serverPlayer));
        }
    }

    public void checkToAddSlowFalling(ServerPlayer player) {
        BlockPos playerPos = player.blockPosition();
        BlockPos targetPos = playerPos.below(10);
        ServerLevel world = player.serverLevel();
        if (world.getBlockState(targetPos).isAir()) {
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 420));
        }
    }

    public LevelStem getWorldSettings(ServerLevel serverLevel) {
        MinecraftServer server = serverLevel.getServer();
        ServerLevel oldLevel = server.getLevel(RoguePlanets.MINER_DIMENSION);
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

    private void loadPlanetInventory(ServerPlayer player, PlanetInventoryContainer cap) {
        for (int i = 0; i < cap.getItems().size(); i++) {
            ItemStack item = cap.getItems().get(i).copy();
            if (item.getItem() instanceof LevelableItem levelableItem) {
                item.setDamageValue((int) Math.round((50 * Math.pow(1 + 0.1, levelableItem.getLevel(item)))));
            }
            player.getInventory().setItem(i, item);
        }
    }

    private void loadOverworldInventory(ServerPlayer player, SaveableSimpleContainer cap) {
        for (int i = 0; i < cap.getContainerSize(); i++) {
            player.getInventory().load(cap.createTag());
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatableInstanceCache;
    }
}

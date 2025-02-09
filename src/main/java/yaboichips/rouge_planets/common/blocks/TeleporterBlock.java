package yaboichips.rouge_planets.common.blocks;

import com.mojang.serialization.DynamicOps;
import commoble.infiniverse.api.InfiniverseAPI;
import net.minecraft.commands.CommandRuntimeException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import yaboichips.rouge_planets.RougePlanets;
import yaboichips.rouge_planets.capabilties.player.PlayerDataUtils;
import yaboichips.rouge_planets.common.containers.PlanetInventoryContainer;
import yaboichips.rouge_planets.common.containers.SaveableSimpleContainer;

import static yaboichips.rouge_planets.RougePlanets.MODID;

public class TeleporterBlock extends Block {
    public TeleporterBlock(Properties p_49795_) {
        super(p_49795_);
    }

    //TODO add armor changing and move all this crap to the canon
    @Override
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (player instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.level().dimension() == Level.OVERWORLD) {
                ServerLevel world = InfiniverseAPI.get().getOrCreateLevel(serverPlayer.server, ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(MODID, player.getStringUUID() + "planet" + level.random.nextInt())), () -> getWorldSettings(serverPlayer.serverLevel()));
                PlayerDataUtils.setSavedInventory(serverPlayer, new SaveableSimpleContainer(player.getInventory()));
                loadInventoryFromCapability(serverPlayer, PlayerDataUtils.getPlanetContainer(serverPlayer));
                serverPlayer.teleportTo(world, 0, 45, 0, 0, 0);
            } else {
                serverPlayer.teleportTo(serverPlayer.getServer().getLevel(Level.OVERWORLD), 1, 100, 0, 0, 0);
                PlayerDataUtils.setPlanetContainer(serverPlayer, new PlanetInventoryContainer(player.getInventory()));
                loadInventoryFromCapability(serverPlayer, PlayerDataUtils.getSavedInventory(serverPlayer));
            }
        }
        return InteractionResult.SUCCESS;
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
}

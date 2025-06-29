package yaboichips.rouge_planets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import commoble.infiniverse.api.InfiniverseAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import yaboichips.rouge_planets.capabilties.ArmorData;
import yaboichips.rouge_planets.capabilties.RougeCapabilities;
import yaboichips.rouge_planets.capabilties.player.ClientPlayerData;
import yaboichips.rouge_planets.capabilties.player.PlayerData;
import yaboichips.rouge_planets.capabilties.player.PlayerDataProvider;
import yaboichips.rouge_planets.capabilties.player.PlayerDataUtils;
import yaboichips.rouge_planets.client.renderers.GenericMonsterRenderer;
import yaboichips.rouge_planets.client.renderers.HumanRenderer;
import yaboichips.rouge_planets.common.blocks.canoncontroller.CanonControllerScreen;
import yaboichips.rouge_planets.common.entities.monsters.GenericMonster;
import yaboichips.rouge_planets.common.entities.workers.HumanMob;
import yaboichips.rouge_planets.common.entities.workers.augmentor.AugmentorScreen;
import yaboichips.rouge_planets.common.entities.workers.canon.CanonEntityRenderer;
import yaboichips.rouge_planets.common.entities.workers.ceo.CEOScreen;
import yaboichips.rouge_planets.common.entities.workers.forgemaster.ForgeMasterScreen;
import yaboichips.rouge_planets.common.entities.workers.merchant.RPMerchantScreen;
import yaboichips.rouge_planets.common.world.DimensionSkybox;
import yaboichips.rouge_planets.core.RPBlocks;
import yaboichips.rouge_planets.core.RPEntities;
import yaboichips.rouge_planets.network.RougePackets;
import yaboichips.rouge_planets.network.SendPlayerDataPacket;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static yaboichips.rouge_planets.core.RPBlockEntities.BLOCK_ENTITY_TYPES;
import static yaboichips.rouge_planets.core.RPBlocks.BLOCKS;
import static yaboichips.rouge_planets.core.RPEntities.ENTITIES;
import static yaboichips.rouge_planets.core.RPItems.CREATIVE_MODE_TABS;
import static yaboichips.rouge_planets.core.RPItems.ITEMS;
import static yaboichips.rouge_planets.core.RPMenus.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RougePlanets.MODID)
public class RougePlanets {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "rougeplanets";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static long currentTick = 0;
    private static final Map<Long, Runnable> scheduledTasks = new ConcurrentHashMap<>();
    public static ResourceKey<Level> MINER_DIMENSION = ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(MODID, "miner_dimension"));
    public Map<Block, RenderType> renderBlocks = new HashMap<>();

    /*
    TODO MOBS, spitter, gremlin, bulky guy
    TODO BIOMES
    TODO POI'S
     */

    public RougePlanets() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        ENTITIES.register(modEventBus);
        MENUS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        BLOCK_ENTITY_TYPES.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::bakeLayers);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::entityAttributes);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        LOGGER.info("Rouge Planets Registered");
    }

    public void onCommonSetup(FMLCommonSetupEvent event) {
        RougePackets.registerPackets();
    }

    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(ArmorData.class);
        event.register(PlayerData.class);
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(FORGE_MASTER_MENU.get(), ForgeMasterScreen::new);
            MenuScreens.register(MERCHANT_MENU.get(), RPMerchantScreen::new);
            MenuScreens.register(AUGMENTOR_MENU.get(), AugmentorScreen::new);
            MenuScreens.register(CEO_MENU.get(), CEOScreen::new);
            MenuScreens.register(CANON_CONTROLLER.get(), CanonControllerScreen::new);
            EntityRenderers.register(RPEntities.FORGE_MASTER.get(), HumanRenderer::new);
            EntityRenderers.register(RPEntities.RP_MERCHANT.get(), HumanRenderer::new);
            EntityRenderers.register(RPEntities.AUGMENTOR.get(), HumanRenderer::new);
            EntityRenderers.register(RPEntities.CEO.get(), HumanRenderer::new);
            EntityRenderers.register(RPEntities.CANON.get(), CanonEntityRenderer::new);

            EntityRenderers.register(RPEntities.CYCLOPS.get(), GenericMonsterRenderer::new);
            EntityRenderers.register(RPEntities.ALIEN.get(), GenericMonsterRenderer::new);
        });
        renderBlocks.put(RPBlocks.SPACE_TORCH.get(), RenderType.cutout());
        renderBlocks.forEach(ItemBlockRenderTypes::setRenderLayer);
    }

    public void bakeLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HumanRenderer.LAYER_LOCATION, () -> LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F), 64, 64));
    }

    public void entityAttributes(final EntityAttributeCreationEvent event) {
        event.put(RPEntities.FORGE_MASTER.get(), HumanMob.createAttributes().build());
        event.put(RPEntities.RP_MERCHANT.get(), HumanMob.createAttributes().build());
        event.put(RPEntities.AUGMENTOR.get(), HumanMob.createAttributes().build());
        event.put(RPEntities.CEO.get(), HumanMob.createAttributes().build());

        event.put(RPEntities.CYCLOPS.get(), GenericMonster.createAttributes().build());
        event.put(RPEntities.ALIEN.get(), GenericMonster.createAttributes().build());
    }

    public static void scheduleTask(long tick, Runnable task) {
        scheduledTasks.put(tick + RougePlanets.currentTick, task);
    }

    public static void clearTask() {
        scheduledTasks.clear();
    }

    @SubscribeEvent
    public void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            player.reviveCaps();
            if (!event.getObject().getCapability(RougeCapabilities.PLAYER_DATA).isPresent()) {
                event.addCapability(RougeCapabilities.PLAYER_DATA_LOCATION, new PlayerDataProvider());
            }
        }
    }


    @SubscribeEvent
    public void onPlayerJoin(EntityJoinLevelEvent event) {
        if (event.getEntity().level().isClientSide)
            return;
        if (event.getEntity() instanceof ServerPlayer player) {
            player.reviveCaps();
            RougePackets.sendToPlayer(new SendPlayerDataPacket(PlayerDataUtils.getO2(player), PlayerDataUtils.getCredits(player)), player);
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        if (event.getOriginal().level().isClientSide)
            return;

        ResourceKey<Level> fromDimension = event.getOriginal().level().dimension();

        // Check if the dimension the player is leaving belongs to your mod
        if (fromDimension.location().getNamespace().equals(MODID)) {
            if (event.getEntity().getServer().getLevel(fromDimension).players().isEmpty()) {
                InfiniverseAPI.get().markDimensionForUnregistration(event.getEntity().getServer(), fromDimension);
            }
        }
        if (event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            LazyOptional<PlayerData> loNewCap = event.getOriginal().getCapability(RougeCapabilities.PLAYER_DATA);
            LazyOptional<PlayerData> loOldCap = event.getOriginal().getCapability(RougeCapabilities.PLAYER_DATA);
            loNewCap.ifPresent(newCap -> {
                loOldCap.ifPresent(oldCap -> {
                    event.getOriginal().reviveCaps();
                    PlayerDataUtils.setO2((ServerPlayer) event.getEntity(), oldCap.getO2());
                    PlayerDataUtils.setCredits((ServerPlayer) event.getEntity(), oldCap.getCredits());
                    PlayerDataUtils.setInitiated((ServerPlayer) event.getEntity(), oldCap.getIsInitiated());
                    PlayerDataUtils.setPlanetContainer((ServerPlayer) event.getEntity(), oldCap.getPlanetContainer());
                    PlayerDataUtils.setArmorContainer((ServerPlayer) event.getEntity(), oldCap.getArmorContainer());
                });
            });
        }
    }

    @SubscribeEvent
    public void onPlayerAttack(AttackEntityEvent event) {
        event.getEntity().getCapability(RougeCapabilities.PLAYER_DATA).ifPresent(playerData -> {
            if (playerData.isPyrolithActive()) {
                event.getTarget().setSecondsOnFire(30);
            }
            if (playerData.isAzuriumActive()) {
                ((LivingEntity) event.getTarget()).addEffect(new MobEffectInstance(MobEffects.LEVITATION, 200, 1));
            }
        });
    }

    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        ResourceKey<Level> fromDimension = event.getFrom();
        // Check if the dimension the player is leaving belongs to your mod
        if (fromDimension.location().getNamespace().equals(MODID)) {
            if (event.getEntity().getServer().getLevel(fromDimension).players().isEmpty()) {
                InfiniverseAPI.get().markDimensionForUnregistration(event.getEntity().getServer(), fromDimension);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient()) return;
        ServerPlayer player = (ServerPlayer) event.player;
        RougePackets.sendToPlayer(new SendPlayerDataPacket(PlayerDataUtils.getO2(player), PlayerDataUtils.getCredits(player)), player);
        event.player.getCapability(RougeCapabilities.PLAYER_DATA).ifPresent(playerData -> {
            if (playerData.getAzuriumTimer() > 0) {
                playerData.setAzuriumTimer(playerData.getAzuriumTimer() - 1);
            }
            if (playerData.getPyrolithTimer() > 0) {
                playerData.setPyrolithTimer(playerData.getPyrolithTimer() - 1);
            }
            if (playerData.getElectryteTimer() > 0) {
                playerData.setElectryteTimer(playerData.getElectryteTimer() - 1);
            }
            if (playerData.getChlorosynthTimer() > 0) {
                playerData.setChlorosynthTimer(playerData.getChlorosynthTimer() - 1);
            }
        });
    }

    @SubscribeEvent
    public void onRenderGuiOverlay(RenderGuiOverlayEvent event) {
        if (event.getOverlay() == VanillaGuiOverlay.PLAYER_HEALTH.type()) {
            renderIntOnHud(event.getGuiGraphics());
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }
        currentTick++;
        Iterator<Map.Entry<Long, Runnable>> iterator = scheduledTasks.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, Runnable> entry = iterator.next();
            if (entry.getKey() <= currentTick) {
                try {
                    entry.getValue().run();
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.info("ROUGE PLANET SERVER EVENT KILLED ITSELF, YELL AT CHIPS");
                }
                iterator.remove();
            }
        }
    }

    private void renderIntOnHud(GuiGraphics guiGraphics) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        if (!mc.player.isLocalPlayer()) return;
        if (mc.player.level().dimension().location().getPath().contains("planet")) {
            // Set the text to render
            String text = formatTicksToTime(ClientPlayerData.getO2());

            // Determine the position (bottom-left corner)
            int x = 5; // 5 pixels from the left
            int y = mc.getWindow().getGuiScaledHeight() - 15; // 15 pixels from the bottom

            // Draw the text
            RenderSystem.enableBlend();
            guiGraphics.drawString(mc.font, text, x, y, 0xFFFFFF); // White color
            RenderSystem.disableBlend();
        }
    }

    public static String formatTicksToTime(int ticks) {
        int totalSeconds = ticks / 20;

        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
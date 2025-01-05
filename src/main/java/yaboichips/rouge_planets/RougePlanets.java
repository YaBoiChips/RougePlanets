package yaboichips.rouge_planets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import org.slf4j.Logger;
import yaboichips.rouge_planets.capabilties.ArmorData;
import yaboichips.rouge_planets.capabilties.PlayerData;
import yaboichips.rouge_planets.capabilties.PlayerDataUtils;
import yaboichips.rouge_planets.capabilties.RougeCapabilities;
import yaboichips.rouge_planets.capabilties.handlers.CapabilityHandler;
import yaboichips.rouge_planets.client.renderers.HumanRenderer;
import yaboichips.rouge_planets.common.entities.forgemaster.ForgeMaster;
import yaboichips.rouge_planets.common.entities.forgemaster.ForgeMasterScreen;
import yaboichips.rouge_planets.core.RPEntities;
import yaboichips.rouge_planets.network.RougePackets;
import yaboichips.rouge_planets.network.SendPlayerTimePacket;
import yaboichips.rouge_planets.network.SyncPlayerDataPacket;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static yaboichips.rouge_planets.capabilties.RougeCapabilities.PLAYER_DATA;
import static yaboichips.rouge_planets.core.RPBlocks.BLOCKS;
import static yaboichips.rouge_planets.core.RPEntities.ENTITIES;
import static yaboichips.rouge_planets.core.RPItems.CREATIVE_MODE_TABS;
import static yaboichips.rouge_planets.core.RPItems.ITEMS;
import static yaboichips.rouge_planets.core.RPMenus.FORGE_MASTER_MENU;
import static yaboichips.rouge_planets.core.RPMenus.MENUS;
import static yaboichips.rouge_planets.network.RougePackets.CHANNEL;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RougePlanets.MODID)
@Mod.EventBusSubscriber(modid = "yourmodid")
public class RougePlanets {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "rougeplanets";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    private static int syncedTime = 0;
    public static long currentTick = 0;
    private static final Map<Long, Runnable> scheduledTasks = new ConcurrentHashMap<>();


    // Create a Deferred Register to hold Blocks which will all be registered under the "rougeplanets" namespace


    public RougePlanets() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        ENTITIES.register(modEventBus);
        MENUS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());


        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::bakeLayers);
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::entityAttributes);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
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
            EntityRenderers.register(RPEntities.FORGE_MASTER.get(), HumanRenderer::new);
        });
    }

    public void bakeLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HumanRenderer.LAYER_LOCATION, () -> LayerDefinition.create(HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F), 64, 64));
    }

    public void entityAttributes(final EntityAttributeCreationEvent event) {
        event.put(RPEntities.FORGE_MASTER.get(), ForgeMaster.createAttributes().build());
    }

    public static void scheduleTask(long tick, Runnable task) {
        scheduledTasks.put(tick, task);
    }

    public static void setTime(int time) {
        syncedTime = time;
    }

    public static void syncData(ServerPlayer player) {
        player.getCapability(PLAYER_DATA).ifPresent(data -> {
            CompoundTag tag = new CompoundTag();
            data.serializeNBT(tag);
            CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new SyncPlayerDataPacket(tag));
        });
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        System.out.println("Joined");
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(RougeCapabilities.PLAYER_DATA).ifPresent(original -> {
                event.getEntity().getCapability(RougeCapabilities.PLAYER_DATA).ifPresent(clone -> {
                    clone.setIsInitiated(original.getIsInitiated());
                    clone.setPlanetContainer(original.getPlanetContainer());
                    clone.setCredits(original.getCredits());
                    clone.setO2(original.getO2());
                });
            });
        }
    }
    @SubscribeEvent
    public void onPlayerSave(PlayerEvent.SaveToFile event) {
        System.out.println("SAVED");
        if (event.getEntity() instanceof ServerPlayer player){
            syncData(player);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.side.isClient()) return;
        ServerPlayer player = (ServerPlayer) event.player;
        RougePackets.CHANNEL.sendTo(new SendPlayerTimePacket(PlayerDataUtils.getO2(player)), player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        if (PlayerDataUtils.getO2(player) > 300) {
            player.kill();
        }
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
            return; // Process only END phase to avoid duplication
        }
        // Increment the tick counter
        currentTick++;
        // Check and execute tasks scheduled for the current tick
        Iterator<Map.Entry<Long, Runnable>> iterator = scheduledTasks.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Long, Runnable> entry = iterator.next();
            if (entry.getKey() <= currentTick) {
                try {
                    entry.getValue().run(); // Execute the Runnable
                } catch (Exception e) {
                    e.printStackTrace(); // Log any errors during execution
                }
                iterator.remove(); // Remove the task after execution
            }
        }
    }

    private void renderIntOnHud(GuiGraphics guiGraphics) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        // Set the text to render
        String text = "Value: " + syncedTime;

        // Determine the position (bottom-left corner)
        int x = 5; // 5 pixels from the left
        int y = mc.getWindow().getGuiScaledHeight() - 15; // 15 pixels from the bottom

        // Draw the text
        RenderSystem.enableBlend();
        guiGraphics.drawString(mc.font, text, x, y, 0xFFFFFF); // White color
        RenderSystem.disableBlend();
    }
}

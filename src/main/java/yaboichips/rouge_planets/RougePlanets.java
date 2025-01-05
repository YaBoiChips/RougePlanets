package yaboichips.rouge_planets;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import yaboichips.rouge_planets.capabilties.armor.IPaddingCapability;
import yaboichips.rouge_planets.client.renderers.HumanRenderer;
import yaboichips.rouge_planets.common.entities.forgemaster.ForgeMaster;
import yaboichips.rouge_planets.common.entities.forgemaster.ForgeMasterScreen;
import yaboichips.rouge_planets.core.RPEntities;
import yaboichips.rouge_planets.network.RougePackets;

import static yaboichips.rouge_planets.core.RPBlocks.BLOCKS;
import static yaboichips.rouge_planets.core.RPEntities.ENTITIES;
import static yaboichips.rouge_planets.core.RPItems.CREATIVE_MODE_TABS;
import static yaboichips.rouge_planets.core.RPItems.ITEMS;
import static yaboichips.rouge_planets.core.RPMenus.FORGE_MASTER_MENU;
import static yaboichips.rouge_planets.core.RPMenus.MENUS;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RougePlanets.MODID)
public class RougePlanets {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "rougeplanets";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
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
        event.register(IPaddingCapability.class);
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
}

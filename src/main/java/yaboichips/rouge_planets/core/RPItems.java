package yaboichips.rouge_planets.core;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yaboichips.rouge_planets.common.items.ExplorerSuit;


import static yaboichips.rouge_planets.RougePlanets.MODID;

public class RPItems {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);


//    public static final RegistryObject<Item> TELEPORTER_BLOCK_ITEM = ITEMS.register("teleporter_block", () -> new BlockItem(TELEPORTER_BLOCK.get(), new Item.Properties()));
//    public static final RegistryObject<Item> CANON_CONTROLLER_BLOCK_ITEM = ITEMS.register("canon_controller", () -> new BlockItem(CANON_CONTROLLER.get(), new Item.Properties()));

    public static final RegistryObject<Item> RUBY = ITEMS.register("ruby",() -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TESTARMOR = ITEMS.register("test",() -> new ExplorerSuit(ArmorMaterials.GOLD, ArmorItem.Type.HELMET, new Item.Properties()));



    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("rp_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .title(Component.literal("Rouge Planets"))
            .icon(() -> RUBY.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                ITEMS.getEntries().forEach(item->output.accept(item.get()));
            }).build());
}

package yaboichips.rogue_planets.core;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yaboichips.rogue_planets.common.blocks.canoncontroller.CanonControllerMenu;
import yaboichips.rogue_planets.common.entities.workers.augmentor.AugmentorMenu;
import yaboichips.rogue_planets.common.entities.workers.ceo.CEOMenu;
import yaboichips.rogue_planets.common.entities.workers.forgemaster.ForgeMasterMenu;
import yaboichips.rogue_planets.common.entities.workers.merchant.RPMerchantMenu;

import static yaboichips.rogue_planets.RoguePlanets.MODID;


public class RPMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

    public static final RegistryObject<MenuType<ForgeMasterMenu>> FORGE_MASTER_MENU = MENUS.register("forge_master_menu", () -> IForgeMenuType.create(ForgeMasterMenu::new));
    public static final RegistryObject<MenuType<RPMerchantMenu>> MERCHANT_MENU = MENUS.register("rp_merchant_menu", () -> IForgeMenuType.create(RPMerchantMenu::new));
    public static final RegistryObject<MenuType<AugmentorMenu>> AUGMENTOR_MENU = MENUS.register("augmentor_menu", () -> IForgeMenuType.create(AugmentorMenu::new));
    public static final RegistryObject<MenuType<CEOMenu>> CEO_MENU = MENUS.register("ceo_menu", () -> IForgeMenuType.create(CEOMenu::new));
    public static final RegistryObject<MenuType<CanonControllerMenu>> CANON_CONTROLLER = MENUS.register("canon_controller", () -> IForgeMenuType.create(CanonControllerMenu::new));
}

package yaboichips.rouge_planets.core;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yaboichips.rouge_planets.common.entities.forgemaster.ForgeMasterMenu;
import yaboichips.rouge_planets.common.entities.merchant.RPMerchantMenu;

import static yaboichips.rouge_planets.RougePlanets.MODID;

public class RPMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

    public static final RegistryObject<MenuType<ForgeMasterMenu>> FORGE_MASTER_MENU = MENUS.register("forge_master_menu", () -> IForgeMenuType.create(ForgeMasterMenu::new));
    public static final RegistryObject<MenuType<RPMerchantMenu>> MERCHANT_MENU = MENUS.register("rp_merchant_menu", () -> IForgeMenuType.create(RPMerchantMenu::new));

}

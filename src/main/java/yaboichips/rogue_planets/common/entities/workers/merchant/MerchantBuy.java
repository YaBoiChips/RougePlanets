package yaboichips.rogue_planets.common.entities.workers.merchant;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import yaboichips.rogue_planets.core.RPItems;

import java.util.ArrayList;
import java.util.List;

public record MerchantBuy(ItemStack item, int price){
    public static final List<MerchantBuy> BUYS = new ArrayList<>();

    public static final MerchantBuy RUBY = register(new MerchantBuy(RPItems.RUBY.get().getDefaultInstance(), 50));
    public static final MerchantBuy SAPPHIRE = register(new MerchantBuy(RPItems.SAPPHIRE.get().getDefaultInstance(), 40));
    public static final MerchantBuy OPAL = register(new MerchantBuy(RPItems.OPAL.get().getDefaultInstance(), 30));
    public static final MerchantBuy TOPAZ = register(new MerchantBuy(RPItems.TOPAZ.get().getDefaultInstance(), 20));
    public static final MerchantBuy ONYX = register(new MerchantBuy(RPItems.ONYX.get().getDefaultInstance(), 10));
    public static final MerchantBuy PYRITE = register(new MerchantBuy(RPItems.PYRITE.get().getDefaultInstance(), 5));
    public static final MerchantBuy AMBER = register(new MerchantBuy(RPItems.AMBER.get().getDefaultInstance(), 2));
    public static final MerchantBuy QUARTZINE = register(new MerchantBuy(RPItems.QUARTZINE.get().getDefaultInstance(), 1));
    public static final MerchantBuy SPACE_TORCH = register(new MerchantBuy(RPItems.QUARTZINE.get().getDefaultInstance(), 1));
    public static final MerchantBuy JERKY = register(new MerchantBuy(RPItems.QUARTZINE.get().getDefaultInstance(), 1));
    public static final MerchantBuy COBBLESTONE = register(new MerchantBuy(Items.COBBLESTONE.getDefaultInstance(), 0));
    public static final MerchantBuy DEEPSLATE = register(new MerchantBuy(Items.COBBLED_DEEPSLATE.getDefaultInstance(), 0));


    public static MerchantBuy register(MerchantBuy sale) {
        BUYS.add(sale);
        return sale;
    }
}

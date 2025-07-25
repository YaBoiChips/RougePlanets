package yaboichips.rogue_planets.common.entities.workers.merchant;

import net.minecraft.world.item.ItemStack;
import yaboichips.rogue_planets.core.RPItems;

import java.util.ArrayList;
import java.util.List;

public record MerchantSales(ItemStack item, int price) {

    public static final List<MerchantSales> SALES = new ArrayList<>();

    public static final MerchantSales ROPE = register(new MerchantSales(RPItems.ROPE.get().getDefaultInstance(), 5));
    public static final MerchantSales JERKY = register(new MerchantSales(RPItems.JERKY.get().getDefaultInstance(), 3));
    public static final MerchantSales SPACE_TORCH = register(new MerchantSales(RPItems.SPACE_TORCH.get().getDefaultInstance(), 3));
    public static final MerchantSales MAGAZINE = register(new MerchantSales(RPItems.MAGAZINE.get().getDefaultInstance(), 10));


    public static MerchantSales register(MerchantSales sale) {
        SALES.add(sale);
        return sale;
    }
}


package yaboichips.rouge_planets.common.entities.merchant;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import yaboichips.rouge_planets.core.RPItems;

import java.util.ArrayList;
import java.util.List;

public class MerchantSales {

    public static final List<MerchantSales> SALES = new ArrayList<>();

    private final ItemStack item;
    private final int price;

    public MerchantSales(ItemStack item, int price) {
        this.item = item;
        this.price = price;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getPrice() {
        return price;
    }

    public static final MerchantSales ROPE = register(new MerchantSales(RPItems.ROPE.get().getDefaultInstance(), 5));
    public static final MerchantSales JERKY = register(new MerchantSales(RPItems.JERKY.get().getDefaultInstance(), 3));
    public static final MerchantSales SPACE_TORCH = register(new MerchantSales(RPItems.SPACE_TORCH.get().getDefaultInstance(), 3));
    public static final MerchantSales MAGAZINE = register(new MerchantSales(RPItems.MAGAZINE.get().getDefaultInstance(), 10));
    public static final MerchantSales AUGMENT = register(new MerchantSales(RPItems.TEST_AUGMENT.get().getDefaultInstance(), 100));



    public static MerchantSales register(MerchantSales sale){
        SALES.add(sale);
        return sale;
    }
}

package yaboichips.rouge_planets.common.entities.merchant;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import yaboichips.rouge_planets.capabilties.player.PlayerDataUtils;
import yaboichips.rouge_planets.core.RPMenus;

public class RPMerchantMenu extends AbstractContainerMenu {

    private final SimpleContainer container;

    public Player player;

    public RPMerchantMenu(int id, Inventory playerInventory, SimpleContainer container) {
        super(RPMenus.MERCHANT_MENU.get(), id);
        checkContainerSize(container, 36);
        this.container = container;
        this.player = playerInventory.player;
        int slotID = 0;

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(container, slotID, 88 + i * 18, 232));
            slotID++;
        }
        for (int i = 0; i < 4; i++) {
            this.addSlot(new Slot(container, slotID, 88 + i * 18, 197));
            slotID++;
        }
    }


    public RPMerchantMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(i, inventory, new SimpleContainer(36));
    }
    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }
    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

    public void purchaseItem(ItemStack stack, int price) {
        if (!player.level().isClientSide()) {
            if (PlayerDataUtils.getCredits((ServerPlayer) player) >= price) {
                this.container.addItem(stack);
                PlayerDataUtils.subCredits((ServerPlayer) player, price);
            }
        } else {
            player.sendSystemMessage(Component.literal("You need " + (price - PlayerDataUtils.getCredits((ServerPlayer) player)) + " more Credits"));
        }
    }
}

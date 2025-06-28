package yaboichips.rouge_planets.common.entities.workers.merchant;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import yaboichips.rouge_planets.capabilties.player.PlayerDataUtils;
import yaboichips.rouge_planets.core.RPMenus;

public class RPMerchantMenu extends AbstractContainerMenu {

    private final SimpleContainer container;

    public Player player;

    public RPMerchantMenu(int id, Inventory playerInventory, SimpleContainer container, Container armor) {
        super(RPMenus.MERCHANT_MENU.get(), id);
        checkContainerSize(container, 36);
        this.container = container;
        this.player = playerInventory.player;
        int slotID = 0;

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(container, slotID, 88 + i * 18, 232));
            slotID++;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(container, slotID, 88 + j * 18, 174 + i * 18));
                slotID++;
            }
        }
        for (int i = 0; i < 4; i++) {
            this.addSlot(new Slot(armor, i, 88 + i * 18, 151));
        }
    }

    public RPMerchantMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(i, inventory, new SimpleContainer(36), new SimpleContainer(4));
    }
    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }
    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

    @Override
    public void clicked(int slot, int p_150401_, ClickType clickType, Player p_150403_) {
        if (slot >= 0) {
            super.clicked(slot, p_150401_, clickType, p_150403_);
        }
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

    public String getCredits(ServerPlayer player){
        return String.valueOf(PlayerDataUtils.getCredits(player));
    }
}

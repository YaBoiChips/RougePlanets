package yaboichips.rouge_planets.common.entities.workers.ceo;

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
import yaboichips.rouge_planets.common.items.LevelableItem;
import yaboichips.rouge_planets.core.RPItems;
import yaboichips.rouge_planets.core.RPMenus;

public class CEOMenu extends AbstractContainerMenu {
    private Container container;

    public Player player;
    private final SimpleContainer levelSlot = new SimpleContainer(1);

    public CEOMenu(int id, Inventory playerInventory, Container container, Container armor) {
        super(RPMenus.CEO_MENU.get(), id);
        checkContainerSize(container, 36);
        checkContainerSize(levelSlot, 1);
        checkContainerSize(armor, 4);
        this.container = container;
        this.player = playerInventory.player;

        this.addSlot(new Slot(levelSlot, 0, 120, 42));

        int slotID = 0;

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(container, slotID, 48 + i * 18, 222));
            slotID++;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(container, slotID, 48 + j * 18, 164 + i * 18));
                slotID++;
            }
        }
        for (int i = 0; i < 4; i++) {
            this.addSlot(new Slot(armor, i, 48 + i * 18, 141));
        }
    }

    public CEOMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(i, inventory, new SimpleContainer(36), new SimpleContainer(4));
    }

    @Override
    public void clicked(int slot, int p_150401_, ClickType clickType, Player p_150403_) {
        if (slot >= 0) {
            super.clicked(slot, p_150401_, clickType, p_150403_);
        }
    }
    @Override
    public void removed(Player p_38940_) {
        if (container instanceof SimpleContainer simpleContainer) {
            simpleContainer.addItem(levelSlot.getItem(0));
        }
        super.removed(p_38940_);
    }
    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }
    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }
    public void layOff() {
        if (!player.level().isClientSide()) {
            ItemStack stack = levelSlot.getItem(0);
            if (!stack.isEmpty() && stack.getItem() instanceof LevelableItem) {
                if (((LevelableItem) stack.getItem()).getLevel(stack) == 20) {
                    PlayerDataUtils.setInitiated((ServerPlayer) player, false);
                    player.getInventory().add(RPItems.TEST_AUGMENT.get().getDefaultInstance());
                    player.sendSystemMessage(Component.literal("If you worked longer we would have had to pay you benefits. Here's an augment as severance, come back to me when your ready to be rehired"));
                } else {
                    player.sendSystemMessage(Component.literal("You haven't worked long enough for the lay off, come back to me with a Level 20 Item"));
                }
            }
        }
    }
}
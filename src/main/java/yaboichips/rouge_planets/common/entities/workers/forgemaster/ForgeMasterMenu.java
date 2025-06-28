package yaboichips.rouge_planets.common.entities.workers.forgemaster;

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
import yaboichips.rouge_planets.core.RPMenus;

public class ForgeMasterMenu extends AbstractContainerMenu {

    private Container container;

    public Player player;

    private final SimpleContainer levelSlot = new SimpleContainer(1);

    public ForgeMasterMenu(int id, Inventory playerInventory, Container container, Container armor) {
        super(RPMenus.FORGE_MASTER_MENU.get(), id);
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

    public ForgeMasterMenu(int id, Inventory playerInventory) {
        super(RPMenus.FORGE_MASTER_MENU.get(), id);
    }

    public ForgeMasterMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
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

    public void levelUpItem() {
        if (!player.level().isClientSide()) {
            ItemStack stack = levelSlot.getItem(0);
            if (!stack.isEmpty() && stack.getItem() instanceof LevelableItem) {
                if (((LevelableItem) stack.getItem()).getLevel(stack) < 20) {
                    if (PlayerDataUtils.getCredits((ServerPlayer) player) >= stack.getTag().getInt("LevelCost")) {
                        ((LevelableItem) stack.getItem()).levelUp(stack);
                        PlayerDataUtils.subCredits((ServerPlayer) player, stack.getTag().getInt("LevelCost"));
                        ((LevelableItem) stack.getItem()).setLevelUpCost(stack, 50 * ((LevelableItem) stack.getItem()).getLevel(stack));
                    } else {
                        player.sendSystemMessage(Component.literal("You need " + (stack.getTag().getInt("LevelCost") - PlayerDataUtils.getCredits((ServerPlayer) player) + " more Credits")));
                    }
                } else {
                    player.sendSystemMessage(Component.literal("Item is Max Level"));
                }
            }
        }
    }
}
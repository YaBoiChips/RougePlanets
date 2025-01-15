package yaboichips.rouge_planets.common.entities.forgemaster;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import yaboichips.rouge_planets.capabilties.player.PlayerDataUtils;
import yaboichips.rouge_planets.common.items.LevelableItem;
import yaboichips.rouge_planets.core.RPMenus;

public class ForgeMasterMenu extends AbstractContainerMenu {

    private Container container;

    public Player player;

    private final SimpleContainer levelSlot = new SimpleContainer(1);

    public ForgeMasterMenu(int id, Inventory playerInventory, Container container) {
        super(RPMenus.FORGE_MASTER_MENU.get(), id);
        checkContainerSize(container, 13);
        checkContainerSize(levelSlot, 1);
        this.container = container;
        this.player = playerInventory.player;

        this.addSlot(new Slot(levelSlot, 0, 80, 26));

        for (int i = 0; i < 13; i++) {
            this.addSlot(new Slot(container, i, 8 + (i % 9) * 18, 84 + (i / 9) * 18));
        }
    }

    public ForgeMasterMenu(int id, Inventory playerInventory) {
        super(RPMenus.FORGE_MASTER_MENU.get(), id);
    }

    public ForgeMasterMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(i, inventory, new SimpleContainer(13));
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
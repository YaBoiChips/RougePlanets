package yaboichips.rouge_planets.common.entities.forgemaster;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import yaboichips.rouge_planets.core.RPMenus;

public class ForgeMasterMenu extends AbstractContainerMenu {
    private final ItemStackHandler itemHandler = new ItemStackHandler(1);

    public ForgeMasterMenu(int id, Inventory playerInventory, Player player) {
        super(RPMenus.FORGE_MASTER_MENU.get(), id);
//        for (int i = 0; i < 13; i++) {
//            this.addSlot(new Slot(((PlayerData)player).getPlanetContainer(), i, 8 + (i % 9) * 18, 18 + (i / 9) * 18));
//        }

        // Add player inventory slots
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

    }

    public ForgeMasterMenu(int id, Inventory playerInventory) {
        super(RPMenus.FORGE_MASTER_MENU.get(), id);
    }

    public ForgeMasterMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(i, inventory);
    }

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public void levelUpItem() {
//        ItemStack stack = itemHandler.getStackInSlot(0);
//        if (!stack.isEmpty() && stack.getItem() instanceof LevelableItem) {
//            ((LevelableItem) stack.getItem()).levelUp(stack);
//        }
    }
}

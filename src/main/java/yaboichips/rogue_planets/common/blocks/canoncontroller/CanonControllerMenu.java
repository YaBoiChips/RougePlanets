package yaboichips.rogue_planets.common.blocks.canoncontroller;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import yaboichips.rogue_planets.core.RPMenus;

public class CanonControllerMenu extends AbstractContainerMenu {

    private BlockPos pos;
    public CanonControllerMenu(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
        this(id, playerInventory, buffer.readBlockPos());
    }
    public CanonControllerMenu(int id, Inventory playerInventory, BlockPos pos) {
        super(RPMenus.CANON_CONTROLLER.get(), id);
        this.pos = pos;
        addPlayerInventory(playerInventory);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            ItemStack originalStack = stack.copy();

            if (index < 36) { // Move from player inventory to block slots
                if (!this.moveItemStackTo(stack, 36, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 0, 36, false)) { // Move from block slots to player inventory
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == originalStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }


    // Utility to add player inventory slots
    private void addPlayerInventory(Inventory playerInventory) {
        int startX = 8;
        int startY = 84;
        int slotSizePlus2 = 18;

        // Player inventory (3 rows of 9 slots)
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, startX + col * slotSizePlus2, startY + row * slotSizePlus2));
            }
        }

        // Hotbar (1 row of 9 slots)
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, startX + col * slotSizePlus2, startY + 58));
        }
    }

    public BlockPos getEntityPos(){
        return pos;
    }
}


package yaboichips.rogue_planets.common.entities.workers.augmentor;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import yaboichips.rogue_planets.capabilties.RogueCapabilities;
import yaboichips.rogue_planets.common.items.augments.AugmentItem;
import yaboichips.rogue_planets.core.RPMenus;

public class AugmentorMenu extends AbstractContainerMenu {
    private SimpleContainer container;

    public Player player;
    private final SimpleContainer augmentableSlot = new SimpleContainer(1);

    public AugmentorMenu(int id, Inventory playerInventory, SimpleContainer container, SimpleContainer armor) {
        super(RPMenus.AUGMENTOR_MENU.get(), id);
        checkContainerSize(container, 36);
        checkContainerSize(armor, 4);
        checkContainerSize(augmentableSlot, 1);
        this.container = container;
        this.player = playerInventory.player;

        this.addSlot(new Slot(augmentableSlot, 0, 120, 44) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getCapability(RogueCapabilities.AUGMENTABLE).isPresent();
            }

            @Override
            public boolean mayPickup(Player p_40228_) {
                return false;
            }

            @Override
            public void setChanged() {
                super.setChanged();
                onSlotChanged(this);
            }
        });

        int k;
        for (k = 0; k < 3; ++k) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 48 + j * 18, 142 + k * 18));
            }
        }

        int slotID = 0;

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(container, slotID, 48 + i * 18, 232));
            slotID++;
        }
        for (int i = 0; i < 4; i++) {
            this.addSlot(new Slot(armor, i, 48 + i * 18, 214));
            slotID++;
        }
    }

    public AugmentorMenu(int id, Inventory playerInventory) {
        super(RPMenus.AUGMENTOR_MENU.get(), id);
    }

    public AugmentorMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(i, inventory, new SimpleContainer(36), new SimpleContainer(4));
    }

    public void onClose() {
        ItemStack stack = augmentableSlot.getItem(0);
        container.addItem(stack);
    }
    @Override
    public void clicked(int slot, int p_150401_, ClickType clickType, Player p_150403_) {
        if (slot >= 0) {
            super.clicked(slot, p_150401_, clickType, p_150403_);
        }
    }


    private void onSlotChanged(Slot slot) {
        if (slot.getItem().getCapability(RogueCapabilities.AUGMENTABLE).isPresent()) {
            slot.getItem().getCapability(RogueCapabilities.AUGMENTABLE).ifPresent(cap -> {
                for (int i = 0; i < cap.getAugmentSlots(); i++) {
//                            checkContainerSize(cap.getAugments(), cap.getAugmentSlots());
                    Slot dynamicSlot = new Slot(cap.getAugments(), i, 102 + i * 18, 94) {
                        @Override
                        public boolean mayPlace(ItemStack stack) {
                            return stack.getItem() instanceof AugmentItem;
                        }
                    };
                    this.addSlot(dynamicSlot);
                }
            });
        }
    }

    public void applyAugment() {
        ItemStack stack = augmentableSlot.getItem(0);
        container.addItem(stack);
    }


    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }
}
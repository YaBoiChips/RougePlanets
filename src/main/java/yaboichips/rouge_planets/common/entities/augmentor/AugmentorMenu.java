package yaboichips.rouge_planets.common.entities.augmentor;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import yaboichips.rouge_planets.capabilties.RougeCapabilities;
import yaboichips.rouge_planets.common.items.SlotableItem;
import yaboichips.rouge_planets.common.items.augments.AugmentItem;
import yaboichips.rouge_planets.core.RPMenus;

public class AugmentorMenu extends AbstractContainerMenu {
    private Container container;

    public Player player;
    private final SimpleContainer augmentableSlot = new SimpleContainer(1);

    public AugmentorMenu(int id, Inventory playerInventory, Container container) {
        super(RPMenus.AUGMENTOR_MENU.get(), id);
        checkContainerSize(container, 13);
        checkContainerSize(augmentableSlot, 1);
        this.container = container;
        this.player = playerInventory.player;

        this.addSlot(new Slot(augmentableSlot, 0, 80, 26) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.getCapability(RougeCapabilities.AUGMENTABLE).isPresent();
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

    public AugmentorMenu(int id, Inventory playerInventory) {
        super(RPMenus.AUGMENTOR_MENU.get(), id);
    }

    public AugmentorMenu(int i, Inventory inventory, FriendlyByteBuf friendlyByteBuf) {
        this(i, inventory, new SimpleContainer(13));
    }

    public void onClose(){
        ItemStack stack = augmentableSlot.getItem(0);
        container.setItem(((SlotableItem)stack.getItem()).getSlot(), stack);
    }


    private void onSlotChanged(Slot slot) {
        if (slot.getItem().getCapability(RougeCapabilities.AUGMENTABLE).isPresent()) {
            slot.getItem().getCapability(RougeCapabilities.AUGMENTABLE).ifPresent(cap -> {
                for (int i = 0; i < cap.getAugmentSlots(); i++) {
//                            checkContainerSize(cap.getAugments(), cap.getAugmentSlots());
                    Slot dynamicSlot = new Slot(cap.getAugments(), i, 88 + i * 18, 140) {
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

    public void applyAugment(){
        ItemStack stack = augmentableSlot.getItem(0);
        if (stack.getItem() instanceof SlotableItem item){
            container.setItem(item.getSlot(), stack);
        }
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
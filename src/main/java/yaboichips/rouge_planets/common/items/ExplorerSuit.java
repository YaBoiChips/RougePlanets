package yaboichips.rouge_planets.common.items;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;
import yaboichips.rouge_planets.capabilties.RougeCapabilities;
import yaboichips.rouge_planets.capabilties.ArmorData;

import java.util.List;

public class ExplorerSuit extends ArmorItem implements LevelableItem {


    public ExplorerSuit(ArmorMaterial p_40386_, Type p_266831_, Properties p_40388_) {
        super(p_40386_, p_266831_, p_40388_);
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ArmorCapabilityProvider();
    }

    public void levelUp(ItemStack stack) {
        setLevel(stack, getLevel(stack) + 1);
        CompoundTag tag = stack.getTag();
        tag.putInt("Level", getLevel(stack));
        stack.setTag(tag);
    }

    @Override
    public int getDamage(ItemStack stack) {
        return super.getDamage(stack);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return (int) Math.round(super.getMaxDamage(stack) * Math.pow(1 + 0.08, getLevel(stack)));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        if (stack.getItem() instanceof LevelableItem) {
            components.add(Component.literal("Level " + stack.getTag().getInt("Level")));
        }
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }

    @Override
    public InteractionResult useOn(net.minecraft.world.item.context.UseOnContext context) {
        ItemStack itemStack = context.getItemInHand();
        itemStack.getCapability(RougeCapabilities.PADDING).ifPresent(cap -> {
            cap.setActivated(!cap.isActivated());
            context.getPlayer().sendSystemMessage(Component.literal("Double Armor Capability Activated!"));
        });
        return InteractionResult.SUCCESS;
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        itemStack.getCapability(RougeCapabilities.PADDING).ifPresent(cap -> {
            if (cap.isActivated()) {
                entity.sendSystemMessage(Component.literal("Wowie Zowie"));
            }
        });
        super.inventoryTick(itemStack, level, entity, p_41407_, p_41408_);
    }


    public static class ArmorCapabilityProvider implements ICapabilityProvider {
        private final ArmorData.ArmorCapability padding = new ArmorData.ArmorCapability();
        private final LazyOptional<ArmorData.ArmorCapability> lazyCapability = LazyOptional.of(() -> padding);

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return cap == RougeCapabilities.PADDING ? lazyCapability.cast() : LazyOptional.empty();
        }

        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();
            padding.serializeNBT(tag);
            return tag;
        }

        public void deserializeNBT(CompoundTag tag) {
            padding.deserializeNBT(tag);
        }
    }


}

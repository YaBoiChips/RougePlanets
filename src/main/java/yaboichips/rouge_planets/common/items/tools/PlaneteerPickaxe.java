package yaboichips.rouge_planets.common.items.tools;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import yaboichips.rouge_planets.common.items.LevelableItem;
import yaboichips.rouge_planets.common.items.SlotableItem;

import java.util.List;

public class PlaneteerPickaxe extends PickaxeItem implements LevelableItem, SlotableItem {
    public PlaneteerPickaxe() {
        super(Tiers.IRON, 1, -2.8F, new Properties());
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return Math.round(super.getDestroySpeed(stack, state) * Math.pow(1 + 0.07, getLevel(stack)));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return (int) Math.round((50 * Math.pow(1 + 0.07, getLevel(stack))));
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> components, TooltipFlag p_41424_) {
        if (stack.getItem() instanceof LevelableItem) {
            components.add(Component.literal("Level " + stack.getTag().getInt("Level")));
        }
        super.appendHoverText(stack, p_41422_, components, p_41424_);
    }
    public void levelUp(ItemStack stack) {
        setLevel(stack, getLevel(stack) + 1);
        CompoundTag tag = stack.getTag();
        tag.putInt("Level", getLevel(stack));
        stack.setTag(tag);
    }

    @Override
    public int getSlot() {
        return 0;
    }
}
package yaboichips.rogue_planets.common.items.tools;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;
import yaboichips.rogue_planets.capabilties.RogueCapabilities;
import yaboichips.rogue_planets.capabilties.augments.AugCapProvider;
import yaboichips.rogue_planets.common.items.LevelableItem;

import java.util.List;

public class PlaneteerPickaxe extends PickaxeItem implements LevelableItem {
    public PlaneteerPickaxe() {
        super(Tiers.IRON, 1, -2.8F, new Properties());
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new AugCapProvider();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.getMainHandItem().getDamageValue()< 1) {
            return InteractionResultHolder.fail(player.getMainHandItem());
        }
        return super.use(level, player, hand);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
            return 0.0F;
        }
        return Math.round(super.getDestroySpeed(stack, state) * Math.pow(1 + 0.1, getLevel(stack)));
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return (int) Math.round((50 * Math.pow(1 + 0.1, getLevel(stack))));
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return stack.getDamageValue() < stack.getMaxDamage() - 1 && super.isCorrectToolForDrops(stack, state);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.getDamageValue() >= stack.getMaxDamage() - 1) {
            return false;
        }
        return super.hurtEnemy(stack, target, attacker);
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
        stack.getCapability(RogueCapabilities.AUGMENTABLE).ifPresent(capability -> {
            if (getLevel(stack) == 5) {
                capability.setAugmentSlots(1);
            } else if (getLevel(stack) == 10) {
                capability.setAugmentSlots(2);
            } else if (getLevel(stack) == 15) {
                capability.setAugmentSlots(3);
            }
        });
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        if (stack.getDamageValue() <= 1){
            return false;
        }
        return super.canPerformAction(stack, toolAction);
    }
}

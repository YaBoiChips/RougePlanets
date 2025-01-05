package yaboichips.rouge_planets.common.items;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface LevelableItem {

    default int getLevel(ItemStack stack) {
        if (stack.getItem() instanceof LevelableItem) {
            return stack.getOrCreateTag().getInt("Level");
        }
        return 0;
    }

    default void setLevel(ItemStack stack, int i) {
        if (stack.getItem() instanceof LevelableItem) {
            stack.getOrCreateTag().putInt("Level", i);
        }
    }

    default void levelUp(ItemStack stack) {
        setLevel(stack, getLevel(stack) + 1);
    }

    default int getLevelUpCost(ItemStack stack){
        if (stack.getItem() instanceof LevelableItem) {
            return stack.getOrCreateTag().getInt("LevelCost");
        }
        return 0;
    }
    default void setLevelUpCost(ItemStack stack, int i) {
        if (stack.getItem() instanceof LevelableItem) {
            stack.getOrCreateTag().putInt("LevelCost", i);
        }
    }
}

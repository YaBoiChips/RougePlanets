package yaboichips.rogue_planets.common.items.crystals;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import yaboichips.rogue_planets.capabilties.RogueCapabilities;

public class Azurium extends Item {

    public Azurium() {
        super(new Properties());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.getCapability(RogueCapabilities.PLAYER_DATA).ifPresent(playerData -> {
            playerData.setAzuriumTimer(30 * 20);
            playerData.addO2(30 * 20);
        });
        player.getCooldowns().addCooldown(stack.getItem(), 30);
        stack.shrink(1);
        return InteractionResultHolder.consume(stack);

    }
}


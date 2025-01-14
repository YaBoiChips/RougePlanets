package yaboichips.rouge_planets.common.items.crystals;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import yaboichips.rouge_planets.capabilties.RougeCapabilities;

public class Chlorosynth extends Item {

    public Chlorosynth() {
        super(new Properties());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.getCapability(RougeCapabilities.PLAYER_DATA).ifPresent(playerData -> {
            playerData.setChlorosynthActive(true);
            playerData.setChlorosynthTimer(30 * 20);
        });
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 60 * 20, 1));
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 30 * 20, 3));
        player.heal(20);

        level.addParticle(ParticleTypes.ELECTRIC_SPARK, player.getX(), player.getY(), player.getZ(), 0, 0, 0);
        level.playSound(player, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS);
        player.getCooldowns().addCooldown(stack.getItem(), 30 * 20);
        return InteractionResultHolder.consume(stack);

    }
}


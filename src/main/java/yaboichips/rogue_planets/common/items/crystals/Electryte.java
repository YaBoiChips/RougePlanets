package yaboichips.rogue_planets.common.items.crystals;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import yaboichips.rogue_planets.capabilties.RogueCapabilities;

import java.util.List;

public class Electryte extends Item {

    private static final double MAX_RADIUS = 10.0; // Maximum radius of the flame circle
    private static final int PARTICLE_COUNT = 150; // Number of particles to spawn each time
    private static final int STEPS = 30;

    public Electryte() {
        super(new Properties());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.getCapability(RogueCapabilities.PLAYER_DATA).ifPresent(playerData -> {
            playerData.setElectryteTimer(30 * 20);
        });
        for (int step = 1; step <= STEPS; step++) {
            double radius = (MAX_RADIUS / STEPS) * step;
            sendZapParticles(level, player, radius);
            applyZapAndDamageToEntities(level, player, radius);
        }
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 30 * 20, 2));
        player.getCooldowns().addCooldown(stack.getItem(), 30 * 20);
        stack.shrink(1);
        return InteractionResultHolder.consume(stack);

    }


    private void sendZapParticles(Level world, Entity user, double radius) {
        Vec3 userPos = user.position();

        for (int i = 0; i < PARTICLE_COUNT; i++) {
            double angle = Math.toRadians(i * (360.0 / PARTICLE_COUNT));
            double xOffset = radius * Math.cos(angle);
            double zOffset = radius * Math.sin(angle);
            double yOffset = 0;

            world.addParticle(ParticleTypes.ELECTRIC_SPARK, userPos.x() + xOffset, userPos.y() + yOffset, userPos.z() + zOffset, 0, 0, 0);
        }
    }


    private void applyZapAndDamageToEntities(Level world, Entity user, double radius) {
        List<Entity> nearbyEntities = world.getEntities(user, user.getBoundingBox().inflate(radius), e -> e instanceof LivingEntity);

        for (Entity entity : nearbyEntities) {
            if (entity instanceof LivingEntity livingEntity) {
                double distance = user.position().distanceTo(entity.position());
                double damage = 20.0 - (distance / 2.0 * 2);
                damage = Math.max(damage, 0);
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 140, 40, false, false));
                livingEntity.hurt(livingEntity.damageSources().magic(), (float) damage);
            }
        }
    }
}


package yaboichips.rouge_planets.common.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import yaboichips.rouge_planets.RougePlanets;

import static yaboichips.rouge_planets.RougePlanets.clearTask;

public class RopeBlockItem extends BlockItem {
    public RopeBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.FAIL;
        }
        BlockPos clickedPos = context.getClickedPos();
        scheduleRopePlacement(level, clickedPos.below(), player);
        return InteractionResult.CONSUME;
    }

    private void scheduleRopePlacement(Level serverLevel, BlockPos startPos, Player player) {
        RougePlanets.scheduleTask(10, () -> {
            placeRope(serverLevel, startPos, player);
        });
    }

    private void placeRope(Level level, BlockPos currentPos, Player player) {
        if (!level.isEmptyBlock(currentPos)) {
            clearTask();
            return;
        }
        ItemStack ropeStack = findRopeInInventory(player);
        if (ropeStack == ItemStack.EMPTY) {
            clearTask();
            return;
        }
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.setBlock(currentPos, this.getBlock().defaultBlockState(), 3);
            serverLevel.playSound(null, currentPos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1, 1);
        } else {
            level.addParticle(ParticleTypes.POOF, currentPos.getX(), currentPos.getY(), currentPos.getZ(), 0, 0, 0);
        }
        if (!player.isCreative()) {
            ropeStack.shrink(1);
        }
        scheduleRopePlacement(level, currentPos.below(), player);
    }

    private ItemStack findRopeInInventory(Player player) {
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(this)) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}

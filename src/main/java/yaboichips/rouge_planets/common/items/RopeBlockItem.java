package yaboichips.rouge_planets.common.items;

import net.minecraft.core.BlockPos;
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

public class RopeBlockItem extends BlockItem {
    public RopeBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.SUCCESS;
        }
        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.FAIL;
        }

        BlockPos clickedPos = context.getClickedPos();

        scheduleRopePlacement(serverLevel, clickedPos.below(), player);
        return InteractionResult.CONSUME;
    }

    private void scheduleRopePlacement(ServerLevel serverLevel, BlockPos startPos, Player player) {
        serverLevel.getServer().execute(() -> placeRopeRecursively(serverLevel, startPos, player));
    }

    private void placeRopeRecursively(ServerLevel serverLevel, BlockPos currentPos, Player player) {
        if (!serverLevel.isEmptyBlock(currentPos)) {
            return; // Stop if the block below is not air
        }

        ItemStack ropeStack = findRopeInInventory(player);
        if (ropeStack == ItemStack.EMPTY) {
            return; // Stop if the player has no rope
        }

        // Place the rope block
        serverLevel.setBlock(currentPos, this.getBlock().defaultBlockState(), 3);
        serverLevel.playSound(null, currentPos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1, 1);
        if (!player.isCreative()) {
            ropeStack.shrink(1);
        }

        // Schedule the next placement after 0.5 seconds
        serverLevel.getServer().execute(() -> serverLevel.scheduleTick(currentPos.below(), this.getBlock(), 500));
        placeRopeRecursively(serverLevel, currentPos.below(), player);
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

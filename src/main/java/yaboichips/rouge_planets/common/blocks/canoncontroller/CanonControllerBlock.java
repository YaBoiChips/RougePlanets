package yaboichips.rouge_planets.common.blocks.canoncontroller;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import yaboichips.rouge_planets.core.RPBlockEntities;

public class CanonControllerBlock extends DirectionalBlock implements EntityBlock {
    public CanonControllerBlock() {
        super(Properties.copy(Blocks.IRON_BLOCK));
    }

    public static final DirectionProperty FACING = BlockStateProperties.FACING;

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_54481_) {
        return this.defaultBlockState().setValue(FACING, p_54481_.getHorizontalDirection().getOpposite());
    }
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_54562_, BlockPos p_54563_, CollisionContext p_54564_) {
        return switch (state.getValue(FACING)) {
            case NORTH -> makeShapeNorth();
            case EAST -> makeShapeEast();
            case WEST -> makeShapeWest();
            default -> makeShapeSouth();
        };
    }

    @Nullable
    @Override
    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((p_48785_, p_48786_, p_48787_) -> new CanonControllerMenu(p_48785_, p_48786_), Component.literal("idk"));
    }
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand p_60507_, BlockHitResult p_60508_) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(level, pos));
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public void onRemove(BlockState p_60515_, Level level, BlockPos pos, BlockState p_60518_, boolean p_60519_) {
        ((CanonControllerBE) level.getBlockEntity(pos)).getLinkedCanon().remove(Entity.RemovalReason.DISCARDED);
        super.onRemove(p_60515_, level, pos, p_60518_, p_60519_);
    }

    public VoxelShape makeShapeSouth() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.25, 0.9375, 0.0625, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.0625, 0.4375, 0.625, 0.8125, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0.125, 1, 0.6875, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.8125, 0.515625, 0.625, 0.875, 0.625), BooleanOp.OR);
        return shape;
    }

    public VoxelShape makeShapeEast() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.0625, 0.75, 0.0625, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.0625, 0.375, 0.625, 0.8125, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.625, 0, 0.9375, 0.6875, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.515625, 0.8125, 0.375, 0.625, 0.875, 0.625), BooleanOp.OR);

        return shape;
    }

    public VoxelShape makeShapeWest() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.25, 0, 0.0625, 0.75, 0.0625, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.0625, 0.375, 0.5625, 0.8125, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.625, 0, 0.875, 0.6875, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.8125, 0.375, 0.484375, 0.875, 0.625), BooleanOp.OR);

        return shape;
    }

    public VoxelShape makeShapeNorth() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.25, 0.9375, 0.0625, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.0625, 0.375, 0.625, 0.8125, 0.5625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0.0625, 1, 0.6875, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.8125, 0.375, 0.625, 0.875, 0.484375), BooleanOp.OR);

        return shape;
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return RPBlockEntities.CANON_CONTROLLER_BE.get().create(pos, state);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54543_) {
        p_54543_.add(FACING);
    }
}
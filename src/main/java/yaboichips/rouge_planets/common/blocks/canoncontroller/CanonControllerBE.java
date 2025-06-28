package yaboichips.rouge_planets.common.blocks.canoncontroller;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import yaboichips.rouge_planets.common.entities.workers.canon.CanonEntity;
import yaboichips.rouge_planets.core.RPBlockEntities;
import yaboichips.rouge_planets.core.RPEntities;

import java.util.UUID;

import static yaboichips.rouge_planets.common.blocks.canoncontroller.CanonControllerBlock.FACING;

public class CanonControllerBE extends BlockEntity {
    private UUID linkedCanonUUID;
    public CanonControllerBE(BlockPos p_155229_, BlockState p_155230_) {
        super(RPBlockEntities.CANON_CONTROLLER_BE.get(), p_155229_, p_155230_);
    }

    public void setLinkedCanon(CanonEntity canon) {
        this.linkedCanonUUID = canon.getUUID();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (!level.isClientSide) {
            if (linkedCanonUUID == null) {
                spawnCanonEntity();
            }
        }
    }
    private void spawnCanonEntity() {
        if (level != null && !level.isClientSide) {
            CanonEntity canon = RPEntities.CANON.get().create(level);
            if (canon != null) {
                canon.setPos(this.getBlockPos().getCenter().relative(this.getBlockState().getValue(FACING), -1).relative(Direction.DOWN, 0.5));
                level.addFreshEntity(canon);
                setLinkedCanon(canon);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        if (linkedCanonUUID != null) {
            tag.putUUID("LinkedCanonUUID", linkedCanonUUID);
        }
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.hasUUID("LinkedCanonUUID")) {
            linkedCanonUUID = tag.getUUID("LinkedCanonUUID");
        }
        super.load(tag);
    }

    public CanonEntity getLinkedCanon() {
        if (linkedCanonUUID != null && level instanceof ServerLevel serverLevel) {
            Entity entity = serverLevel.getEntity(linkedCanonUUID);
            if (entity instanceof CanonEntity) {
                return (CanonEntity) entity;
            }
        }
        return null;
    }
}
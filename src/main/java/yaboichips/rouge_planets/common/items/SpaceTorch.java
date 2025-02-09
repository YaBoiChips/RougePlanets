package yaboichips.rouge_planets.common.items;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

public class SpaceTorch extends TorchBlock {
    public SpaceTorch() {
        super(BlockBehaviour.Properties.of().noCollission().instabreak().lightLevel((light) -> 14).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY), ParticleTypes.FLAME);
    }
}

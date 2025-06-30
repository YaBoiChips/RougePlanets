package yaboichips.rouge_planets.common.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class BeegStoneSpike extends Feature<NoneFeatureConfiguration> {
    public BeegStoneSpike(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        LevelAccessor level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        BlockPos surface = origin;
        while (level.getBlockState(surface).isAir() && surface.getY() > 60) {
            surface = surface.below();
        }

        if (!level.getBlockState(surface).isSolid()) return false;
        surface = surface.above();

        int height = Mth.nextInt(random, 24, 42);
        int baseRadius = Mth.nextInt(random, 5, 9);
        double angle = Math.toRadians(random.nextInt(360));
        double lean = 0.2 + random.nextDouble() * 0.35;

        double dx = Math.cos(angle) * lean;
        double dz = Math.sin(angle) * lean;

        double x = surface.getX();
        double y = surface.getY();
        double z = surface.getZ();

        for (int xOffset = -baseRadius; xOffset <= baseRadius; xOffset++) {
            for (int zOffset = -baseRadius; zOffset <= baseRadius; zOffset++) {
                if (xOffset * xOffset + zOffset * zOffset <= baseRadius * baseRadius) {
                    BlockPos below = surface.offset(xOffset, -1, zOffset);
                    if (!level.getBlockState(below).is(Blocks.STONE)) {
                        return false;
                    }
                }
            }
        }
        for (int i = 0; i < height; i++) {
            int radius = baseRadius - (i * baseRadius / height);
            for (int xOffset = -radius; xOffset <= radius; xOffset++) {
                for (int zOffset = -radius; zOffset <= radius; zOffset++) {
                    if (xOffset * xOffset + zOffset * zOffset <= radius * radius) {
                        BlockPos pos = BlockPos.containing(x + xOffset, y + i, z + zOffset);
                        if (level.getBlockState(pos).isAir() && level.isAreaLoaded(pos, 4)) {
                            if (random.nextFloat() < 0.7f) {
                                level.setBlock(pos, Blocks.STONE.defaultBlockState(), 2);
                            } else {
                                level.setBlock(pos, Blocks.COBBLESTONE.defaultBlockState(), 2);
                            }
                        }
                    }
                }
            }
            x += dx;
            z += dz;
        }
        return true;
    }
}
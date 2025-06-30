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

public class AngledArchFeature extends Feature<NoneFeatureConfiguration> {
    public AngledArchFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        LevelAccessor level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        // Random arch parameters
        int span = Mth.nextInt(random, 6, 19);           // distance between feet
        int height = Mth.nextInt(random, 14, 20);         // height of the arch
        int thickness = Mth.nextInt(random, 3, 6);       // how thick the arch is

        double angle = Math.toRadians(random.nextInt(360)); // direction of arch
        double dx = Math.cos(angle);
        double dz = Math.sin(angle);

        // Get left and right base positions
        BlockPos leftBase = findGround(level, origin.offset((int)(-dx * span / 2), 0, (int)(-dz * span / 2)));
        BlockPos rightBase = findGround(level, origin.offset((int)(dx * span / 2), 0, (int)(dz * span / 2)));

        if (leftBase == null || rightBase == null) return false;

        // Generate points between the two bases
        for (int i = 0; i <= span; i++) {
            double t = (double) i / span;

            // Interpolated position along the base line
            double x = Mth.lerp(t, leftBase.getX(), rightBase.getX());
            double z = Mth.lerp(t, leftBase.getZ(), rightBase.getZ());

            // Arch shape using sine
            double archY = Mth.lerp(t, leftBase.getY(), rightBase.getY()) + Math.sin(t * Math.PI) * height;

            // Thickness block fill
            for (int ox = -thickness; ox <= thickness; ox++) {
                for (int oy = -thickness; oy <= thickness; oy++) {
                    for (int oz = -thickness; oz <= thickness; oz++) {
                        if (ox * ox + oy * oy + oz * oz <= thickness * thickness) {
                            BlockPos pos = BlockPos.containing(x + ox, archY + oy, z + oz);

                            // Chunk bounds safety check
                            if (isTooFar(pos, origin)) continue;

                            if (level.getBlockState(pos).isAir()) {
                                level.setBlock(pos,
                                        random.nextFloat() < 0.7f
                                                ? Blocks.STONE.defaultBlockState()
                                                : Blocks.COBBLESTONE.defaultBlockState(),
                                        2
                                );
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    private BlockPos findGround(LevelAccessor level, BlockPos pos) {
        BlockPos check = pos.mutable();
        while (check.getY() > 60 && level.getBlockState(check).isAir()) {
            check = check.below();
        }
        return level.getBlockState(check).isSolid() ? check.above() : null;
    }

    private boolean isTooFar(BlockPos target, BlockPos origin) {
        int cx = target.getX() >> 4;
        int cz = target.getZ() >> 4;
        int ox = origin.getX() >> 4;
        int oz = origin.getZ() >> 4;
        return Math.abs(cx - ox) > 1 || Math.abs(cz - oz) > 1;
    }
}
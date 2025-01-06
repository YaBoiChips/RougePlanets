package yaboichips.rouge_planets.core;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yaboichips.rouge_planets.common.blocks.RopeBlock;
import yaboichips.rouge_planets.common.items.SpaceTorch;

import static yaboichips.rouge_planets.RougePlanets.MODID;

public class RPBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final RegistryObject<Block> ROPE = BLOCKS.register("rope", () -> new RopeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).sound(SoundType.WOOL).noCollission()));
    public static final RegistryObject<Block> SPACE_TORCH = BLOCKS.register("space_torch", SpaceTorch::new);


//    public static final RegistryObject<Block> TELEPORTER_BLOCK = BLOCKS.register("teleporter_block", () -> new TeleporterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
//    public static final RegistryObject<Block> CANON_CONTROLLER = BLOCKS.register("canon_controller", CanonControllerBlock::new);


}

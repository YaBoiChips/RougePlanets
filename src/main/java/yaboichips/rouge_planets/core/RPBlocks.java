package yaboichips.rouge_planets.core;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static yaboichips.rouge_planets.RougePlanets.MODID;

public class RPBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

//    public static final RegistryObject<Block> TELEPORTER_BLOCK = BLOCKS.register("teleporter_block", () -> new TeleporterBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
//    public static final RegistryObject<Block> CANON_CONTROLLER = BLOCKS.register("canon_controller", CanonControllerBlock::new);


}

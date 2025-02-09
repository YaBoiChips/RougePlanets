package yaboichips.rouge_planets.core;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yaboichips.rouge_planets.common.blocks.RopeBlock;
import yaboichips.rouge_planets.common.blocks.TeleporterBlock;
import yaboichips.rouge_planets.common.blocks.canoncontroller.CanonControllerBlock;
import yaboichips.rouge_planets.common.items.SpaceTorch;

import static yaboichips.rouge_planets.RougePlanets.MODID;

public class RPBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final RegistryObject<Block> ROPE = BLOCKS.register("rope", () -> new RopeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).sound(SoundType.WOOL).noCollission()));
    public static final RegistryObject<Block> SPACE_TORCH = BLOCKS.register("space_torch", SpaceTorch::new);
    public static final RegistryObject<Block> AZURIUM_BLOCK = BLOCKS.register("azurium_block", () -> new Block(BlockBehaviour.Properties.of().lightLevel((light) -> 6).noOcclusion()));
    public static final RegistryObject<Block> PYROLITH_BLOCK = BLOCKS.register("pyrolith_block", () -> new Block(BlockBehaviour.Properties.of().lightLevel((light) -> 6).noOcclusion()));
    public static final RegistryObject<Block> ELECTRYTE_BLOCK = BLOCKS.register("electryte_block", () -> new Block(BlockBehaviour.Properties.of().lightLevel((light) -> 6).noOcclusion()));
    public static final RegistryObject<Block> CHLOROSYNTH_BLOCK = BLOCKS.register("chlorosynth_block", () -> new Block(BlockBehaviour.Properties.of().lightLevel((light) -> 6).noOcclusion()));
    public static final RegistryObject<Block> TP = BLOCKS.register("tp", () -> new TeleporterBlock(BlockBehaviour.Properties.of().lightLevel((light) -> 6).noOcclusion()));
    public static final RegistryObject<Block> CANON_CONTROLLER = BLOCKS.register("canon_controller", CanonControllerBlock::new);


    public static final RegistryObject<Block> DEEPSLATE_RUBY_ORE = BLOCKS.register("deepslate_ruby_ore", RPBlocks::createDeepslateOreBlock);
    public static final RegistryObject<Block> DEEPSLATE_SAPPHIRE_ORE = BLOCKS.register("deepslate_sapphire_ore", RPBlocks::createDeepslateOreBlock);
    public static final RegistryObject<Block> DEEPSLATE_TOPAZ_ORE = BLOCKS.register("deepslate_topaz_ore", RPBlocks::createDeepslateOreBlock);
    public static final RegistryObject<Block> DEEPSLATE_OPAL_ORE = BLOCKS.register("deepslate_opal_ore", RPBlocks::createDeepslateOreBlock);
    public static final RegistryObject<Block> DEEPSLATE_AMBER_ORE = BLOCKS.register("deepslate_amber_ore", RPBlocks::createDeepslateOreBlock);
    public static final RegistryObject<Block> DEEPSLATE_ONYX_ORE = BLOCKS.register("deepslate_onyx_ore", RPBlocks::createDeepslateOreBlock);
    public static final RegistryObject<Block> DEEPSLATE_PYRITE_ORE = BLOCKS.register("deepslate_pyrite_ore", RPBlocks::createDeepslateOreBlock);
    public static final RegistryObject<Block> DEEPSLATE_THALLIUM_ORE = BLOCKS.register("deepslate_thallium_ore", RPBlocks::createDeepslateOreBlock);

    public static final RegistryObject<Block> RUBY_ORE = BLOCKS.register("ruby_ore", RPBlocks::createOreBlock);
    public static final RegistryObject<Block> SAPPHIRE_ORE = BLOCKS.register("sapphire_ore", RPBlocks::createOreBlock);
    public static final RegistryObject<Block> TOPAZ_ORE = BLOCKS.register("topaz_ore", RPBlocks::createOreBlock);
    public static final RegistryObject<Block> OPAL_ORE = BLOCKS.register("opal_ore", RPBlocks::createOreBlock);
    public static final RegistryObject<Block> AMBER_ORE = BLOCKS.register("amber_ore", RPBlocks::createOreBlock);
    public static final RegistryObject<Block> ONYX_ORE = BLOCKS.register("onyx_ore", RPBlocks::createOreBlock);
    public static final RegistryObject<Block> PYRITE_ORE = BLOCKS.register("pyrite_ore", RPBlocks::createOreBlock);
    public static final RegistryObject<Block> THALLIUM_ORE = BLOCKS.register("thallium_ore", RPBlocks::createOreBlock);
//    public static final RegistryObject<Block> CANON_CONTROLLER = BLOCKS.register("canon_controller", CanonControllerBlock::new);

    public static Block createDeepslateOreBlock() {
        return new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_COAL_ORE));
    }

    public static Block createOreBlock() {
        return new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE));
    }
}

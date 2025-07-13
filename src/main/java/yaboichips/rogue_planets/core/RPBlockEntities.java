package yaboichips.rogue_planets.core;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yaboichips.rogue_planets.common.blocks.canoncontroller.CanonControllerBE;

import static yaboichips.rogue_planets.RoguePlanets.MODID;

public class RPBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);

    public static final RegistryObject<BlockEntityType<CanonControllerBE>> CANON_CONTROLLER_BE = BLOCK_ENTITY_TYPES.register("canon_controller",
            () -> BlockEntityType.Builder.of(CanonControllerBE::new, RPBlocks.CANON_CONTROLLER.get()).build(null));

}

package yaboichips.rouge_planets.common.entities.forgemaster;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import yaboichips.rouge_planets.PlayerData;
import yaboichips.rouge_planets.common.entities.HumanMob;
import yaboichips.rouge_planets.common.items.LevelableItem;
import yaboichips.rouge_planets.core.RPItems;

import static yaboichips.rouge_planets.RougePlanets.MODID;

public class ForgeMaster extends HumanMob {
    public ForgeMaster(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.FOLLOW_RANGE, 35.0D).add(Attributes.MOVEMENT_SPEED, 0.01F).add(Attributes.ARMOR, 2.0D);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!player.level().isClientSide) {
            if (!((PlayerData)player).getIsInitiated()){
                player.sendSystemMessage(Component.literal("Initiated"));
                ItemStack stack = RPItems.TESTARMOR.get().getDefaultInstance();
                ((PlayerData)player).setIsInitiated(true);
                if (stack.getItem() instanceof LevelableItem item) {
                    ((PlayerData) player).getPlanetContainer().addItem(RPItems.TESTARMOR.get().getDefaultInstance());
                }
            }
            else if (hand == InteractionHand.MAIN_HAND) {
                player.openMenu(new SimpleMenuProvider((id, playerInv, container) -> new ForgeMasterMenu(id, playerInv, ((PlayerData)player).getPlanetContainer()), Component.literal("Forge Master")));
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public ResourceLocation getTextureLocation() {
        return ResourceLocation.fromNamespaceAndPath(MODID, "textures/entity/foreman.png");
    }
}
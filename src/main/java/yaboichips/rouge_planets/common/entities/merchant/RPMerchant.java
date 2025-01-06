package yaboichips.rouge_planets.common.entities.merchant;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import yaboichips.rouge_planets.capabilties.player.PlayerDataUtils;
import yaboichips.rouge_planets.common.entities.HumanMob;

import static yaboichips.rouge_planets.RougePlanets.MODID;

public class RPMerchant extends HumanMob {
    public RPMerchant(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }


    @Override
    protected InteractionResult mobInteract(Player p, InteractionHand hand) {
        if (!p.level().isClientSide) {
            if (p instanceof ServerPlayer player) {
                if (!PlayerDataUtils.getIsInitiated(player)) {
                    player.sendSystemMessage(Component.literal("Who are you? I only sell to qualified personal!"));
                } else if (hand == InteractionHand.MAIN_HAND) {
                    player.openMenu(new SimpleMenuProvider((id, playerInv, container) -> new RPMerchantMenu(id, playerInv, PlayerDataUtils.getPlanetContainer(player)), Component.literal("Merchant")));
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public ResourceLocation getTextureLocation() {
        return ResourceLocation.fromNamespaceAndPath(MODID, "textures/entity/merchant.png");
    }
}
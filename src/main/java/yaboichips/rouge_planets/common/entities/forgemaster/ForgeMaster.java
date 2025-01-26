package yaboichips.rouge_planets.common.entities.forgemaster;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import yaboichips.rouge_planets.capabilties.player.PlayerDataUtils;
import yaboichips.rouge_planets.common.containers.PlanetArmorContainer;
import yaboichips.rouge_planets.common.containers.PlanetInventoryContainer;
import yaboichips.rouge_planets.common.entities.HumanMob;
import yaboichips.rouge_planets.common.items.SlotableItem;
import yaboichips.rouge_planets.core.RPItems;

import java.util.ArrayList;
import java.util.List;

import static yaboichips.rouge_planets.RougePlanets.MODID;

public class ForgeMaster extends HumanMob {
    public ForgeMaster(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }


    @Override
    protected InteractionResult mobInteract(Player p, InteractionHand hand) {
        if (!p.level().isClientSide) {
            if (p instanceof ServerPlayer player) {
                if (!PlayerDataUtils.getIsInitiated(player)) {
                    player.sendSystemMessage(Component.literal("Initiated"));
                    PlayerDataUtils.setPlanetContainer(player, new PlanetInventoryContainer());
                    PlayerDataUtils.setArmorContainer(player, new PlanetArmorContainer());
                    PlayerDataUtils.setInitiated(player, true);
                    defaultItems().forEach(item -> PlayerDataUtils.getPlanetContainer(player).setItem(((SlotableItem) item).getSlot(), item.getDefaultInstance()));
                    armor().forEach(item -> PlayerDataUtils.getArmorContainer(player).setItem(((SlotableItem) item).getSlot(), item.getDefaultInstance()));
                } else if (hand == InteractionHand.MAIN_HAND) {
                    player.openMenu(new SimpleMenuProvider((id, playerInv, container) -> new ForgeMasterMenu(id, playerInv, PlayerDataUtils.getPlanetContainer(player), PlayerDataUtils.getArmorContainer(player)), Component.literal("Forge Master")));
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    private List<Item> defaultItems() {
        List<Item> items = new ArrayList<>();
        items.add(RPItems.PLANETEER_PICKAXE.get());
        return items;
    }
    private List<Item> armor() {
        List<Item> items = new ArrayList<>();
        items.add(RPItems.TESTARMOR.get());
        return items;
    }

    @Override
    public ResourceLocation getTextureLocation() {
        return ResourceLocation.fromNamespaceAndPath(MODID, "textures/entity/foreman.png");
    }
}
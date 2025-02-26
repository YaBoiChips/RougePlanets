package yaboichips.rouge_planets.common.entities.ceo;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import yaboichips.rouge_planets.capabilties.player.PlayerDataUtils;
import yaboichips.rouge_planets.common.containers.PlanetArmorContainer;
import yaboichips.rouge_planets.common.containers.PlanetInventoryContainer;
import yaboichips.rouge_planets.common.entities.HumanMob;
import yaboichips.rouge_planets.common.entities.forgemaster.ForgeMasterMenu;
import yaboichips.rouge_planets.core.RPItems;

import java.util.ArrayList;
import java.util.List;

import static yaboichips.rouge_planets.RougePlanets.MODID;

public class CEO extends HumanMob {
    public CEO(EntityType<? extends Mob> p_21368_, Level p_21369_) {
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
                    defaultItems().forEach(item -> PlayerDataUtils.getPlanetContainer(player).addItem(item));
                    armor().forEach(item -> PlayerDataUtils.getArmorContainer(player).addItem(item));
                } else if (hand == InteractionHand.MAIN_HAND) {
                    player.openMenu(new SimpleMenuProvider((id, playerInv, container) -> new CEOMenu(id, playerInv, PlayerDataUtils.getPlanetContainer(player), PlayerDataUtils.getArmorContainer(player)), Component.literal("Forge Master")));
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    private List<ItemStack> defaultItems() {
        List<ItemStack> items = new ArrayList<>();
        items.add(RPItems.PLANETEER_PICKAXE.get().getDefaultInstance());
        ItemStack rope = RPItems.ROPE.get().getDefaultInstance();
        rope.setCount(12);
        items.add(rope);
        ItemStack torch = RPItems.SPACE_TORCH.get().getDefaultInstance();
        torch.setCount(32);
        items.add(torch);
        return items;
    }
    private List<ItemStack> armor() {
        List<ItemStack> items = new ArrayList<>();
        items.add(RPItems.PLANETEER_HELMET.get().getDefaultInstance());
        items.add(RPItems.PLANETEER_CHESTPLATE.get().getDefaultInstance());
        items.add(RPItems.PLANETEER_LEGGINGS.get().getDefaultInstance());
        items.add(RPItems.PLANETEER_BOOTS.get().getDefaultInstance());
        return items;
    }
    @Override
    public ResourceLocation getTextureLocation() {
        return ResourceLocation.fromNamespaceAndPath(MODID, "textures/entity/ceo.png");
    }
}
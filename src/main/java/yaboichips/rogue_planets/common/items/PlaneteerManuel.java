package yaboichips.rogue_planets.common.items;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import yaboichips.rogue_planets.capabilties.player.PlayerDataUtils;
import yaboichips.rogue_planets.network.OpenPlaneteerGUIPacket;
import yaboichips.rogue_planets.network.RoguePackets;

public class PlaneteerManuel extends Item {

    public PlaneteerManuel() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41434_) {
        if (player instanceof ServerPlayer sPlayer) {
            int credits = PlayerDataUtils.getCredits(sPlayer);
            RoguePackets.CHANNEL.send(PacketDistributor.PLAYER.with(() -> sPlayer), new OpenPlaneteerGUIPacket(credits));
        }
        return super.use(level, player, p_41434_);
    }
}

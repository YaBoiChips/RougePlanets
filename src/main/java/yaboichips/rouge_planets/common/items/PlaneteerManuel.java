package yaboichips.rouge_planets.common.items;

import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import yaboichips.rouge_planets.PlayerData;
import yaboichips.rouge_planets.client.screens.PlaneteerInfoScreen;
import yaboichips.rouge_planets.network.OpenPlaneteerGUIPacket;
import yaboichips.rouge_planets.network.RougePackets;

public class PlaneteerManuel extends Item {

    public PlaneteerManuel() {
        super(new Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41434_) {
        if (player instanceof ServerPlayer sPlayer) {
            int credits = ((PlayerData) sPlayer).getCredits();
//            int xp = ((PlayerData) sPlayer).getPlaneteerXP();
//            int pLevel = ((PlayerData) sPlayer).getPlaneteerLevel();
            RougePackets.CHANNEL.send(PacketDistributor.PLAYER.with(() -> sPlayer), new OpenPlaneteerGUIPacket(credits));
        }
        return super.use(level, player, p_41434_);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int p_41407_, boolean p_41408_) {
        if (entity instanceof ServerPlayer player) {
            if (player.getMainHandItem() == stack){
                ((PlayerData)player).addO2(1);
            }
        }
        super.inventoryTick(stack, level, entity, p_41407_, p_41408_);
    }
}

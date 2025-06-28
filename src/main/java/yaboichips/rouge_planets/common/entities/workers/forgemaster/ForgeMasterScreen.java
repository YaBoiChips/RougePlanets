package yaboichips.rouge_planets.common.entities.workers.forgemaster;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import yaboichips.rouge_planets.capabilties.player.ClientPlayerData;
import yaboichips.rouge_planets.network.LevelUpItemPacket;
import yaboichips.rouge_planets.network.RougePackets;

import static yaboichips.rouge_planets.RougePlanets.MODID;

public class ForgeMasterScreen extends AbstractContainerScreen<ForgeMasterMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/gui/forge_master.png");
    private final ForgeMasterMenu menu;

    public ForgeMasterScreen(ForgeMasterMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 256;
        this.imageHeight = 256;
        this.menu = menu;
    }

    @Override
    protected void init() {
        super.init();
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.addRenderableWidget(Button.builder(Component.literal("Level Up"), button -> {
            RougePackets.CHANNEL.sendToServer(new LevelUpItemPacket());
        }).tooltip(Tooltip.create(Component.literal("Cost to upgrade, Level * 50₡"))).bounds(x + 96 , y + 70, 60, 20).build());
    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        guiGraphics.blit(TEXTURE, (this.width - this.imageWidth) / 2, (this.height - this.imageHeight) / 2, 0, 0, this.imageWidth, this.imageHeight);
    }


    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, Component.literal(ClientPlayerData.getCredits() + "₡"), this.titleLabelX + 215, this.titleLabelY, 4210752, false);
    }

    @Override
    public void render(GuiGraphics poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }
}

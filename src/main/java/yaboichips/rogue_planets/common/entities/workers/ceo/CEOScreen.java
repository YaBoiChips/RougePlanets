package yaboichips.rogue_planets.common.entities.workers.ceo;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import yaboichips.rogue_planets.network.LayOffPacket;
import yaboichips.rogue_planets.network.RoguePackets;

import static yaboichips.rogue_planets.RoguePlanets.MODID;

public class CEOScreen extends AbstractContainerScreen<CEOMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/gui/forge_master.png");
    private final CEOMenu menu;

    public CEOScreen(CEOMenu menu, Inventory inventory, Component title) {
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
        this.addRenderableWidget(Button.builder(Component.literal("Lay Off"), button -> {
            RoguePackets.CHANNEL.sendToServer(new LayOffPacket());
            this.onClose();
        }).bounds(x + 96 , y + 70, 60, 20).build());
    }
    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        guiGraphics.blit(TEXTURE, (this.width - this.imageWidth) / 2, (this.height - this.imageHeight) / 2, 0, 0, this.imageWidth, this.imageHeight);
    }
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY + 58, 4210752, false);
    }
    @Override
    public void render(GuiGraphics poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }
}

package yaboichips.rouge_planets.common.entities.workers.augmentor;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import yaboichips.rouge_planets.network.ApplyAugmentPacket;
import yaboichips.rouge_planets.network.RougePackets;
import yaboichips.rouge_planets.network.SendItemsToSlotPacket;

import static yaboichips.rouge_planets.RougePlanets.MODID;

public class AugmentorScreen extends AbstractContainerScreen<AugmentorMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/gui/augmentor.png");

    public AugmentorScreen(AugmentorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 256;
        this.imageHeight = 256;
    }

    @Override
    protected void init() {
        super.init();
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.addRenderableWidget(Button.builder(Component.literal("Apply Augment"), button -> {
            RougePackets.CHANNEL.sendToServer(new ApplyAugmentPacket());
            this.minecraft.player.closeContainer();
        }).bounds(x + 82, y + 66, 90, 20).build());
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        guiGraphics.blit(TEXTURE, (this.width - this.imageWidth) / 2, (this.height - this.imageHeight) / 2, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void onClose() {
        RougePackets.CHANNEL.sendToServer(new SendItemsToSlotPacket());
        super.onClose();
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }

    @Override
    public void render(GuiGraphics poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }
}
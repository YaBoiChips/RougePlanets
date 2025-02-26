package yaboichips.rouge_planets.common.blocks.canoncontroller;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import yaboichips.rouge_planets.network.RideCanonPacket;
import yaboichips.rouge_planets.network.RougePackets;

import static yaboichips.rouge_planets.RougePlanets.MODID;


public class CanonControllerScreen extends AbstractContainerScreen<CanonControllerMenu> {
    private static final ResourceLocation BACKGROUND_TEXTURE = ResourceLocation.fromNamespaceAndPath(MODID, "textures/gui/canon_controller.png");

    public CanonControllerScreen(CanonControllerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
    @Override
    protected void renderLabels(GuiGraphics poseStack, int mouseX, int mouseY) {
        super.renderLabels(poseStack, mouseX, mouseY);
        poseStack.drawString(minecraft.font, "Canon Controller", 8, 6, 4210752);
    }

    @Override
    protected void init() {
        super.init();
        addRenderableWidget(Button.builder(Component.literal("Ride Canon"), button -> {
            RougePackets.sendToServer(new RideCanonPacket(menu.getEntityPos()));
        }).pos(leftPos + 10, topPos + 20).size(100, 20).build());
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        renderBackground(guiGraphics);
        minecraft.getTextureManager().bindForSetup(BACKGROUND_TEXTURE);
    }
}
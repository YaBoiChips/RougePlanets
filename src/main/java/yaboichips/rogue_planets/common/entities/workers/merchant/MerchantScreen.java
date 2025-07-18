package yaboichips.rogue_planets.common.entities.workers.merchant;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import yaboichips.rogue_planets.capabilties.player.ClientPlayerData;
import yaboichips.rogue_planets.network.BuyItemPacket;
import yaboichips.rogue_planets.network.RoguePackets;
import yaboichips.rogue_planets.network.SellItemPacket;

import static yaboichips.rogue_planets.RoguePlanets.MODID;

public class MerchantScreen extends AbstractContainerScreen<RPMerchantMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/gui/merchant.png");

    private String credits;
    public MerchantScreen(RPMerchantMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 256;
        this.imageHeight = 256;
    }

    @Override
    protected void init() {
        super.init();
        this.clearWidgets();
        Button sellButton = Button.builder(Component.literal("Buy Item"), btn -> RoguePackets.CHANNEL.sendToServer(new SellItemPacket())).pos(leftPos + 143, topPos + 76).size(50,20).build();
        this.addRenderableWidget(sellButton);
        for (int i = 0; i < MerchantSales.SALES.size(); i++) {
            MerchantSales sale = MerchantSales.SALES.get(i);

            int x = this.leftPos + 6; // Position relative to the screen
            int y = this.topPos + 6 + i * 20; // Stack buttons vertically

            Button button = new CustomItemButton(x, y, 76, 20, sale.item(), sale.price(), btn -> RoguePackets.CHANNEL.sendToServer(new BuyItemPacket(sale.item(), sale.price())));

            this.addRenderableWidget(button);
        }
    }
    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);

    }
    @Override
    public void render(GuiGraphics poseStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(poseStack);
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(poseStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(this.font, Component.literal(ClientPlayerData.getCredits() + "₡"), this.titleLabelX + 215, this.titleLabelY, 4210752, false);
    }

    private class CustomItemButton extends Button {
        private final ItemStack itemStack;
        private final int price;

        public CustomItemButton(int x, int y, int width, int height, ItemStack itemStack, int price, OnPress onPress) {
            super(x, y, width, height, Component.empty(), onPress, DEFAULT_NARRATION);
            this.itemStack = itemStack;
            this.price = price;
        }

        @Override
        public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
            // Draw button background
            super.renderWidget(graphics, mouseX, mouseY, partialTicks);

            // Render the item icon
            int iconX = this.getX() + 5;
            int iconY = this.getY() + (this.height - 16) / 2;
            graphics.renderItem(itemStack, iconX, iconY);
            graphics.renderItemDecorations(Minecraft.getInstance().font, itemStack, iconX, iconY);

            // Render the price as text
            int priceX = this.getX() + 45;
            int priceY = this.getY() + (this.height - 8) / 2;
            graphics.drawString(Minecraft.getInstance().font, price + "₡", priceX, priceY, 0xFFFFFF);
        }
    }
}

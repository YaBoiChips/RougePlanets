package yaboichips.rouge_planets.client.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class PlaneteerInfoScreen extends Screen {
    // A list to hold integers and their positions
    private final int credits; // Reference to the player
    private final List<IntRenderData> intDataList = new ArrayList<>();

    public PlaneteerInfoScreen(int credits) {
        super(Component.literal("Planeteer Manuel"));
        this.credits = credits;

    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        // Draw the background
        this.renderBackground(guiGraphics);

        addInt("Credits", credits, 10, 10);

        // Render all integers
        for (IntRenderData data : intDataList) {
            if (data.description == null) {
                guiGraphics.drawString(this.font, String.valueOf(data.value), data.x, data.y, 0xFFFFFF, false);
            } else {
                guiGraphics.drawString(this.font, data.description + ": " + data.value, data.x, data.y, 0xFFFFFF, false);

            }
        }
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    /**
     * Adds an integer to be rendered on the screen at the specified position.
     *
     * @param value The integer value to display.
     * @param x     The x-coordinate to render the integer.
     * @param y     The y-coordinate to render the integer.
     */
    public void addInt(int value, int x, int y) {
        this.intDataList.add(new IntRenderData(null, value, x, y));
    }
    public void addInt(String description, int value, int x, int y) {
        this.intDataList.add(new IntRenderData(description, value, x, y));
    }

    // Class to hold integer and position data
        private record IntRenderData(String description, int value, int x, int y) {
    }
}


package matejko06.mathax.gui.themes.mathax.widgets.pressable;

import matejko06.mathax.gui.renderer.GuiRenderer;
import matejko06.mathax.gui.themes.mathax.MatHaxWidget;
import matejko06.mathax.gui.widgets.pressable.WTriangle;

public class WMatHaxTriangle extends WTriangle implements MatHaxWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.rotatedQuad(x, y, width, height, rotation, GuiRenderer.TRIANGLE, theme().backgroundColor.get(pressed, mouseOver));
    }
}

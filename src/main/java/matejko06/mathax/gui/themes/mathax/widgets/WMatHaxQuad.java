package matejko06.mathax.gui.themes.mathax.widgets;

import matejko06.mathax.gui.renderer.GuiRenderer;
import matejko06.mathax.gui.widgets.WQuad;
import matejko06.mathax.utils.render.color.Color;

public class WMatHaxQuad extends WQuad {
    public WMatHaxQuad(Color color) {
        super(color);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.quad(x, y, width, height, color);
    }
}

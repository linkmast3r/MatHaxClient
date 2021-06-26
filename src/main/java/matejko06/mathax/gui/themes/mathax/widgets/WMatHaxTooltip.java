package matejko06.mathax.gui.themes.mathax.widgets;

import matejko06.mathax.gui.renderer.GuiRenderer;
import matejko06.mathax.gui.themes.mathax.MatHaxWidget;
import matejko06.mathax.gui.widgets.WTooltip;

public class WMatHaxTooltip extends WTooltip implements MatHaxWidget {
    public WMatHaxTooltip(String text) {
        super(text);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.quad(this, theme().backgroundColor.get());
    }
}

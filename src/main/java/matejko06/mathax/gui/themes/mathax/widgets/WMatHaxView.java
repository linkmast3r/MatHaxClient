package matejko06.mathax.gui.themes.mathax.widgets;

import matejko06.mathax.gui.renderer.GuiRenderer;
import matejko06.mathax.gui.themes.mathax.MatHaxWidget;
import matejko06.mathax.gui.widgets.containers.WView;

public class WMatHaxView extends WView implements MatHaxWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (canScroll && hasScrollBar) {
            renderer.quad(handleX(), handleY(), handleWidth(), handleHeight(), theme().scrollbarColor.get(handlePressed, handleMouseOver));
        }
    }
}

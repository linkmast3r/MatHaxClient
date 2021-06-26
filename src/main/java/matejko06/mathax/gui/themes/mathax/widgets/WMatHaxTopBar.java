package matejko06.mathax.gui.themes.mathax.widgets;

import matejko06.mathax.gui.themes.mathax.MatHaxWidget;
import matejko06.mathax.gui.widgets.WTopBar;
import matejko06.mathax.utils.render.color.Color;

public class WMatHaxTopBar extends WTopBar implements MatHaxWidget {
    @Override
    protected Color getButtonColor(boolean pressed, boolean hovered) {
        return theme().backgroundColor.get(pressed, hovered);
    }

    @Override
    protected Color getNameColor() {
        return theme().textColor.get();
    }
}

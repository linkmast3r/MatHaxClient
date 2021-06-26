package matejko06.mathax.gui.utils;

import matejko06.mathax.gui.GuiTheme;
import matejko06.mathax.gui.WidgetScreen;

public interface IScreenFactory {
    WidgetScreen createScreen(GuiTheme theme);
}

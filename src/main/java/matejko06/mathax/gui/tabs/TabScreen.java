package matejko06.mathax.gui.tabs;

import matejko06.mathax.gui.GuiTheme;
import matejko06.mathax.gui.WidgetScreen;
import matejko06.mathax.gui.utils.Cell;
import matejko06.mathax.gui.widgets.WWidget;

public class TabScreen extends WidgetScreen {
    public final Tab tab;

    public TabScreen(GuiTheme theme, Tab tab) {
        super(theme, tab.name);
        this.tab = tab;
    }

    public <T extends WWidget> Cell<T> addDirect(T widget) {
        return super.add(widget);
    }
}

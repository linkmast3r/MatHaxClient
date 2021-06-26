package matejko06.mathax.gui.tabs;

import matejko06.mathax.gui.GuiTheme;
import matejko06.mathax.gui.utils.Cell;
import matejko06.mathax.gui.widgets.WWidget;
import matejko06.mathax.gui.widgets.containers.WWindow;

public class WindowTabScreen extends TabScreen {
    private final WWindow window;

    public WindowTabScreen(GuiTheme theme, Tab tab) {
        super(theme, tab);

        window = super.add(theme.window(tab.name)).center().widget();
    }

    @Override
    public <W extends WWidget> Cell<W> add(W widget) {
        return window.add(widget);
    }

    @Override
    public void clear() {
        window.clear();
    }
}

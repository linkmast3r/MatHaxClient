package mathax.client.gui.tabs;

import mathax.client.gui.GuiTheme;
import mathax.client.gui.utils.Cell;
import mathax.client.gui.widgets.WWidget;
import mathax.client.gui.widgets.containers.WWindow;

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

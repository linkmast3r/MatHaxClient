package matejko06.mathax.gui;

import matejko06.mathax.gui.utils.Cell;
import matejko06.mathax.gui.widgets.WWidget;
import matejko06.mathax.gui.widgets.containers.WWindow;

public abstract class WindowScreen extends WidgetScreen {
    private final WWindow window;

    public WindowScreen(GuiTheme theme, String title) {
        super(theme, title);

        window = super.add(theme.window(title)).center().widget();
        window.view.scrollOnlyWhenMouseOver = false;
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

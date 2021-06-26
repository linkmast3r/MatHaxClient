package matejko06.mathax.gui.utils;

import matejko06.mathax.gui.GuiTheme;
import matejko06.mathax.gui.widgets.WWidget;
import matejko06.mathax.settings.Settings;

public interface SettingsWidgetFactory {
    WWidget create(GuiTheme theme, Settings settings, String filter);
}

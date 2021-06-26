package matejko06.mathax.gui.screens.settings;

import matejko06.mathax.gui.GuiTheme;
import matejko06.mathax.gui.widgets.WWidget;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.systems.modules.Modules;

import java.util.List;

public class ModuleListSettingScreen extends LeftRightListSettingScreen<Module> {
    public ModuleListSettingScreen(GuiTheme theme, Setting<List<Module>> setting) {
        super(theme, "Select Modules", setting, setting.get(), Modules.REGISTRY);
    }

    @Override
    protected WWidget getValueWidget(Module value) {
        return theme.label(getValueName(value));
    }

    @Override
    protected String getValueName(Module value) {
        return value.title;
    }
}

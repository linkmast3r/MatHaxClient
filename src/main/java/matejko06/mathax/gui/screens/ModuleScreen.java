package matejko06.mathax.gui.screens;

import matejko06.mathax.events.mathax.ModuleBindChangedEvent;
import matejko06.mathax.gui.GuiTheme;
import matejko06.mathax.gui.WindowScreen;
import matejko06.mathax.gui.utils.Cell;
import matejko06.mathax.gui.widgets.WKeybind;
import matejko06.mathax.gui.widgets.WWidget;
import matejko06.mathax.gui.widgets.containers.WContainer;
import matejko06.mathax.gui.widgets.containers.WHorizontalList;
import matejko06.mathax.gui.widgets.containers.WSection;
import matejko06.mathax.gui.widgets.pressable.WCheckbox;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.systems.modules.Modules;
import matejko06.mathax.utils.Utils;
import matejko06.mathax.bus.EventHandler;

import static matejko06.mathax.utils.Utils.getWindowWidth;

public class ModuleScreen extends WindowScreen {
    private final Module module;

    private final WContainer settings;
    private final WKeybind keybind;

    public ModuleScreen(GuiTheme theme, Module module) {
        super(theme, module.title);
        this.module = module;

        // Description
        add(theme.label(module.description, getWindowWidth() / 2.0));

        // Settings
        settings = add(theme.verticalList()).expandX().widget();
        if (module.settings.groups.size() > 0) {
            settings.add(theme.settings(module.settings)).expandX();
        }

        // Custom widget
        WWidget widget = module.getWidget(theme);

        if (widget != null) {
            Cell<WWidget> cell = add(widget);
            if (widget instanceof WContainer) cell.expandX();
        }

        // Bind
        WSection section = add(theme.section("Bind", true)).expandX().widget();
        keybind = section.add(theme.keybind(module.keybind)).expandX().widget();
        keybind.actionOnSet = () -> Modules.get().setModuleToBind(module);

        // Toggle on bind release
        WHorizontalList tobr = section.add(theme.horizontalList()).widget();

        tobr.add(theme.label("Toggle on bind release: "));
        WCheckbox tobrC = tobr.add(theme.checkbox(module.toggleOnBindRelease)).widget();
        tobrC.action = () -> module.toggleOnBindRelease = tobrC.checked;

        add(theme.horizontalSeparator()).expandX();

        // Bottom
        WHorizontalList bottom = add(theme.horizontalList()).expandX().widget();

        //   Active
        bottom.add(theme.label("Active: "));
        WCheckbox active = bottom.add(theme.checkbox(module.isActive())).expandCellX().widget();
        active.action = () -> {
            if (module.isActive() != active.checked) module.toggle(Utils.canUpdate());
        };

        //   Visible
        bottom.add(theme.label("Visible: "));
        WCheckbox visible = bottom.add(theme.checkbox(module.isVisible())).widget();
        visible.action = () -> {
            if (module.isVisible() != visible.checked) module.setVisible(visible.checked);
        };
    }

    @Override
    public void tick() {
        super.tick();

        module.settings.tick(settings, theme);
    }

    @EventHandler
    private void onModuleBindChanged(ModuleBindChangedEvent event) {
        keybind.reset();
    }
}

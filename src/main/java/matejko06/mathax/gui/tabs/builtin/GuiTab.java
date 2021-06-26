package matejko06.mathax.gui.tabs.builtin;

import matejko06.mathax.gui.GuiTheme;
import matejko06.mathax.gui.GuiThemes;
import matejko06.mathax.gui.tabs.Tab;
import matejko06.mathax.gui.tabs.TabScreen;
import matejko06.mathax.gui.tabs.WindowTabScreen;
import matejko06.mathax.gui.widgets.containers.WTable;
import matejko06.mathax.gui.widgets.input.WDropdown;
import net.minecraft.client.gui.screen.Screen;

import static matejko06.mathax.utils.Utils.mc;

public class GuiTab extends Tab {
    public GuiTab() {
        super("GUI");
    }

    @Override
    public TabScreen createScreen(GuiTheme theme) {
        return new GuiScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof GuiScreen;
    }

    private static class GuiScreen extends WindowTabScreen {
        public GuiScreen(GuiTheme theme, Tab tab) {
            super(theme, tab);

            WTable table = add(theme.table()).expandX().widget();

            table.add(theme.label("Theme:"));
            WDropdown<String> themeW = table.add(theme.dropdown(GuiThemes.getNames(), GuiThemes.get().name)).widget();
            themeW.action = () -> {
                GuiThemes.select(themeW.get());

                mc.openScreen(null);
                tab.openScreen(GuiThemes.get());
            };

            theme.settings.onActivated();
            add(theme.settings(theme.settings)).expandX();
        }
    }
}

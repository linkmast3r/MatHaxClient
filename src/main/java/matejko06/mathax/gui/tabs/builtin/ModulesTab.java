package matejko06.mathax.gui.tabs.builtin;

import matejko06.mathax.gui.GuiTheme;
import matejko06.mathax.gui.GuiThemes;
import matejko06.mathax.gui.tabs.Tab;
import matejko06.mathax.gui.tabs.TabScreen;
import net.minecraft.client.gui.screen.Screen;

public class ModulesTab extends Tab {
    public ModulesTab() {
        super("Modules");
    }

    @Override
    public TabScreen createScreen(GuiTheme theme) {
        return theme.modulesScreen();
    }

    @Override
    public boolean isScreen(Screen screen) {
        return GuiThemes.get().isModulesScreen(screen);
    }
}

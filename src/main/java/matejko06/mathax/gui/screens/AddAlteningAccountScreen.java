package matejko06.mathax.gui.screens;

import matejko06.mathax.gui.GuiTheme;
import matejko06.mathax.gui.WindowScreen;
import matejko06.mathax.gui.widgets.containers.WTable;
import matejko06.mathax.gui.widgets.input.WTextBox;
import matejko06.mathax.gui.widgets.pressable.WButton;
import matejko06.mathax.systems.accounts.types.TheAlteningAccount;

public class AddAlteningAccountScreen extends WindowScreen {
    public AddAlteningAccountScreen(GuiTheme theme) {
        super(theme, "Add The Altening Account");

        WTable t = add(theme.table()).widget();

        // Token
        t.add(theme.label("Token: "));
        WTextBox token = t.add(theme.textBox("")).minWidth(400).expandX().widget();
        token.setFocused(true);
        t.row();

        // Add
        WButton add = t.add(theme.button("Add")).expandX().widget();
        add.action = () -> {
            if (!token.get().isEmpty()) {
                AccountsScreen.addAccount(add, this, new TheAlteningAccount(token.get()));
            }
        };

        enterAction = add.action;
    }
}

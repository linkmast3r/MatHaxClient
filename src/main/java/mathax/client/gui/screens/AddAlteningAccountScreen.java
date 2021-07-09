package mathax.client.gui.screens;

import mathax.client.gui.GuiTheme;
import mathax.client.gui.WindowScreen;
import mathax.client.gui.widgets.containers.WTable;
import mathax.client.gui.widgets.input.WTextBox;
import mathax.client.gui.widgets.pressable.WButton;
import mathax.client.systems.accounts.types.TheAlteningAccount;

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

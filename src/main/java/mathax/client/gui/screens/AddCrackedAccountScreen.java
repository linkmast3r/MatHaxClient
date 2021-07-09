package mathax.client.gui.screens;

import mathax.client.gui.GuiTheme;
import mathax.client.gui.WindowScreen;
import mathax.client.gui.widgets.containers.WTable;
import mathax.client.gui.widgets.input.WTextBox;
import mathax.client.gui.widgets.pressable.WButton;
import mathax.client.systems.accounts.Accounts;
import mathax.client.systems.accounts.types.CrackedAccount;

public class AddCrackedAccountScreen extends WindowScreen {
    public AddCrackedAccountScreen(GuiTheme theme) {
        super(theme, "Add Cracked Account");

        WTable t = add(theme.table()).widget();

        // Name
        t.add(theme.label("Name: "));
        WTextBox name = t.add(theme.textBox("")).minWidth(400).expandX().widget();
        name.setFocused(true);
        t.row();

        // Add
        WButton add = t.add(theme.button("Add")).expandX().widget();
        add.action = () -> {
            CrackedAccount account = new CrackedAccount(name.get());
            if (!name.get().trim().isEmpty() && !(Accounts.get().exists(account))) {
                AccountsScreen.addAccount(add, this, account);
            }
        };

        enterAction = add.action;
    }
}

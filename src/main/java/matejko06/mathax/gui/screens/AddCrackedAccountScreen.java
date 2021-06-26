package matejko06.mathax.gui.screens;

import matejko06.mathax.gui.GuiTheme;
import matejko06.mathax.gui.WindowScreen;
import matejko06.mathax.gui.widgets.containers.WTable;
import matejko06.mathax.gui.widgets.input.WTextBox;
import matejko06.mathax.gui.widgets.pressable.WButton;
import matejko06.mathax.systems.accounts.Accounts;
import matejko06.mathax.systems.accounts.types.CrackedAccount;

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

package matejko06.mathax.gui.screens;

import matejko06.mathax.gui.GuiTheme;
import matejko06.mathax.gui.WidgetScreen;
import matejko06.mathax.gui.WindowScreen;
import matejko06.mathax.gui.widgets.WAccount;
import matejko06.mathax.gui.widgets.containers.WContainer;
import matejko06.mathax.gui.widgets.containers.WHorizontalList;
import matejko06.mathax.gui.widgets.pressable.WButton;
import matejko06.mathax.systems.accounts.Account;
import matejko06.mathax.systems.accounts.Accounts;
import matejko06.mathax.utils.network.MatHaxExecutor;

import static matejko06.mathax.utils.Utils.mc;

public class AccountsScreen extends WindowScreen {
    public AccountsScreen(GuiTheme theme) {
        super(theme, "Accounts");
    }

    @Override
    protected void init() {
        super.init();

        clear();
        initWidgets();
    }

    private void initWidgets() {
        // Accounts
        for (Account<?> account : Accounts.get()) {
            WAccount wAccount = add(theme.account(this, account)).expandX().widget();
            wAccount.refreshScreenAction = () -> {
                clear();
                initWidgets();
            };
        }

        // Add account
        WHorizontalList l = add(theme.horizontalList()).expandX().widget();

        addButton(l, "Cracked", () -> mc.openScreen(new AddCrackedAccountScreen(theme)));
        addButton(l, "Premium", () -> mc.openScreen(new AddPremiumAccountScreen(theme)));
        addButton(l, "The Altening", () -> mc.openScreen(new AddAlteningAccountScreen(theme)));
    }

    private void addButton(WContainer c, String text, Runnable action) {
        WButton button = c.add(theme.button(text)).expandX().widget();
        button.action = action;
    }

    public static void addAccount(WButton add, WidgetScreen screen, Account<?> account) {
        add.set("...");
        screen.locked = true;

        MatHaxExecutor.execute(() -> {
            if (account.fetchInfo() && account.fetchHead()) {
                Accounts.get().add(account);
                screen.locked = false;
                screen.onClose();
            }

            add.set("Add");
            screen.locked = false;
        });
    }
}

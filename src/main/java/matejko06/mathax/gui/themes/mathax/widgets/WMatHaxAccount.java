package matejko06.mathax.gui.themes.mathax.widgets;

import matejko06.mathax.gui.WidgetScreen;
import matejko06.mathax.gui.themes.mathax.MatHaxWidget;
import matejko06.mathax.gui.widgets.WAccount;
import matejko06.mathax.systems.accounts.Account;
import matejko06.mathax.utils.render.color.Color;

public class WMatHaxAccount extends WAccount implements MatHaxWidget {
    public WMatHaxAccount(WidgetScreen screen, Account<?> account) {
        super(screen, account);
    }

    @Override
    protected Color loggedInColor() {
        return theme().loggedInColor.get();
    }

    @Override
    protected Color accountTypeColor() {
        return theme().textSecondaryColor.get();
    }
}

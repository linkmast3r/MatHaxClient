package mathax.client.gui.screens.settings;

import mathax.client.gui.GuiTheme;
import mathax.client.gui.WindowScreen;
import mathax.client.gui.widgets.containers.WTable;
import mathax.client.gui.widgets.pressable.WButton;
import mathax.client.settings.PotionSetting;
import mathax.client.utils.misc.MyPotion;

public class PotionSettingScreen extends WindowScreen {
    public PotionSettingScreen(GuiTheme theme, PotionSetting setting) {
        super(theme, "Select Potion");

        WTable table = add(theme.table()).expandX().widget();

        for (MyPotion potion : MyPotion.values()) {
            table.add(theme.itemWithLabel(potion.potion, potion.potion.getName().getString()));

            WButton select = table.add(theme.button("Select")).widget();
            select.action = () -> {
                setting.set(potion);
                onClose();
            };

            table.row();
        }
    }
}

package matejko06.mathax.gui.screens.settings;

import matejko06.mathax.gui.GuiTheme;
import matejko06.mathax.gui.WindowScreen;
import matejko06.mathax.gui.widgets.containers.WTable;
import matejko06.mathax.gui.widgets.pressable.WButton;
import matejko06.mathax.settings.PotionSetting;
import matejko06.mathax.utils.misc.MyPotion;

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

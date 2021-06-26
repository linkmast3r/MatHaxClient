package matejko06.mathax.gui.tabs.builtin;

import matejko06.mathax.gui.GuiTheme;
import matejko06.mathax.gui.tabs.Tab;
import matejko06.mathax.gui.tabs.TabScreen;
import matejko06.mathax.gui.tabs.WindowTabScreen;
import matejko06.mathax.gui.widgets.containers.WHorizontalList;
import matejko06.mathax.gui.widgets.containers.WSection;
import matejko06.mathax.gui.widgets.containers.WTable;
import matejko06.mathax.gui.widgets.input.WTextBox;
import matejko06.mathax.gui.widgets.pressable.WMinus;
import matejko06.mathax.gui.widgets.pressable.WPlus;
import matejko06.mathax.settings.BoolSetting;
import matejko06.mathax.settings.ColorSetting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.settings.Settings;
import matejko06.mathax.systems.friends.Friend;
import matejko06.mathax.systems.friends.Friends;
import matejko06.mathax.utils.render.color.SettingColor;
import net.minecraft.client.gui.screen.Screen;

public class FriendsTab extends Tab {
    public FriendsTab() {
        super("Friends");
    }

    @Override
    public TabScreen createScreen(GuiTheme theme) {
        return new FriendsScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof FriendsScreen;
    }

    private static class FriendsScreen extends WindowTabScreen {
        public FriendsScreen(GuiTheme theme, Tab tab) {
            super(theme, tab);

            Settings s = new Settings();

            SettingGroup sgDefault = s.getDefaultGroup();

            sgDefault.add(new ColorSetting.Builder()
                    .name("color")
                    .description("The color used to show friends.")
                    .defaultValue(new SettingColor(250, 0, 150))
                    .onChanged(Friends.get().color::set)
                    .onModuleActivated(colorSetting -> colorSetting.set(Friends.get().color))
                    .build()
            );

            sgDefault.add(new BoolSetting.Builder()
                    .name("attack")
                    .description("Whether to attack friends.")
                    .defaultValue(false)
                    .onChanged(aBoolean -> Friends.get().attack = aBoolean)
                    .onModuleActivated(booleanSetting -> booleanSetting.set(Friends.get().attack))
                    .build()
            );

            s.onActivated();
            add(theme.settings(s)).expandX();

            // Friends
            WSection friends = add(theme.section("Friends")).expandX().widget();
            WTable table = friends.add(theme.table()).expandX().widget();

            fillTable(table);

            // New
            WHorizontalList list = friends.add(theme.horizontalList()).expandX().widget();

            WTextBox nameW = list.add(theme.textBox("")).minWidth(400).expandX().widget();
            nameW.setFocused(true);

            WPlus add = list.add(theme.plus()).widget();
            add.action = () -> {
                String name = nameW.get().trim();

                if (Friends.get().add(new Friend(name))) {
                    nameW.set("");

                    table.clear();
                    fillTable(table);
                }
            };

            enterAction = add.action;
        }

        private void fillTable(WTable table) {
            for (Friend friend : Friends.get()) {
                table.add(theme.label(friend.name));

                WMinus remove = table.add(theme.minus()).expandCellX().right().widget();
                remove.action = () -> {
                    Friends.get().remove(friend);

                    table.clear();
                    fillTable(table);
                };

                table.row();
            }
        }
    }
}

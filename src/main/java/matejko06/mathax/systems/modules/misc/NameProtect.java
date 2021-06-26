package matejko06.mathax.systems.modules.misc;

import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.settings.StringSetting;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;

public class NameProtect extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<String> name = sgGeneral.add(new StringSetting.Builder()
            .name("name")
            .description("Name to be replaced with.")
            .defaultValue("Matejko06")
            .build()
    );

    private String username = "If you see this, something is wrong.";

    public NameProtect() {
        super(Categories.Player, "name-protect", "Hides your name client-side.");
    }

    @Override
    public void onActivate() {
        username = mc.getSession().getUsername();
    }

    public String replaceName(String string) {
        if (string.contains(username) && name.get().length() > 0 && isActive()) {
            return string.replace(username, name.get());
        } else return string;
    }

    public String getName(String original) {
        if (name.get().length() > 0 && isActive()) return name.get();
        else return original;
    }
}

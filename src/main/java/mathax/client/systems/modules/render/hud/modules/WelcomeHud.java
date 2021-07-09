package mathax.client.systems.modules.render.hud.modules;

import mathax.client.settings.ColorSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.misc.NameProtect;
import mathax.client.systems.modules.render.hud.HUD;
import mathax.client.utils.render.color.SettingColor;

public class WelcomeHud extends TripleTextHudElement {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<SettingColor> color = sgGeneral.add(new ColorSetting.Builder()
            .name("color")
            .description("Color of welcome text.")
            .defaultValue(new SettingColor(0, 255, 100))
            .build()
    );

    public WelcomeHud(HUD hud) {
        super(hud, "welcome", "Displays a welcome message.", "Welcome to MatHax Client, "); // "Welcome to MatHax Client, Developer "
        rightColor = color.get();
    }

    @Override
    protected String getRight() {
        return Modules.get().get(NameProtect.class).getName(mc.getSession().getUsername());
    }

    public String getEnd() {
        return "!";
    }
}

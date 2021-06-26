package matejko06.mathax.systems.modules.render.hud.modules;

import matejko06.mathax.settings.ColorSetting;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.systems.modules.Modules;
import matejko06.mathax.systems.modules.misc.NameProtect;
import matejko06.mathax.systems.modules.render.hud.HUD;
import matejko06.mathax.utils.render.color.SettingColor;
import net.minecraft.client.MinecraftClient;

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

package matejko06.mathax.systems.modules.movement;

import matejko06.mathax.settings.EnumSetting;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.systems.modules.Modules;
import matejko06.mathax.systems.modules.render.Freecam;

public class Sneak extends Module {
    public enum Mode {
        Packet,
        Vanilla
    }
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
            .name("mode")
            .description("Which method to sneak.")
            .defaultValue(Mode.Vanilla)
            .build()
    );

    public Sneak() {
        super (Categories.Movement, "sneak", "Sneaks for you");
    }

    public boolean doPacket() {
        return isActive() && !Modules.get().isActive(Freecam.class) && mode.get() == Mode.Packet;
    }

    public boolean doVanilla() {
        return isActive() && !Modules.get().isActive(Freecam.class) && mode.get() == Mode.Vanilla;
    }
}

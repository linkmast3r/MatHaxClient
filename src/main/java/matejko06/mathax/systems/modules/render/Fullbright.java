package matejko06.mathax.systems.modules.render;

import matejko06.mathax.MatHaxClient;
import matejko06.mathax.events.world.TickEvent;
import matejko06.mathax.settings.EnumSetting;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.systems.modules.Modules;
import matejko06.mathax.bus.EventHandler;
import net.minecraft.client.MinecraftClient;

public class Fullbright extends Module {

    public enum Mode {
        Gamma,
        Luminance
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
            .name("mode")
            .description("The mode to use for Fullbright.")
            .defaultValue(Mode.Luminance)
            .onChanged(mode -> {
                if(mode == Mode.Luminance) {
                    mc.options.gamma = StaticListener.prevGamma;
                }
            })
            .build()
    );

    public Fullbright() {
        super(Categories.Render, "fullbright", "Lights up your world!");

        MatHaxClient.EVENT_BUS.subscribe(StaticListener.class);
    }

    @Override
    public void onActivate() {
        enable();
    }

    @Override
    public void onDeactivate() {
        disable();
    }

    public static boolean isEnabled() {
        return StaticListener.timesEnabled > 0;
    }

    public static void enable() {
        StaticListener.timesEnabled++;
    }

    public static void disable() {
        StaticListener.timesEnabled--;
    }

    private static class StaticListener {
        private static final MinecraftClient mc = MinecraftClient.getInstance();
        private static final Fullbright fullbright = Modules.get().get(Fullbright.class);

        private static int timesEnabled;
        private static int lastTimesEnabled;

        private static double prevGamma = mc.options.gamma;

        @EventHandler
        private static void onTick(TickEvent.Post event) {
            if (timesEnabled > 0 && lastTimesEnabled == 0) {
                prevGamma = mc.options.gamma;
            } else if (timesEnabled == 0 && lastTimesEnabled > 0) {
                if (fullbright.mode.get() == Mode.Gamma) {
                    mc.options.gamma = prevGamma == 16 ? 1 : prevGamma;
                }
            }

            if (timesEnabled > 0) {
                if (fullbright.mode.get() == Mode.Gamma) {
                    mc.options.gamma = 16;
                }
            }

            lastTimesEnabled = timesEnabled;
        }
    }
}

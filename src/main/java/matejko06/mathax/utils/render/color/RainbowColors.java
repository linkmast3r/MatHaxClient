package matejko06.mathax.utils.render.color;

import matejko06.mathax.MatHaxClient;
import matejko06.mathax.events.world.TickEvent;
import matejko06.mathax.gui.GuiThemes;
import matejko06.mathax.gui.WidgetScreen;
import matejko06.mathax.gui.tabs.builtin.ConfigTab;
import matejko06.mathax.settings.ColorSetting;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.systems.waypoints.Waypoint;
import matejko06.mathax.systems.waypoints.Waypoints;
import matejko06.mathax.utils.misc.UnorderedArrayList;
import matejko06.mathax.bus.EventHandler;

import java.util.List;

import static matejko06.mathax.utils.Utils.mc;

public class RainbowColors {

    public static final RainbowColor GLOBAL = new RainbowColor().setSpeed(ConfigTab.rainbowSpeed.get() / 100);

    private static final List<Setting<SettingColor>> colorSettings = new UnorderedArrayList<>();
    private static final List<SettingColor> colors = new UnorderedArrayList<>();
    private static final List<Runnable> listeners = new UnorderedArrayList<>();

    public static void init() {
        MatHaxClient.EVENT_BUS.subscribe(RainbowColors.class);
    }

    public static void addSetting(Setting<SettingColor> setting) {
        colorSettings.add(setting);
    }

    public static void removeSetting(Setting<SettingColor> setting) {
        colorSettings.remove(setting);
    }

    public static void add(SettingColor color) {
        colors.add(color);
    }

    public static void register(Runnable runnable) {
        listeners.add(runnable);
    }

    @EventHandler
    private static void onTick(TickEvent.Post event) {
        GLOBAL.getNext();

        for (Setting<SettingColor> setting : colorSettings) {
            if (setting.module == null || setting.module.isActive()) setting.get().update();
        }

        for (SettingColor color : colors) {
            color.update();
        }

        for (Waypoint waypoint : Waypoints.get()) {
            waypoint.color.update();
        }

        if (mc.currentScreen instanceof WidgetScreen) {
            for (SettingGroup group : GuiThemes.get().settings) {
                for (Setting<?> setting : group) {
                    if (setting instanceof ColorSetting) ((SettingColor) setting.get()).update();
                }
            }
        }

        for (Runnable listener : listeners) listener.run();
    }
}

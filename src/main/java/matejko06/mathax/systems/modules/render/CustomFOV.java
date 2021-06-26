package matejko06.mathax.systems.modules.render;

import matejko06.mathax.events.render.Render3DEvent;
import matejko06.mathax.settings.IntSetting;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.bus.EventHandler;

public class CustomFOV extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Integer> fovSetting = sgGeneral.add(new IntSetting.Builder()
            .name("fov")
            .description("Your custom fov.")
            .defaultValue(100)
            .sliderMin(1)
            .sliderMax(179)
            .build()
    );

    private double fov;

    public CustomFOV() {
        super(Categories.Render, "custom-fov", "Allows your FOV to be more customizable.");
    }

    @Override
    public void onActivate() {
        fov = mc.options.fov;
        mc.options.fov = fovSetting.get();
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        if (fovSetting.get() != mc.options.fov) {
            mc.options.fov = fovSetting.get();
        }
    }

    @Override
    public void onDeactivate() {
     mc.options.fov = fov;
    }
}

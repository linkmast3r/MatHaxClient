package matejko06.mathax.systems.modules.render;

import matejko06.mathax.events.game.ChangePerspectiveEvent;
import matejko06.mathax.events.mathax.MouseScrollEvent;
import matejko06.mathax.settings.BoolSetting;
import matejko06.mathax.settings.DoubleSetting;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.bus.EventHandler;
import net.minecraft.client.option.Perspective;

public class CameraTweaks extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> clip = sgGeneral.add(new BoolSetting.Builder()
            .name("clip")
            .description("Allows the camera to clip through blocks.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Double> cameraDistance = sgGeneral.add(new DoubleSetting.Builder()
            .name("camera-distance")
            .description("The distance the third person camera is from the player.")
            .defaultValue(4)
            .min(0)
            .onChanged(value -> distance = value)
            .build()
    );

    private final Setting<Double> scrollSensitivity = sgGeneral.add(new DoubleSetting.Builder()
            .name("distance-scroll-sensitivity")
            .description("Scroll sensitivity when changing the cameras distance. 0 to disable.")
            .defaultValue(1)
            .min(0)
            .build()
    );

    public double distance;

    public CameraTweaks() {
        super(Categories.Render, "camera-tweaks", "Allows modification of the third person camera.");
    }

    @Override
    public void onActivate() {
        distance = cameraDistance.get();
    }

    @EventHandler
    private void onPerspectiveChanged(ChangePerspectiveEvent event) {
        distance = cameraDistance.get();
    }

    @EventHandler
    private void onMouseScroll(MouseScrollEvent event) {
        if (mc.options.getPerspective() == Perspective.FIRST_PERSON) return;
        if (scrollSensitivity.get() > 0) {
            distance += event.value * 0.25 * (scrollSensitivity.get() * distance);

            event.cancel();
        }
    }

    public boolean clip() {
        return isActive() && clip.get();
    }

    public double getDistance() {
        return isActive() ? distance : 4;
    }
}

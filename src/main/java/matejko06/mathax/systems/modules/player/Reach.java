package matejko06.mathax.systems.modules.player;

import matejko06.mathax.settings.DoubleSetting;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;

public class Reach extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> reach = sgGeneral.add(new DoubleSetting.Builder()
            .name("reach")
            .description("Your reach modifier.")
            .defaultValue(5)
            .min(0)
            .sliderMax(6)
            .build()
    );

    public Reach() {
        super(Categories.Player, "reach", "Gives you super long arms.");
    }

    public float getReach() {
        if (!isActive()) return mc.interactionManager.getCurrentGameMode().isCreative() ? 5.0F : 4.5F;
        return reach.get().floatValue();
    }
}

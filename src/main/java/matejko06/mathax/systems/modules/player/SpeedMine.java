package matejko06.mathax.systems.modules.player;

import matejko06.mathax.events.world.TickEvent;
import matejko06.mathax.mixin.StatusEffectInstanceAccessor;
import matejko06.mathax.settings.DoubleSetting;
import matejko06.mathax.settings.EnumSetting;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.bus.EventHandler;
import net.minecraft.entity.effect.StatusEffectInstance;

import static net.minecraft.entity.effect.StatusEffects.HASTE;

public class SpeedMine extends Module {
    public enum Mode {
        Normal,
        Haste1,
        Haste2
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
            .name("mode")
            .defaultValue(Mode.Normal)
            .build()
    );
    public final Setting<Double> modifier = sgGeneral.add(new DoubleSetting.Builder()
            .name("modifier")
            .description("Mining speed modifier. An additional value of 0.2 is equivalent to one haste level (1.2 = haste 1).")
            .defaultValue(1.4D)
            .min(0D)
            .sliderMin(1D)
            .sliderMax(10D)
            .build()
    );

    public SpeedMine() {
        super(Categories.Player, "speed-mine", "Allows you to quickly mine blocks.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        Mode mode = this.mode.get();

        if (mode == Mode.Haste1 || mode == Mode.Haste2) {
            int amplifier = mode == Mode.Haste2 ? 1 : 0;
            if (mc.player.hasStatusEffect(HASTE)) {
                StatusEffectInstance effect = mc.player.getStatusEffect(HASTE);
                ((StatusEffectInstanceAccessor) effect).setAmplifier(amplifier);
                if (effect.getDuration() < 20) {
                    ((StatusEffectInstanceAccessor) effect).setDuration(20);
                }
            } else {
                mc.player.addStatusEffect(new StatusEffectInstance(HASTE, 20, amplifier, false, false, false));
            }
        }
    }
}

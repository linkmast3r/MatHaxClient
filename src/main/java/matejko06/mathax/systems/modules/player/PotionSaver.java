package matejko06.mathax.systems.modules.player;

import matejko06.mathax.settings.BoolSetting;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.settings.StatusEffectListSetting;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.utils.player.PlayerUtils;
import net.minecraft.entity.effect.StatusEffect;

import java.util.Arrays;
import java.util.List;

import static net.minecraft.entity.effect.StatusEffects.*;

public class PotionSaver extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<List<StatusEffect>> effects = sgGeneral.add(new StatusEffectListSetting.Builder()
        .name("effects")
        .description("The effects to preserve.")
        .defaultValue(Arrays.asList(STRENGTH, ABSORPTION, RESISTANCE, FIRE_RESISTANCE, SPEED, HASTE, REGENERATION, WATER_BREATHING, SATURATION, LUCK, SLOW_FALLING, DOLPHINS_GRACE, CONDUIT_POWER, HERO_OF_THE_VILLAGE))
        .build()
    );

    public final Setting<Boolean> onlyWhenStationary = sgGeneral.add(new BoolSetting.Builder()
        .name("only-when-stationary")
        .description("Only freezes effects when you aren't moving.")
        .defaultValue(false)
        .build()
    );

    public PotionSaver() {
        super(Categories.Player, "potion-saver", "Stops potion effects ticking when you stand still.");
    }

    public boolean shouldFreeze(StatusEffect effect) {
        return isActive() && (!onlyWhenStationary.get() || !PlayerUtils.isMoving()) && !mc.player.getStatusEffects().isEmpty() && effects.get().contains(effect);
    }
}

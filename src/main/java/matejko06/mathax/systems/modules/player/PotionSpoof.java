package matejko06.mathax.systems.modules.player;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import matejko06.mathax.events.world.TickEvent;
import matejko06.mathax.mixin.StatusEffectInstanceAccessor;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.settings.StatusEffectAmplifierMapSetting;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.utils.Utils;
import matejko06.mathax.bus.EventHandler;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

public class PotionSpoof extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Object2IntMap<StatusEffect>> potions = sgGeneral.add(new StatusEffectAmplifierMapSetting.Builder()
            .name("potions")
            .description("Potions to add.")
            .defaultValue(Utils.createStatusEffectMap())
            .build()
    );

    public PotionSpoof() {
        super(Categories.Player, "potion-spoof", "Spoofs specified potion effects for you. SOME effects DO NOT work.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        for (StatusEffect statusEffect : potions.get().keySet()) {
            int level = potions.get().getInt(statusEffect);
            if (level <= 0) continue;

            if (mc.player.hasStatusEffect(statusEffect)) {
                StatusEffectInstance instance = mc.player.getStatusEffect(statusEffect);
                ((StatusEffectInstanceAccessor) instance).setAmplifier(level - 1);
                if (instance.getDuration() < 20) ((StatusEffectInstanceAccessor) instance).setDuration(20);
            } else {
                mc.player.addStatusEffect(new StatusEffectInstance(statusEffect, 20, level - 1));
            }
        }
    }
}

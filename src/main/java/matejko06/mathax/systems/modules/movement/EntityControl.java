package matejko06.mathax.systems.modules.movement;

import matejko06.mathax.events.world.TickEvent;
import matejko06.mathax.mixin.ClientPlayerEntityAccessor;
import matejko06.mathax.mixininterface.IHorseBaseEntity;
import matejko06.mathax.settings.BoolSetting;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.utils.Utils;
import matejko06.mathax.bus.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.HorseBaseEntity;

public class EntityControl extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> maxJump = sgGeneral.add(new BoolSetting.Builder()
            .name("max-jump")
            .description("Sets jump power to maximum.")
            .defaultValue(true)
            .build()
    );

    public EntityControl() {
        super(Categories.Movement, "entity-control", "Lets you control rideable entities without a saddle.");
    }

    @Override
    public void onDeactivate() {
        if (!Utils.canUpdate() || mc.world.getEntities() == null) return;

        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof HorseBaseEntity) ((IHorseBaseEntity) entity).setSaddled(false);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        for (Entity entity : mc.world.getEntities()) {
            if (entity instanceof HorseBaseEntity) ((IHorseBaseEntity) entity).setSaddled(true);
        }

        if (maxJump.get()) ((ClientPlayerEntityAccessor) mc.player).setMountJumpStrength(1);
    }
}

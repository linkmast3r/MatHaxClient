package matejko06.mathax.systems.modules.movement;

import matejko06.mathax.events.world.TickEvent;
import matejko06.mathax.mixininterface.IVec3d;
import matejko06.mathax.settings.DoubleSetting;
import matejko06.mathax.settings.EnumSetting;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.bus.EventHandler;

public class AutoJump extends Module {
    public enum JumpWhen {
        Sprinting,
        Walking,
        Always
    }

    public enum Mode {
        Jump,
        LowHop
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
            .name("mode")
            .description("The method of jumping.")
            .defaultValue(Mode.Jump)
            .build()
    );

    private final Setting<JumpWhen> jumpIf = sgGeneral.add(new EnumSetting.Builder<JumpWhen>()
            .name("jump-if")
            .description("Jump if.")
            .defaultValue(JumpWhen.Always)
            .build()
    );

    private final Setting<Double> velocityHeight = sgGeneral.add(new DoubleSetting.Builder()
            .name("velocity-height")
            .description("The distance that velocity mode moves you.")
            .defaultValue(0.25)
            .min(0)
            .sliderMax(2)
            .build()
    );

    public AutoJump() {
        super(Categories.Movement, "auto-jump", "Automatically jumps.");
    }

    private boolean jump() {
        switch (jumpIf.get()) {
            case Sprinting: return mc.player.isSprinting() && (mc.player.forwardSpeed != 0 || mc.player.sidewaysSpeed != 0);
            case Walking:   return mc.player.forwardSpeed != 0 || mc.player.sidewaysSpeed != 0;
            case Always:    return true;
            default:        return false;
        }
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (!mc.player.isOnGround() || mc.player.isSneaking() || !jump()) return;

        if (mode.get() == Mode.Jump) mc.player.jump();
        else ((IVec3d) mc.player.getVelocity()).setY(velocityHeight.get());
    }
}

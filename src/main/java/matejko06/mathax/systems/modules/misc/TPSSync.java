package matejko06.mathax.systems.modules.misc;

import matejko06.mathax.events.world.TickEvent;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.systems.modules.Modules;
import matejko06.mathax.systems.modules.world.Timer;
import matejko06.mathax.utils.world.TickRate;
import matejko06.mathax.bus.EventHandler;

public class TPSSync extends Module {
    public TPSSync() {
        super(Categories.Misc, "tps-sync", "Syncs the clients TPS with the server's.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        Modules.get().get(Timer.class).setOverride((TickRate.INSTANCE.getTickRate() >= 1 ? TickRate.INSTANCE.getTickRate() : 1) / 20);
    }

    @Override
    public void onDeactivate() {
        Modules.get().get(Timer.class).setOverride(Timer.OFF);
    }
}

package mathax.client.systems.modules.misc;

import mathax.client.events.world.TickEvent;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.world.Timer;
import mathax.client.utils.world.TickRate;
import mathax.client.bus.EventHandler;

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

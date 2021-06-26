package matejko06.mathax.systems.modules.misc;

import matejko06.mathax.events.game.OpenScreenEvent;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.systems.modules.Modules;
import matejko06.mathax.systems.modules.render.WaypointsModule;
import matejko06.mathax.bus.EventHandler;
import matejko06.mathax.bus.EventPriority;
import net.minecraft.client.gui.screen.DeathScreen;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super(Categories.Player, "auto-respawn", "Automatically respawns after death.");
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onOpenScreenEvent(OpenScreenEvent event) {
        if (!(event.screen instanceof DeathScreen)) return;

        Modules.get().get(WaypointsModule.class).addDeath(mc.player.getPos());
        mc.player.requestRespawn();
        event.cancel();
    }
}

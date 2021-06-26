package matejko06.mathax.systems.modules.render.hud.modules;

import matejko06.mathax.systems.modules.Modules;
import matejko06.mathax.systems.modules.render.hud.HUD;
import matejko06.mathax.systems.modules.world.Timer;

public class SpeedHud extends TripleTextHudElement {
    public SpeedHud(HUD hud) {
        super(hud, "speed", "Displays your horizontal speed.", "Speed: ");
    }

    @Override
    protected String getRight() {
        if (isInEditor()) return "0,0";

        double tX = Math.abs(mc.player.getX() - mc.player.prevX);
        double tZ = Math.abs(mc.player.getZ() - mc.player.prevZ);
        double length = Math.sqrt(tX * tX + tZ * tZ);

        if (Modules.get().isActive(Timer.class)){
            length *= Modules.get().get(Timer.class).getMultiplier();
        }

        return String.format("%.1f", length * 20);
    }

    public String getEnd() {
        return "";
    }
}

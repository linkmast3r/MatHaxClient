package matejko06.mathax.systems.modules.render.hud.modules;

import matejko06.mathax.mixin.MinecraftClientAccessor;
import matejko06.mathax.systems.modules.render.hud.HUD;

public class FpsHud extends TripleTextHudElement {
    public FpsHud(HUD hud) {
        super(hud, "fps", "Displays your FPS.", "FPS: ");
    }

    @Override
    protected String getRight() {
        return Integer.toString(((MinecraftClientAccessor) mc).getFps());
    }

    public String getEnd() {
        return "";
    }
}

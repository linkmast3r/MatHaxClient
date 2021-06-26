package matejko06.mathax.systems.modules.render.hud.modules;

import matejko06.mathax.MatHaxClient;
import matejko06.mathax.systems.config.Config;
import matejko06.mathax.systems.modules.render.hud.HUD;

public class WatermarkHud extends TripleTextHudElement {
    public WatermarkHud(HUD hud) {
        super(hud, "watermark", "Displays a MatHax Client watermark.", "MatHax Client ");
    }

    @Override
    protected String getRight() {
        return "v" + MatHaxClient.clientversion;
    }

    public String getEnd() {
        return "";
    }
}

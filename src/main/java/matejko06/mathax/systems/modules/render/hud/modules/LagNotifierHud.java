package matejko06.mathax.systems.modules.render.hud.modules;

import matejko06.mathax.systems.modules.render.hud.HUD;
import matejko06.mathax.utils.render.color.Color;
import matejko06.mathax.utils.world.TickRate;

public class LagNotifierHud extends TripleTextHudElement {
    private static final Color RED = new Color(225, 45, 45);
    private static final Color AMBER = new Color(235, 158, 52);
    private static final Color YELLOW = new Color(255, 255, 5);

    public LagNotifierHud(HUD hud) {
        super(hud, "lag-notifier", "Displays if the server is lagging in ticks.", "Server is lagging ");
    }

    @Override
    protected String getRight() {
        if (isInEditor()) {
            rightColor = hud.secondaryColor.get();
            visible = true;
            return "4,3";
        }

        float timeSinceLastTick = TickRate.INSTANCE.getTimeSinceLastTick();

        if (timeSinceLastTick > 10) rightColor = hud.secondaryColor.get();
        else if (timeSinceLastTick > 3) rightColor = AMBER;
        else rightColor = YELLOW;

        visible = timeSinceLastTick >= 1f;
        return String.format("%.1f", timeSinceLastTick);
    }

    public String getEnd() {
        return "";
    }
}

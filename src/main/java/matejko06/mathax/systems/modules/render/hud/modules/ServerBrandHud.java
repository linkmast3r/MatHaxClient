package matejko06.mathax.systems.modules.render.hud.modules;

import matejko06.mathax.systems.modules.render.hud.HUD;
import matejko06.mathax.utils.Utils;

public class ServerBrandHud extends TripleTextHudElement {
    public ServerBrandHud(HUD hud) {
        super(hud, "serverbrand", "Displays the brand of the server you're currently in.", "ServerBrand: ");
    }

    @Override
    protected String getRight() {
        if (!Utils.canUpdate()) return "None";

        return mc.player.getServerBrand();
    }

    public String getEnd() {
        return "";
    }
}




package mathax.client.systems.modules.render.hud.modules;

import mathax.client.systems.modules.render.hud.HUD;
import mathax.client.utils.Utils;

public class ServerHud extends TripleTextHudElement {
    public ServerHud(HUD hud) {
        super(hud, "server", "Displays the server you're currently in.", "Server: ");
    }

    @Override
    protected String getRight() {
        if (!Utils.canUpdate()) return "None";

        return Utils.getWorldName();
    }

    public String getEnd() {
        return "";
    }
}




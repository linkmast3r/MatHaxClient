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

        return mc.player.getServerBrand().replace("§a", "").replace("§b", "").replace("§c", "").replace("§d", "").replace("§e", "").replace("§1", "").replace("§2", "").replace("§3", "").replace("§4", "").replace("§5", "").replace("§6", "").replace("§7", "").replace("§8", "").replace("§9", "").replace("§k", "").replace("§l", "").replace("§m", "").replace("§n", "").replace("§o", "").replace("§r", "").replace("&a", "").replace("&b", "").replace("&c", "").replace("&d", "").replace("&e", "").replace("&1", "").replace("&2", "").replace("&3", "").replace("&4", "").replace("&5", "").replace("&6", "").replace("&7", "").replace("&8", "").replace("&9", "").replace("&k", "").replace("&l", "").replace("&m", "").replace("&n", "").replace("&o", "").replace("&r", "");
    }

    public String getEnd() {
        return "";
    }
}




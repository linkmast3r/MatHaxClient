package mathax.client.systems.modules.misc;

import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;

public class AntiPacketKick extends Module {
    public AntiPacketKick() {
        super(Categories.Misc, "anti-packet-kick", "Attempts to prevent you from being disconnected by large packets.");
    }
}

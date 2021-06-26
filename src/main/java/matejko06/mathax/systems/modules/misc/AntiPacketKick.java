package matejko06.mathax.systems.modules.misc;

import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;

public class AntiPacketKick extends Module {
    public AntiPacketKick() {
        super(Categories.Misc, "anti-packet-kick", "Attempts to prevent you from being disconnected by large packets.");
    }
}

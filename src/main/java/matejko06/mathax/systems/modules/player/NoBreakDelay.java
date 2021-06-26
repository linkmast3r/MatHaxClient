package matejko06.mathax.systems.modules.player;

import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;

public class NoBreakDelay extends Module {
    public NoBreakDelay() {
        super(Categories.Player, "no-break-delay", "Completely removes the delay between breaking blocks.");
    }
}

package matejko06.mathax.systems.modules.render;

import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;

public class UnfocusedCPU extends Module {
    public UnfocusedCPU() {
        super(Categories.Render, "unfocused-cpu", "Will not render anything when your Minecraft window is not focused.");
    }
}

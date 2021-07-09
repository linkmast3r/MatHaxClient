package mathax.client.systems.modules.render;

import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;

public class UnfocusedCPU extends Module {
    public UnfocusedCPU() {
        super(Categories.Render, "unfocused-cpu", "Will not render anything when your Minecraft window is not focused.");
    }
}

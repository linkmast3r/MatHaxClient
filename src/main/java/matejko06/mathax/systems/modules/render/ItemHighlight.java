package matejko06.mathax.systems.modules.render;

import matejko06.mathax.settings.ColorSetting;
import matejko06.mathax.settings.ItemListSetting;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.utils.render.color.SettingColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemHighlight extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<List<Item>> items = sgGeneral.add(new ItemListSetting.Builder()
            .name("items")
            .description("Items to highlight.")
            .defaultValue(new ArrayList<>())
            .build()
    );

    private final Setting<SettingColor> color = sgGeneral.add(new ColorSetting.Builder()
            .name("color")
            .description("The color to highlight the items with.")
            .defaultValue(new SettingColor(225, 25, 255, 50))
            .build()
    );

    public ItemHighlight() {
        super(Categories.Render, "item-highlight", "Highlights selected items when in guis");
    }

    public int getColor(ItemStack stack) {
        if (items.get().contains(stack.getItem()) && isActive()) return color.get().getPacked();
        return -1;
    }
}

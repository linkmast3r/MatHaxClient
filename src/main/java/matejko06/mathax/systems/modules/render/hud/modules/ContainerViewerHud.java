package matejko06.mathax.systems.modules.render.hud.modules;

import matejko06.mathax.renderer.Renderer2D;
import matejko06.mathax.settings.BoolSetting;
import matejko06.mathax.settings.DoubleSetting;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.systems.modules.render.hud.HUD;
import matejko06.mathax.systems.modules.render.hud.HudRenderer;
import matejko06.mathax.utils.Utils;
import matejko06.mathax.utils.render.RenderUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class ContainerViewerHud extends HudElement {
    private static final Identifier TEXTURE = new Identifier("mathax", "textures/container.png");

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> scale = sgGeneral.add(new DoubleSetting.Builder()
        .name("scale")
        .description("The scale.")
        .defaultValue(2)
        .min(1)
        .sliderMin(1).sliderMax(5)
        .build()
    );

    private final Setting<Boolean> echestNoItem = sgGeneral.add(new BoolSetting.Builder()
        .name("echest-when-empty")
        .description("Display contents of ender chest if not holding any other container.")
        .defaultValue(false)
        .build()
    );

    private final ItemStack[] inventory = new ItemStack[9 * 3];

    public ContainerViewerHud(HUD hud) {
        super(hud, "container-viewer", "Displays held containers.", false);
    }

    @Override
    public void update(HudRenderer renderer) {
        box.setSize(176 * scale.get(), 67 * scale.get());
    }

    @Override
    public void render(HudRenderer renderer) {
        double x = box.getX();
        double y = box.getY();

        ItemStack container = getContainer();
        if (container == null) return;

        drawBackground((int) x, (int) y, container);

        Utils.getItemsInContainerItem(container, inventory);

        for (int row = 0; row < 3; row++) {
            for (int i = 0; i < 9; i++) {
                ItemStack stack = inventory[row * 9 + i];
                if (stack == null || stack.isEmpty()) continue;

                RenderUtils.drawItem(stack, (int) (x + (8 + i * 18) * scale.get()), (int) (y + (7 + row * 18) * scale.get()), scale.get(), true);
            }
        }
    }

    private ItemStack getContainer() {
        if (isInEditor()) return Items.ENDER_CHEST.getDefaultStack();

        ItemStack stack = mc.player.getOffHandStack();
        if (Utils.hasItems(stack)) return stack;

        stack = mc.player.getMainHandStack();
        if (Utils.hasItems(stack)) return stack;

        return echestNoItem.get() ? Items.ENDER_CHEST.getDefaultStack() : null;
    }

    private void drawBackground(int x, int y, ItemStack container) {
        mc.getTextureManager().bindTexture(TEXTURE);

        Renderer2D.TEXTURE.begin();
        Renderer2D.TEXTURE.texQuad(x, y, box.width, box.height, Utils.getShulkerColor(container));
        Renderer2D.TEXTURE.render(null);
    }
}
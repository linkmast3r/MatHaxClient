package matejko06.mathax.systems.modules.render;

import matejko06.mathax.events.render.Render3DEvent;
import matejko06.mathax.events.world.TickEvent;
import matejko06.mathax.renderer.ShapeMode;
import matejko06.mathax.settings.ColorSetting;
import matejko06.mathax.settings.EnumSetting;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.utils.entity.EntityUtils;
import matejko06.mathax.utils.entity.SortPriority;
import matejko06.mathax.utils.entity.TargetUtils;
import matejko06.mathax.utils.render.color.SettingColor;
import matejko06.mathax.bus.EventHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class CityESP extends Module {
    private final SettingGroup sgRender = settings.createGroup("Render");

    // Render

    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>()
            .name("shape-mode")
            .description("How the shapes are rendered.")
            .defaultValue(ShapeMode.Both)
            .build()
    );

    private final Setting<SettingColor> sideColor = sgRender.add(new ColorSetting.Builder()
            .name("side-color")
            .description("The side color of the rendering.")
            .defaultValue(new SettingColor(225, 0, 0, 75))
            .build()
    );

    private final Setting<SettingColor> lineColor = sgRender.add(new ColorSetting.Builder()
            .name("line-color")
            .description("The line color of the rendering.")
            .defaultValue(new SettingColor(225, 0, 0, 255))
            .build()
    );

    private BlockPos target;

    public CityESP() {
        super(Categories.Render, "city-esp", "Displays blocks that can be broken in order to city another player.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        PlayerEntity targetEntity = TargetUtils.getPlayerTarget(mc.interactionManager.getReachDistance() + 2, SortPriority.LowestDistance);

        if (TargetUtils.isBadTarget(targetEntity, mc.interactionManager.getReachDistance() + 2)) {
            target = null;
        } else {
            target = EntityUtils.getCityBlock(targetEntity);
        }
    }

    @EventHandler
    private void onRender(Render3DEvent event) {
        if (target == null) return;

        event.renderer.box(target, sideColor.get(), lineColor.get(), shapeMode.get(), 0);
    }
}

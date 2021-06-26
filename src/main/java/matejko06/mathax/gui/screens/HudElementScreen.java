package matejko06.mathax.gui.screens;

import matejko06.mathax.events.render.Render2DEvent;
import matejko06.mathax.gui.GuiTheme;
import matejko06.mathax.gui.WindowScreen;
import matejko06.mathax.gui.renderer.GuiRenderer;
import matejko06.mathax.gui.widgets.containers.WContainer;
import matejko06.mathax.gui.widgets.containers.WHorizontalList;
import matejko06.mathax.gui.widgets.pressable.WButton;
import matejko06.mathax.gui.widgets.pressable.WCheckbox;
import matejko06.mathax.systems.modules.Modules;
import matejko06.mathax.systems.modules.render.hud.HUD;
import matejko06.mathax.systems.modules.render.hud.modules.HudElement;
import matejko06.mathax.utils.Utils;

import static matejko06.mathax.utils.Utils.getWindowWidth;

public class HudElementScreen extends WindowScreen {
    private final HudElement element;
    private WContainer settings;

    public HudElementScreen(GuiTheme theme, HudElement element) {
        super(theme, element.title);
        this.element = element;

        // Description
        add(theme.label(element.description, getWindowWidth() / 2.0));

        // Settings
        if (element.settings.sizeGroups() > 0) {
            settings = add(theme.verticalList()).expandX().widget();
            settings.add(theme.settings(element.settings)).expandX();

            add(theme.horizontalSeparator()).expandX();
        }

        // Bottom
        WHorizontalList bottomList = add(theme.horizontalList()).expandX().widget();

        //   Active
        bottomList.add(theme.label("Active:"));
        WCheckbox active = bottomList.add(theme.checkbox(element.active)).widget();
        active.action = () -> {
            if (element.active != active.checked) element.toggle();
        };

        WButton reset = bottomList.add(theme.button(GuiRenderer.RESET)).expandCellX().right().widget();
        reset.action = () -> {
            if (element.active != element.defaultActive) element.active = active.checked = element.defaultActive;
        };
    }

    @Override
    public void tick() {
        super.tick();

        if (settings == null) return;

        element.settings.tick(settings, theme);
    }

    @Override
    protected void onRenderBefore(float delta) {
        if (!Utils.canUpdate()) Modules.get().get(HUD.class).onRender(Render2DEvent.get(0, 0, delta));
    }
}

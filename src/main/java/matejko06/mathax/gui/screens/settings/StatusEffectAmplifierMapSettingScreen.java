package matejko06.mathax.gui.screens.settings;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import matejko06.mathax.gui.GuiTheme;
import matejko06.mathax.gui.WindowScreen;
import matejko06.mathax.gui.widgets.containers.WTable;
import matejko06.mathax.gui.widgets.input.WIntEdit;
import matejko06.mathax.gui.widgets.input.WTextBox;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.utils.misc.Names;
import net.minecraft.entity.effect.StatusEffect;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class StatusEffectAmplifierMapSettingScreen extends WindowScreen {
    private final Setting<Object2IntMap<StatusEffect>> setting;
    private final WTextBox filter;

    private String filterText = "";

    private WTable table;

    public StatusEffectAmplifierMapSettingScreen(GuiTheme theme, Setting<Object2IntMap<StatusEffect>> setting) {
        super(theme, "Modify Amplifiers");

        this.setting = setting;

        // Filter
        filter = add(theme.textBox("")).minWidth(400).expandX().widget();
        filter.setFocused(true);
        filter.action = () -> {
            filterText = filter.get().trim();

            table.clear();
            initWidgets();
        };

        table = add(theme.table()).expandX().widget();

        initWidgets();
    }

    private void initWidgets() {
        List<StatusEffect> statusEffects = new ArrayList<>(setting.get().keySet());
        statusEffects.sort(Comparator.comparing(Names::get));

        for (StatusEffect statusEffect : statusEffects) {
            String name = Names.get(statusEffect);
            if (!StringUtils.containsIgnoreCase(name, filterText)) continue;

            table.add(theme.label(name)).expandCellX();

            WIntEdit level = theme.intEdit(setting.get().getInt(statusEffect), 0, 0);
            level.hasSlider = false;
            level.action = () -> {
                setting.get().put(statusEffect, level.get());
                setting.changed();
            };

            table.add(level).minWidth(50);
            table.row();
        }
    }
}

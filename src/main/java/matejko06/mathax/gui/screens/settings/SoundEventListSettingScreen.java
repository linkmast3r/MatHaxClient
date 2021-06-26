package matejko06.mathax.gui.screens.settings;

import matejko06.mathax.gui.GuiTheme;
import matejko06.mathax.gui.widgets.WWidget;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.utils.misc.Names;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class SoundEventListSettingScreen extends LeftRightListSettingScreen<SoundEvent> {
    public SoundEventListSettingScreen(GuiTheme theme, Setting<List<SoundEvent>> setting) {
        super(theme, "Select Sounds", setting, setting.get(), Registry.SOUND_EVENT);
    }

    @Override
    protected WWidget getValueWidget(SoundEvent value) {
        return theme.label(getValueName(value));
    }

    @Override
    protected String getValueName(SoundEvent value) {
        return Names.getSoundName(value.getId());
    }
}

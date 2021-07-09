package mathax.client.systems.modules.misc;

import mathax.client.events.world.PlaySoundEvent;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.settings.SoundEventListSetting;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.bus.EventHandler;
import net.minecraft.sound.SoundEvent;

import java.util.ArrayList;
import java.util.List;

public class SoundBlocker extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<List<SoundEvent>> sounds = sgGeneral.add(new SoundEventListSetting.Builder()
            .name("sounds")
            .description("Sounds to block.")
            .defaultValue(new ArrayList<>(0))
            .build()
    );

    public SoundBlocker() {
        super(Categories.Misc, "sound-blocker", "Cancels out selected sounds.");
    }

    @EventHandler
    private void onPlaySound(PlaySoundEvent event) {
        for (SoundEvent sound : sounds.get()) {
            if (sound.getId().equals(event.sound.getId())) {
                event.cancel();
                break;
            }
        }
    }
}

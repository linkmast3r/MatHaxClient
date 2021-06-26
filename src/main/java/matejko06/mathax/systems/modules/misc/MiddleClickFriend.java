package matejko06.mathax.systems.modules.misc;

import matejko06.mathax.events.mathax.MouseButtonEvent;
import matejko06.mathax.settings.BoolSetting;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.systems.friends.Friend;
import matejko06.mathax.systems.friends.Friends;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.utils.misc.input.KeyAction;
import matejko06.mathax.bus.EventHandler;
import net.minecraft.entity.player.PlayerEntity;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_MIDDLE;

public class MiddleClickFriend extends Module {

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> message = sgGeneral.add(new BoolSetting.Builder()
            .name("message")
            .description("Sends a message to the player when you add them as a friend.")
            .defaultValue(false)
            .build()
    );

    public MiddleClickFriend() {
        super(Categories.Misc, "middle-click-friend", "Adds or removes a player as a friend via middle click.");
    }

    @EventHandler
    private void onMouseButton(MouseButtonEvent event) {
        if (event.action == KeyAction.Press && event.button == GLFW_MOUSE_BUTTON_MIDDLE && mc.currentScreen == null && mc.targetedEntity != null && mc.targetedEntity instanceof PlayerEntity) {
            if (!Friends.get().isFriend((PlayerEntity) mc.targetedEntity)) {
                Friends.get().add(new Friend((PlayerEntity) mc.targetedEntity));
                if (message.get()) mc.player.sendChatMessage("/msg " + mc.targetedEntity.getEntityName() + " I just friended you on MatHax.");
            } else {
                Friends.get().remove(Friends.get().get((PlayerEntity) mc.targetedEntity));
            }
        }
    }
}

package mathax.client.systems.modules.player;

import mathax.client.events.packets.PacketEvent;
import mathax.client.mixin.PlayerPositionLookS2CPacketAccessor;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.bus.EventHandler;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

public class NoRotate extends Module {
    public NoRotate() {
        super(Categories.Player, "no-rotate", "Attempts to block rotations sent from server to client.");
    }

    @EventHandler
    private void onReceivePacket(PacketEvent.Receive event) {
        if (event.packet instanceof PlayerPositionLookS2CPacket) {
            ((PlayerPositionLookS2CPacketAccessor) event.packet).setPitch(mc.player.getPitch());
            ((PlayerPositionLookS2CPacketAccessor) event.packet).setYaw(mc.player.getYaw());
        }
    }
}

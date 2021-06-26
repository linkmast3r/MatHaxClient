package matejko06.mathax.systems.modules.world;

import matejko06.mathax.events.packets.PacketEvent;
import matejko06.mathax.mixin.BlockHitResultAccessor;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.bus.EventHandler;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.math.Direction;

public class BuildHeight extends Module {
    public BuildHeight() {
        super(Categories.World, "build-height", "Allows you to interact with objects at the build limit.");
    }

    @EventHandler
    private void onSendPacket(PacketEvent.Send event) {
        if (!(event.packet instanceof PlayerInteractBlockC2SPacket)) return;

        PlayerInteractBlockC2SPacket p = (PlayerInteractBlockC2SPacket) event.packet;
        if (p.getBlockHitResult().getPos().y >= 255 && p.getBlockHitResult().getSide() == Direction.UP) {
            ((BlockHitResultAccessor) p.getBlockHitResult()).setSide(Direction.DOWN);
        }
    }
}

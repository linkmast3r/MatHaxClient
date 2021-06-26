package matejko06.mathax.systems.modules.misc;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import matejko06.mathax.events.packets.PacketEvent;
import matejko06.mathax.settings.PacketListSetting;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.utils.network.PacketUtils;
import matejko06.mathax.bus.EventHandler;
import matejko06.mathax.bus.EventPriority;
import net.minecraft.network.Packet;

import java.util.Set;

public class PacketCanceller extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Set<Class<? extends Packet<?>>>> s2cPackets = sgGeneral.add(new PacketListSetting.Builder()
        .name("S2C-packets")
        .description("Server-to-client packets to cancel.")
        .defaultValue(new ObjectOpenHashSet<>(0))
        .filter(aClass -> PacketUtils.getS2CPackets().contains(aClass))
        .build()
    );

    private final Setting<Set<Class<? extends Packet<?>>>> c2sPackets = sgGeneral.add(new PacketListSetting.Builder()
        .name("C2S-packets")
        .description("Client-to-server packets to cancel.")
        .defaultValue(new ObjectOpenHashSet<>(0))
        .filter(aClass -> PacketUtils.getC2SPackets().contains(aClass))
        .build()
    );

    public PacketCanceller() {
        super(Categories.Misc, "packet-canceller", "Allows you to cancel certain packets.");
    }

    @EventHandler(priority = EventPriority.HIGHEST + 1)
    private void onReceivePacket(PacketEvent.Receive event) {
        if (s2cPackets.get().contains(event.packet.getClass())) event.cancel();
    }

    @EventHandler(priority = EventPriority.HIGHEST + 1)
    private void onSendPacket(PacketEvent.Send event) {
        if (c2sPackets.get().contains(event.packet.getClass())) event.cancel();
    }
}

package matejko06.mathax.systems.modules.misc;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import matejko06.mathax.MatHaxClient;
import matejko06.mathax.events.world.TickEvent;
import matejko06.mathax.settings.BoolSetting;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.settings.SettingGroup;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Module;
import matejko06.mathax.utils.Utils;
import matejko06.mathax.utils.misc.Placeholders;
import matejko06.mathax.bus.EventHandler;
import net.minecraft.SharedConstants;

public class DiscordPresence extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> playericon = sgGeneral.add(new BoolSetting.Builder()
        .name("player-icon")
        .description("If your a special person for the MatHax Team, your head will be on the presence.")
        .defaultValue(true)
        .build()
    );

    String line1 = "%username% | %server%";
    String line2 = "http://mathaxclient.xyz";

    private static final DiscordRichPresence rpc = new DiscordRichPresence();
    private static final DiscordRPC instance = DiscordRPC.INSTANCE;
    private SmallImage currentSmallImage;
    private int ticks;

    public DiscordPresence() {
        super(Categories.Misc, "discord-presence", "Displays a RPC for you on Discord to show that you're playing MatHax Client!");
    }

    static String getMinecraftVersion(){
        return SharedConstants.getGameVersion().getName();
    }

    @Override
    public void onActivate() {
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        instance.Discord_Initialize("822968662012067844", handlers, true, null);

        rpc.startTimestamp = System.currentTimeMillis() / 1000L;
        rpc.largeImageKey = "mathax_logo";
        String largeText = "MatHax Client" + MatHaxClient.discordversion;
        rpc.largeImageText = largeText;
        updateDetails();

        instance.Discord_UpdatePresence(rpc);
        instance.Discord_RunCallbacks();
    }

    @Override
    public void onDeactivate() {
        instance.Discord_ClearPresence();
        instance.Discord_Shutdown();
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (!Utils.canUpdate()) return;
        ticks++;

        if (ticks >= 200) {
            if (playericon.get()) {
                if (mc.getSession().getUsername().equals("Matejko06")) {
                    currentSmallImage = SmallImage.Matejko06;
                } else if (mc.getSession().getUsername().equals("GeekieCoder")) {
                    currentSmallImage = SmallImage.GeekieCoder;
                } else if (mc.getSession().getUsername().equals("ludanecek")) {
                    currentSmallImage = SmallImage.Ludanecek;
                } else {
                    currentSmallImage = SmallImage.NotClientDev;
                }
            } else {
                currentSmallImage = SmallImage.NotClientDev;
            }
            currentSmallImage.apply();
            instance.Discord_UpdatePresence(rpc);

            ticks = 0;
        }

        updateDetails();
        instance.Discord_RunCallbacks();
    }

    private void updateDetails() {
        if (isActive() && Utils.canUpdate()) {
            rpc.details = Placeholders.apply(line1);
            rpc.state = Placeholders.apply(line2);

            instance.Discord_UpdatePresence(rpc);
        }
    }

    private enum SmallImage {
        Matejko06("matejko06", "Developer: Matejko06"),
        GeekieCoder("geekiecoder", "Developer: GeekieCoder"),
        Ludanecek("ludanecek","Friend: ludanecek"),
        NotClientDev("", "");

        private final String key, text;

        SmallImage(String key, String text) {
            this.key = key;
            this.text = text;
        }

        void apply() {
            rpc.smallImageKey = key;
            rpc.smallImageText = text;
        }
    }
}

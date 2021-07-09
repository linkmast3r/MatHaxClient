package mathax.client;

import mathax.client.events.game.GameLeftEvent;
import mathax.client.events.mathax.CharTypedEvent;
import mathax.client.events.mathax.ClientInitialisedEvent;
import mathax.client.events.mathax.KeyEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.gui.GuiThemes;
import mathax.client.gui.renderer.GuiRenderer;
import mathax.client.gui.tabs.Tabs;
import mathax.client.renderer.Fonts;
import mathax.client.renderer.PostProcessRenderer;
import mathax.client.renderer.Renderer2D;
import mathax.client.renderer.Shaders;
import mathax.client.systems.Systems;
import mathax.client.systems.config.Config;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Modules;
import mathax.client.systems.modules.misc.DiscordPresence;
import mathax.client.systems.modules.render.hud.HUD;
import mathax.client.utils.Utils;
import mathax.client.utils.misc.FakeClientPlayer;
import mathax.client.utils.misc.Names;
import mathax.client.utils.misc.input.KeyAction;
import mathax.client.utils.misc.input.KeyBinds;
import mathax.client.utils.network.Capes;
import mathax.client.utils.network.MatHaxExecutor;
import mathax.client.utils.network.OnlinePlayers;
import mathax.client.utils.player.DamageUtils;
import mathax.client.utils.player.EChestMemory;
import mathax.client.utils.player.Rotations;
import mathax.client.utils.render.Outlines;
import mathax.client.utils.render.color.RainbowColors;
import mathax.client.utils.windowutils.IconExport;
import mathax.client.utils.windowutils.WindowImage;
import mathax.client.utils.world.BlockIterator;
import mathax.client.utils.world.BlockUtils;
import mathax.client.bus.EventBus;
import mathax.client.bus.EventHandler;
import mathax.client.bus.IEventBus;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import static mathax.client.utils.Utils.mc;

public class MatHaxClient implements ClientModInitializer {
    public static MatHaxClient INSTANCE;
    public static final IEventBus EVENT_BUS = new EventBus();
    public static final File MCCONFIG_FOLDER = new File(net.fabricmc.loader.FabricLoader.INSTANCE.getGameDirectory().toString(), "config/MatHax");
    public static final File FOLDER = new File(FabricLoader.getInstance().getGameDir().toString(), "MatHax");
    public static final File VERSION_FOLDER = new File(FOLDER + "/" + getMinecraftVersion());
    public static final Logger LOG = LogManager.getLogger();

    public static Screen screenToOpen;

    public static String logprefix = "[MatHax] ";

    public static String versionNumber = "1.0.0";
    public static String devBuildNumber = "12";

    public static String devbuild = " Dev Build " + devBuildNumber + " ";
    //public static String devbuild = "";

    public static String clientversion = versionNumber + devbuild;

    public static String discorddevbuild = devbuild + "- ";
    //public static String discorddevbuild = "";

    public static String discordversion = " " + "v" + versionNumber + discorddevbuild + MinecraftClient.getInstance().getVersionType() + " " + getMinecraftVersion();

    @Override
    public void onInitializeClient() {
        if (INSTANCE == null) {
            INSTANCE = this;
            return;
        }

        LOG.info(logprefix + "Initializing MatHax Client...");

        Utils.mc = MinecraftClient.getInstance();
        EVENT_BUS.registerLambdaFactory("mathax.client", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));

        List<MatHaxAddon> addons = new ArrayList<>();
        for (EntrypointContainer<MatHaxAddon> entrypoint : FabricLoader.getInstance().getEntrypointContainers("mathax", MatHaxAddon.class)) {
            addons.add(entrypoint.getEntrypoint());
        }
        LOG.info(logprefix + "10% initialized!");

        IconExport.reset();
        IconExport.init();
        mc.getInstance().execute(this::updateTitleLoading);
        mc.getInstance().execute(this::updateImage);

        Systems.addPreLoadTask(() -> {
            if (!Modules.get().getFile().exists()) {
                Modules.get().get(HUD.class).toggle(false);
                Modules.get().get(DiscordPresence.class).toggle(false);
                //Utils.addMatHaxPvpToServerList();
            }
        });

        LOG.info(logprefix + "20% initialized!");

        Shaders.init();
        Renderer2D.init();
        Outlines.init();
        LOG.info(logprefix + "30% initialized!");

        MatHaxExecutor.init();
        Capes.init();
        LOG.info(logprefix + "40% initialized!");
        RainbowColors.init();
        BlockIterator.init();
        EChestMemory.init();
        Rotations.init();
        LOG.info(logprefix + "50% initialized!");
        Names.init();
        FakeClientPlayer.init();
        PostProcessRenderer.init();
        Tabs.init();
        LOG.info(logprefix + "60% initialized!");
        GuiThemes.init();
        Fonts.init();
        DamageUtils.init();
        BlockUtils.init();

        LOG.info(logprefix + "70% initialized!");

        // Register categories
        Modules.REGISTERING_CATEGORIES = true;
        Categories.register();
        addons.forEach(MatHaxAddon::onRegisterCategories);
        Modules.REGISTERING_CATEGORIES = false;

        Systems.init();
        LOG.info(logprefix + "80% initialized!");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            OnlinePlayers.leave();
            Systems.save();
            GuiThemes.save();
        }));

        LOG.info(logprefix + "90% initialized!");
        mc.getInstance().execute(this::updateTitleLoaded);

        EVENT_BUS.subscribe(this);
        EVENT_BUS.post(new ClientInitialisedEvent()); // TODO: This is there just for compatibility

        // Call onInitialize for addons
        addons.forEach(MatHaxAddon::onInitialize);

        Modules.get().sortModules();
        Systems.load();

        Fonts.load();
        GuiRenderer.init();
        GuiThemes.postInit();

        LOG.info(logprefix + "100% initialized!");
        mc.getInstance().execute(this::updateTitle);

        LOG.info(logprefix + "MatHax Client initialized!");
    }

    private final WindowImage windowimageconfig;

    public MatHaxClient() throws IOException {

        windowimageconfig = WindowImage.read(MCCONFIG_FOLDER + "/Icons/Window");

    }

    static String getMinecraftVersion(){
        return SharedConstants.getGameVersion().getName();
    }

    public void updateImage() {
        final Window window = MinecraftClient.getInstance().getWindow();
        if(MinecraftClient.getInstance().getSession().getUsername().equals("Matejko06")) {
            window.setIcon(windowimageconfig.readIcon64(), windowimageconfig.readIcon128());
        } else {
            window.setIcon(windowimageconfig.readIcon16(), windowimageconfig.readIcon32());
        }

    }

    public void updateTitleLoading() {
        final Window window = MinecraftClient.getInstance().getWindow();
        window.setTitle("Loading MatHax Client v" + clientversion + "...".replace(devBuildNumber + " ...", devBuildNumber + "..."));
    }

    public void updateTitleLoaded() {
        final Window window = MinecraftClient.getInstance().getWindow();
        window.setTitle("MatHax Client v" + clientversion + " loaded!");
    }

    public void updateTitle() {
        final Window window = MinecraftClient.getInstance().getWindow();
        window.setTitle("MatHax Client v" + clientversion + " - " + MinecraftClient.getInstance().getVersionType() + " " + getMinecraftVersion());
    }

    private void openClickGui() {
        Tabs.get().get(0).openScreen(GuiThemes.get());
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        Systems.save();
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        Capes.tick();

        if (screenToOpen != null && mc.currentScreen == null) {
            mc.openScreen(screenToOpen);
            screenToOpen = null;
        }

        if (Utils.canUpdate()) {
            mc.player.getActiveStatusEffects().values().removeIf(statusEffectInstance -> statusEffectInstance.getDuration() <= 0);
        }
    }

    @EventHandler
    private void onKey(KeyEvent event) {
        // Click GUI
        if (event.action == KeyAction.Press && KeyBinds.OPEN_CLICK_GUI.matchesKey(event.key, 0)) {
            if (!Utils.canUpdate() && Utils.isWhitelistedScreen() || mc.currentScreen == null) openClickGui();
        }
    }

    @EventHandler
    private void onCharTyped(CharTypedEvent event) {
        if (mc.currentScreen != null) return;
        if (!Config.get().openChatOnPrefix) return;

        if (event.c == Config.get().prefix.charAt(0)) {
            mc.openScreen(new ChatScreen(Config.get().prefix));
            event.cancel();
        }
    }
}

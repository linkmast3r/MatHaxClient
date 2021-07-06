package matejko06.mathax;

import matejko06.mathax.events.game.GameLeftEvent;
import matejko06.mathax.events.mathax.CharTypedEvent;
import matejko06.mathax.events.mathax.ClientInitialisedEvent;
import matejko06.mathax.events.mathax.KeyEvent;
import matejko06.mathax.events.world.TickEvent;
import matejko06.mathax.gui.GuiThemes;
import matejko06.mathax.gui.renderer.GuiRenderer;
import matejko06.mathax.gui.tabs.Tabs;
import matejko06.mathax.renderer.Fonts;
import matejko06.mathax.renderer.PostProcessRenderer;
import matejko06.mathax.renderer.Renderer2D;
import matejko06.mathax.renderer.Shaders;
import matejko06.mathax.systems.Systems;
import matejko06.mathax.systems.config.Config;
import matejko06.mathax.systems.modules.Categories;
import matejko06.mathax.systems.modules.Modules;
import matejko06.mathax.systems.modules.misc.DiscordPresence;
import matejko06.mathax.systems.modules.render.hud.HUD;
import matejko06.mathax.utils.Utils;
import matejko06.mathax.utils.misc.FakeClientPlayer;
import matejko06.mathax.utils.misc.Names;
import matejko06.mathax.utils.misc.input.KeyAction;
import matejko06.mathax.utils.misc.input.KeyBinds;
import matejko06.mathax.utils.network.Capes;
import matejko06.mathax.utils.network.MatHaxExecutor;
import matejko06.mathax.utils.network.OnlinePlayers;
import matejko06.mathax.utils.player.DamageUtils;
import matejko06.mathax.utils.player.EChestMemory;
import matejko06.mathax.utils.player.Rotations;
import matejko06.mathax.utils.render.Outlines;
import matejko06.mathax.utils.render.color.RainbowColors;
import matejko06.mathax.utils.world.BlockIterator;
import matejko06.mathax.utils.world.BlockUtils;
import matejko06.mathax.bus.EventBus;
import matejko06.mathax.bus.EventHandler;
import matejko06.mathax.bus.IEventBus;
import net.fabricmc.api.ClientModInitializer;
import matejko06.mathax.utils.windowutils.*;
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

import static matejko06.mathax.utils.Utils.mc;

public class MatHaxClient implements ClientModInitializer {
    public static MatHaxClient INSTANCE;
    public static final IEventBus EVENT_BUS = new EventBus();
    public static final File FOLDER = new File(FabricLoader.getInstance().getGameDir().toString(), "MatHax");
    public static final Logger LOG = LogManager.getLogger();

    public static Screen screenToOpen;

    public static String logprefix = "[MatHax] ";

    public static String versionNumber = "1.0.0";
    public static String devbuildNumber = "10";

    public static String devbuild = " Dev Build " + devbuildNumber + " ";
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

        LOG.info(logprefix + "Initializing MatHax Client");

        Utils.mc = MinecraftClient.getInstance();
        EVENT_BUS.registerLambdaFactory("matejko06.mathax", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));

        List<MatHaxAddon> addons = new ArrayList<>();
        for (EntrypointContainer<MatHaxAddon> entrypoint : FabricLoader.getInstance().getEntrypointContainers("MatHax", MatHaxAddon.class)) {
            addons.add(entrypoint.getEntrypoint());
        }

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

        Shaders.init();
        Renderer2D.init();
        Outlines.init();

        MatHaxExecutor.init();
        Capes.init();
        RainbowColors.init();
        BlockIterator.init();
        EChestMemory.init();
        Rotations.init();
        Names.init();
        FakeClientPlayer.init();
        PostProcessRenderer.init();
        Tabs.init();
        GuiThemes.init();
        Fonts.init();
        DamageUtils.init();
        BlockUtils.init();

        // Register categories
        Modules.REGISTERING_CATEGORIES = true;
        Categories.register();
        addons.forEach(MatHaxAddon::onRegisterCategories);
        Modules.REGISTERING_CATEGORIES = false;

        Systems.init();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            OnlinePlayers.leave();
            Systems.save();
            GuiThemes.save();
        }));

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

        mc.getInstance().execute(this::updateTitle);

        LOG.info(logprefix + "MatHax Client initialized!");
    }

    private final WindowImage windowimageconfig;

    public MatHaxClient() throws IOException {

        windowimageconfig = WindowImage.read(FOLDER + "/textures/icons/window");

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
        window.setTitle("Loading MatHax Client v" + clientversion + "...");
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

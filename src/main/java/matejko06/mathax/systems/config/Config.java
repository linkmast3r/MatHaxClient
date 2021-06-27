package matejko06.mathax.systems.config;

import com.g00fy2.versioncompare.Version;
import matejko06.mathax.gui.tabs.builtin.ConfigTab;
import matejko06.mathax.settings.Setting;
import matejko06.mathax.systems.System;
import matejko06.mathax.systems.Systems;
import matejko06.mathax.utils.render.color.RainbowColors;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.nbt.NbtCompound;

public class Config extends System<Config> {
    public final Version version;
    public final String devBuild;

    public String font = ConfigTab.font.get();
    public boolean customFont = ConfigTab.customFont.get();
    public int rotationHoldTicks = ConfigTab.rotationHoldTicks.get();

    public String prefix = ConfigTab.prefix.get();
    public boolean openChatOnPrefix = ConfigTab.openChatOnPrefix.get();
    public boolean chatCommandsInfo = ConfigTab.chatCommandsInfo.get();
    public boolean deleteChatCommandsInfo = ConfigTab.deleteChatCommandsInfo.get();
    public boolean rainbowPrefix = ConfigTab.rainbowPrefix.get();

    public boolean useTeamColor = ConfigTab.useTeamColor.get();

    public Config() {
        super("config");

        /*ModMetadata metadata = FabricLoader.getInstance().getModContainer("MatHax").get().getMetadata();

        String versionString = metadata.getVersion().getFriendlyString();
        if (versionString.contains("-")) versionString = versionString.split("-")[0];*/

        version = new Version(" 1.0.0");
        devBuild = "7";
    }

    public static Config get() {
        return Systems.get(Config.class);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();
        tag.putString("version", version.getOriginalString());

        tag.putString("font", font);
        tag.putBoolean("customFont", customFont);
        tag.putDouble("rainbowSpeed", RainbowColors.GLOBAL.getSpeed());
        tag.putInt("rotationHoldTicks", rotationHoldTicks);

        tag.putString("prefix", prefix);
        tag.putBoolean("openChatOnPrefix", openChatOnPrefix);
        tag.putBoolean("chatCommandsInfo", chatCommandsInfo);
        tag.putBoolean("deleteChatCommandsInfo", deleteChatCommandsInfo);
        tag.putBoolean("rainbowPrefix", rainbowPrefix);

        tag.putBoolean("useTeamColor", useTeamColor);

        return tag;
    }

    @Override
    public Config fromTag(NbtCompound tag) {
        font = getString(tag, "font", ConfigTab.font);
        customFont = getBoolean(tag, "customFont", ConfigTab.customFont);
        RainbowColors.GLOBAL.setSpeed(tag.contains("rainbowSpeed") ? tag.getDouble("rainbowSpeed") : ConfigTab.rainbowSpeed.getDefaultValue() / 100);
        rotationHoldTicks = getInt(tag, "rotationHoldTicks", ConfigTab.rotationHoldTicks);

        prefix = getString(tag, "prefix", ConfigTab.prefix);
        openChatOnPrefix = getBoolean(tag, "openChatOnPrefix", ConfigTab.openChatOnPrefix);
        chatCommandsInfo = getBoolean(tag, "chatCommandsInfo", ConfigTab.chatCommandsInfo);
        deleteChatCommandsInfo = getBoolean(tag, "deleteChatCommandsInfo", ConfigTab.deleteChatCommandsInfo);
        rainbowPrefix = getBoolean(tag, "rainbowPrefix", ConfigTab.rainbowPrefix);

        useTeamColor = getBoolean(tag, "useTeamColor", ConfigTab.useTeamColor);

        return this;
    }

    private boolean getBoolean(NbtCompound tag, String key, Setting<Boolean> setting) {
        return tag.contains(key) ? tag.getBoolean(key) : setting.get();
    }

    private String getString(NbtCompound tag, String key, Setting<String> setting) {
        return tag.contains(key) ? tag.getString(key) : setting.get();
    }

    private double getDouble(NbtCompound tag, String key, Setting<Double> setting) {
        return tag.contains(key) ? tag.getDouble(key) : setting.get();
    }

    private int getInt(NbtCompound tag, String key, Setting<Integer> setting) {
        return tag.contains(key) ? tag.getInt(key) : setting.get();
    }
}

package matejko06.mathax.utils.misc;

import matejko06.mathax.MatHaxClient;
import matejko06.mathax.systems.config.Config;
import matejko06.mathax.utils.Utils;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.screen.TitleScreen;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static matejko06.mathax.utils.Utils.getWorldName;
import static matejko06.mathax.utils.Utils.mc;

public class Placeholders {
    private static final Pattern pattern = Pattern.compile("(%version%|%mc_version%|%player%|%username%|%server%)");

    public static String apply(String string) {
        Matcher matcher = pattern.matcher(string);
        StringBuffer sb = new StringBuffer(string.length());

        while (matcher.find()) {
            matcher.appendReplacement(sb, getReplacement(matcher.group(1)));
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    private static String getReplacement(String placeholder) {
        switch (placeholder) {
            case "%version%":    return MatHaxClient.clientversion;
            case "%mc_version%": return SharedConstants.getGameVersion().getName();
            case "%player%":
            case "%username%":   return mc.getSession().getUsername();
            case "%server%":     if (mc.currentScreen instanceof TitleScreen) { return "In Menu"; } else { return Utils.getWorldName(); }
            default:             return "";
        }
    }
}

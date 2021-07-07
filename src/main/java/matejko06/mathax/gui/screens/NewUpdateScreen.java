package matejko06.mathax.gui.screens;

import matejko06.mathax.gui.GuiTheme;
import matejko06.mathax.gui.WindowScreen;
import matejko06.mathax.gui.widgets.containers.WHorizontalList;
import matejko06.mathax.gui.widgets.containers.WTable;
import matejko06.mathax.systems.config.Config;
import matejko06.mathax.utils.misc.Version;
import net.minecraft.util.Util;

public class NewUpdateScreen extends WindowScreen {
    public NewUpdateScreen(GuiTheme theme, Version latestVer) {
        super(theme, "New Update");

        add(theme.label("A new version of MatHax has been released."));

        add(theme.horizontalSeparator()).expandX();

        WTable versionsT = add(theme.table()).widget();
        versionsT.add(theme.label(""));
        versionsT.row();
        versionsT.add(theme.label("Your version: "));
        versionsT.add(theme.label("v" + Config.get().version.toString()));
        versionsT.row();
        versionsT.add(theme.label("Latest version: "));
        versionsT.add(theme.label("v" + latestVer.toString()));
        versionsT.row();
        versionsT.add(theme.label(""));

        add(theme.horizontalSeparator()).expandX();

        WHorizontalList buttonsL = add(theme.horizontalList()).widget();
        buttonsL.add(theme.button("Download v" + latestVer.toString())).expandX().widget().action = () -> Util.getOperatingSystem().open("https://mathaxclient.xyz/");
        buttonsL.add(theme.button("OK")).expandX().widget().action = this::onClose;
    }
}


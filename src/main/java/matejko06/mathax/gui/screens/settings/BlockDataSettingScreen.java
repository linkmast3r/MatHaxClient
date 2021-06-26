package matejko06.mathax.gui.screens.settings;

import matejko06.mathax.gui.GuiTheme;
import matejko06.mathax.gui.WindowScreen;
import matejko06.mathax.gui.renderer.GuiRenderer;
import matejko06.mathax.gui.widgets.containers.WTable;
import matejko06.mathax.gui.widgets.input.WTextBox;
import matejko06.mathax.gui.widgets.pressable.WButton;
import matejko06.mathax.settings.BlockDataSetting;
import matejko06.mathax.settings.IBlockData;
import matejko06.mathax.utils.misc.IChangeable;
import matejko06.mathax.utils.misc.ICopyable;
import matejko06.mathax.utils.misc.ISerializable;
import matejko06.mathax.utils.misc.Names;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static matejko06.mathax.utils.Utils.mc;

public class BlockDataSettingScreen extends WindowScreen {
    private static final List<Block> BLOCKS = new ArrayList<>(100);

    private final BlockDataSetting<?> setting;

    private WTable table;
    private String filterText = "";

    public BlockDataSettingScreen(GuiTheme theme, BlockDataSetting<?> setting) {
        super(theme, "Configure Blocks");

        this.setting = setting;

        WTextBox filter = add(theme.textBox("")).minWidth(400).expandX().widget();
        filter.setFocused(true);
        filter.action = () -> {
            filterText = filter.get().trim();

            table.clear();
            initWidgets();
        };

        table = add(theme.table()).expandX().widget();
    }

    @Override
    protected void init() {
        super.init();

        table.clear();
        initWidgets();
    }

    private <T extends ICopyable<T> & ISerializable<T> & IChangeable & IBlockData<T>> void initWidgets() {
        for (Block block : Registry.BLOCK) {
            T blockData = (T) setting.get().get(block);

            if (blockData != null && blockData.isChanged()) BLOCKS.add(0, block);
            else BLOCKS.add(block);
        }

        for (Block block : BLOCKS) {
            String name = Names.get(block);
            if (!StringUtils.containsIgnoreCase(name, filterText)) continue;

            T blockData = (T) setting.get().get(block);

            table.add(theme.itemWithLabel(block.asItem().getDefaultStack(), Names.get(block))).expandCellX();
            table.add(theme.label((blockData != null && blockData.isChanged()) ? "*" : " "));

            WButton edit = table.add(theme.button(GuiRenderer.EDIT)).widget();
            edit.action = () -> {
                T data = blockData;
                if (data == null) data = (T) setting.defaultData.get().copy();

                mc.openScreen(data.createScreen(theme, block, (BlockDataSetting<T>) setting));
            };

            WButton reset = table.add(theme.button(GuiRenderer.RESET)).widget();
            reset.action = () -> {
                setting.get().remove(block);
                setting.changed();

                if (blockData != null && blockData.isChanged()) {
                    table.clear();
                    initWidgets();
                }
            };

            table.row();
        }

        BLOCKS.clear();
    }
}

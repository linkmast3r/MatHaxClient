package matejko06.mathax.settings;

import matejko06.mathax.gui.GuiTheme;
import matejko06.mathax.gui.WidgetScreen;
import matejko06.mathax.utils.misc.IChangeable;
import matejko06.mathax.utils.misc.ICopyable;
import matejko06.mathax.utils.misc.ISerializable;
import net.minecraft.block.Block;

public interface IBlockData<T extends ICopyable<T> & ISerializable<T> & IChangeable & IBlockData<T>> {
    WidgetScreen createScreen(GuiTheme theme, Block block, BlockDataSetting<T> setting);
}

package matejko06.mathax.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import matejko06.mathax.systems.Systems;
import matejko06.mathax.systems.commands.Command;
import matejko06.mathax.utils.network.Capes;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ReloadCommand extends Command {
    public ReloadCommand() {
        super("reload", "Reloads the config, modules, friends, macros, accounts and capes.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            Systems.load();
            Capes.init();

            return SINGLE_SUCCESS;
        });
    }
}

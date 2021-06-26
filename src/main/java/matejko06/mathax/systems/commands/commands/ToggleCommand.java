package matejko06.mathax.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import matejko06.mathax.systems.commands.Command;
import matejko06.mathax.systems.commands.arguments.ModuleArgumentType;
import matejko06.mathax.systems.modules.Module;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class ToggleCommand extends Command {


    public ToggleCommand() {
        super("toggle", "Toggles a module.", "t");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(argument("module", ModuleArgumentType.module())
                .executes(context -> {
                    Module m = ModuleArgumentType.getModule(context, "module");
                    m.toggle();
                    return SINGLE_SUCCESS;
                }).then(literal("on")
                        .executes(context -> {
                            Module m = ModuleArgumentType.getModule(context, "module");
                            if (!m.isActive()) m.toggle();
                            return SINGLE_SUCCESS;
                        })).then(literal("off")
                        .executes(context -> {
                            Module m = ModuleArgumentType.getModule(context, "module");
                            if (m.isActive()) m.toggle();
                            return SINGLE_SUCCESS;
                        })
                )
        );
    }
}

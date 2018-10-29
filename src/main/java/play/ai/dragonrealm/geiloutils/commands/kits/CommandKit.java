package play.ai.dragonrealm.geiloutils.commands.kits;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandKit extends CommandBase {

    @Override
    public String getName() {
        return "kit";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Gives you your kits";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

    }
}

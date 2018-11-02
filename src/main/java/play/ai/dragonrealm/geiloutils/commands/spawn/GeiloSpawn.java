package play.ai.dragonrealm.geiloutils.commands.spawn;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class GeiloSpawn extends CommandBase {
    @Override
    public String getName() {
        return "geilospawn";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Used to spawn mobs";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 2 && args[0].equals("list")){

        }
    }
}

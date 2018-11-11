package play.ai.dragonrealm.geiloutils.commands.geilokill;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class GeiloKill extends CommandBase {
    @Override
    public String getName() {
        return "geilokill";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Kills a player even if its name is Miradae";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player = (EntityPlayer) sender;
        //player.onkil
    }
}

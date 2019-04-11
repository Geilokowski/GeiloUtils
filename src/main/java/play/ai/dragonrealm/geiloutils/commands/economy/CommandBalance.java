package play.ai.dragonrealm.geiloutils.commands.economy;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;

public class CommandBalance extends CmdBase {

	@Override
	public String getName() {
	    return "balance";
	}

	@Override
	public String getUsage(ICommandSender sender) {
	    return "Gives you your current account balance";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if ((sender instanceof EntityPlayer))
	    {
	      EntityPlayer player = (EntityPlayer)sender;
	      sendMsg(sender, " Your balance is: " + getPlayerBalance(player) + "$");
	    }
	}
	
	@Override
	public List<String> getAliases(){
	    ArrayList<String> al = new ArrayList<String>();
	    al.add("bal");
	    al.add("Bal");
	    al.add("Balance");
	    return al;
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }
}

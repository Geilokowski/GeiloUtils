package play.ai.dragonrealm.geiloutils.commands.admin;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.GeiloUtils;

public class CommandGeiloReload extends CommandBase{

	@Override
	public String getName() {
		return "geiloreload";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "Use this to sync the settings from the config";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		GeiloUtils.getManager().readFilesToRuntime();
		sender.sendMessage(new TextComponentString("GeiloUtils Reloaded Configs."));
	}

}

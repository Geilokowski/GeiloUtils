package play.ai.dragonrealm.geiloutils.commands.permissions;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.config.kits.Kit;
import play.ai.dragonrealm.geiloutils.config.permissions.Permission;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigManager;
import play.ai.dragonrealm.geiloutils.utils.PermissionUtils;

public class CommandGeiloPerm extends CommandBase{

	@Override
	public String getName() {
		return "geiloperm";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "Used to create your own custom permissions";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		ITextComponent msg;
		if(args.length > 0) {
			if(args[0].equals("create")) {

					if(args.length == 2) {
						Permission perm = new Permission(args[1]);
						ConfigurationManager.getPermissionsConfig().getPermissions().add(perm);
						ConfigurationManager.syncFromFields();
						
						msg = new TextComponentString(ConfigManager.getGeneralConfig().getCommandPrefix() + "Created the permission '" + perm.getName() + "'");
						sender.sendMessage(msg);
					}else {
						msg = new TextComponentString(ConfigManager.getGeneralConfig().getCommandPrefix() + "Wrong Syntax: /geiloperm create <name>");
						sender.sendMessage(msg);
					}
			}
			
			if((args[0].equals("delete") || args[0].equals("remove"))) {
				if(args.length == 2) {
					if(PermissionUtils.doesPermissionExist(args[1])) {
						if(!PermissionUtils.removePermission(args[1]).equals("")) {
							msg = new TextComponentString(ConfigManager.getGeneralConfig().getCommandPrefix() + "Deleted the permission '" + args[1] + "'");
							sender.sendMessage(msg);
						}else {
							msg = new TextComponentString(ConfigManager.getGeneralConfig().getCommandPrefix() + "Couldnt find the permission '" + args[1] + "'");
							sender.sendMessage(msg);
						}
					}else {
						msg = new TextComponentString(ConfigManager.getGeneralConfig().getCommandPrefix() + "Couldnt find the permission '" + args[1] + "'");
						sender.sendMessage(msg);
					}
				}else {
					msg = new TextComponentString(ConfigManager.getGeneralConfig().getCommandPrefix() + "Wrong syntex: /geiloperm remove <name>");
					sender.sendMessage(msg);
				}
			}
			
			if(args.length == 1 && args[0].equals("list")) {
				msg = new TextComponentString(ConfigManager.getGeneralConfig().getCommandPrefix() + "Found " + ConfigurationManager.getPermissionsConfig().getPermissions().size() + " permissions!");
				sender.sendMessage(msg);
				for(Permission perm : ConfigurationManager.getPermissionsConfig().getPermissions()) {
					msg = new TextComponentString(ConfigManager.getGeneralConfig().getCommandPrefix() + perm.toString());
					sender.sendMessage(msg);
				}
			}
		}else {
			msg = new TextComponentString(ConfigManager.getGeneralConfig().getCommandPrefix() + "Wrong syntax: /geiloperm <remove/create/list> <name>");
			sender.sendMessage(msg);
		}
	}

}

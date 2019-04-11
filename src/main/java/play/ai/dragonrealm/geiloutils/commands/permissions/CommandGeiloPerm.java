package play.ai.dragonrealm.geiloutils.commands.permissions;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.new_configs.models.Permission;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.utils.PermissionUtils;

public class CommandGeiloPerm extends CmdBase {

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
						ConfigAccess.getPermissionConfig().getPermissions().add(perm);
						ConfigAccess.writePermissionFile();
						
						sendMsg(sender, "Created the permission '" + perm.getName() + "'");
						
					}else {
						sendMsg(sender, "Wrong Syntax: /geiloperm create <name>");
						
					}
			}
			
			if((args[0].equals("delete") || args[0].equals("remove"))) {
				if(args.length == 2) {
					if(PermissionUtils.doesPermissionExist(args[1])) {
						if(!PermissionUtils.removePermission(args[1]).equals("")) {
							sendMsg(sender, "Deleted the permission '" + args[1] + "'");
							
						}else {
							sendMsg(sender, "Couldnt find the permission '" + args[1] + "'");
							
						}
					}else {
						sendMsg(sender, "Couldnt find the permission '" + args[1] + "'");
						
					}
				}else {
					sendMsg(sender, "Wrong syntex: /geiloperm remove <name>");
					
				}
			}
			
			if(args.length == 1 && args[0].equals("list")) {
				sendMsg(sender, "Found " + ConfigAccess.getPermissionConfig().getPermissions().size() + " permissions!");
				
				for(Permission perm : ConfigAccess.getPermissionConfig().getPermissions()) {
					sendMsg(sender, perm.toString());
					
				}
			}
		}else {
			sendMsg(sender, "Wrong syntax: /geiloperm <remove/create/list> <name>");
			
		}
	}

}

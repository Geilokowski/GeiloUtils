package play.ai.dragonrealm.geiloutils.commands.kits;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.config.kits.Kit;
import play.ai.dragonrealm.geiloutils.config.kits.KitItem;
import play.ai.dragonrealm.geiloutils.config.permissions.Permission;
import play.ai.dragonrealm.geiloutils.utils.KitUtils;

public class CommandGeiloKit extends CommandBase{

	@Override
	public String getName() {
		return "geilokit";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "Manage your kits";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		ITextComponent msg;
		if(args.length == 2 && args[0].equals("create") && !args[1].equals(null)) {
			if(KitUtils.doesKitExist(args[1])) {
				msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "This kit already exists");
				sender.sendMessage(msg);
				return;
			}else {
				Kit kit = new Kit();
				kit.setName(args[1]);
				ConfigurationManager.getKitsConfig().getKits().add(kit);
				ConfigurationManager.syncFromFields();
				
				msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Created the kit '" + kit.getName() + "'");
				sender.sendMessage(msg);
			}
		}
		
		if(args.length == 2 && args[0].equals("delete") && !args[1].equals(null)) {
			String kit = KitUtils.removeKitByName(args[1]); 
			if(!kit.equals("")) {
				ConfigurationManager.syncFromFields();
				msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Deleted the kit '" + kit + "'");
				sender.sendMessage(msg);
				return;
			}

			msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Couldnt find the kit '" + args[1] + "'. Use /kit list for a list of kits");
			sender.sendMessage(msg);
			
		}
		
		if(args.length == 1 && args[0].equals("list")) {
			msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Found " + KitUtils.getKitCount() + " kits. Use /kit info <kit> to gte more detailed information about a kit");
			sender.sendMessage(msg);
			for(String s : KitUtils.getKitNameList()) {
				msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Kit '" + s + "' found");
				sender.sendMessage(msg);
			}
		}
		
		if(args.length == 3 && args[0].equals("addItem") && !args[1].equals(null) && !args[2].equals(null)) {
			if(KitUtils.doesKitExist(args[1])) {
				
			}
		}
		
		if(args.length == 3 && args[0].equals("addPermission") && !args[1].equals(null) && !args[2].equals(null)) {
			if(KitUtils.doesKitExist(args[1])) {
				
			}
		}
		
		if(args.length == 3 && args[0].equals("removeItem") && !args[1].equals(null) && !args[2].equals(null)) {
			if(KitUtils.doesKitExist(args[1])) {
				
			}
		}
		
		if(args.length == 3 && args[0].equals("removePermission") && !args[1].equals(null) && !args[2].equals(null)) {
			if(KitUtils.doesKitExist(args[1])) {
				
			}
		}
		
		if(args.length == 2 && args[0].equals("info") && !args[1].equals(null)) {
			if(KitUtils.doesKitExist(args[1])) {
				Kit kit = KitUtils.getKitByName(args[1]);
				// Beginning
				msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Kit found! ");
				sender.sendMessage(msg);
				// Name
				msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Name: " + kit.getName());
				sender.sendMessage(msg);
				// Cooldown
				msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Cooldown: " + kit.getCooldown() + "s");
				sender.sendMessage(msg);
				// Permission
				if(kit.getPermissionList().isEmpty()) {
					msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Permission needed: This kit is available to everyone");
					sender.sendMessage(msg);
				}else {
					String tmp = "";
					for(Permission perm : kit.getPermissionList()) {
						tmp = tmp + perm.getName() + ", ";
					}
					msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Permission needed: " + tmp.substring(0, tmp.length() - 1));
					sender.sendMessage(msg);
				}
				// Items
				if(kit.getItems().isEmpty()) {
					msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Items: No items added yet. Add one with /kit addItem <kit> <registryname> <metadata>");
					sender.sendMessage(msg);
				}else {
					String tmp = "";
					for(KitItem kitItem : kit.getItems()) {
						tmp = tmp + kitItem.getRegistryName() + "[" + kitItem.getMetadata() + "]" + ", ";
					}
					msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Items: " + tmp.substring(0, tmp.length() - 1));
					sender.sendMessage(msg);
				}
			}
		}
	}

}

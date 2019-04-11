package play.ai.dragonrealm.geiloutils.commands.ban;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.new_configs.models.BannedBlock;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;

public class CommandGeiloBan extends CommandBase{

	String usage = "/geiloban <list/add/remove> <block name> <metadata/all> <dimension number/all>";
	@Override
	public String getName() {
		return "geiloban";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "Used to ban/unban items from the given dimension.";
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos){
		List<String> tmpList = new ArrayList<>();
		EntityPlayer player;
		if(sender instanceof EntityPlayer) {
			player = (EntityPlayer) sender;
		}else {
			return tmpList;
		}
		
		if(args.length == 1) {
			tmpList.add("add");
			tmpList.add("list");
			tmpList.add("remove");
		}
		if(args.length == 2 && sender instanceof EntityPlayer && (args[0].equals("add") || args[0].equals("remove"))) {
			tmpList.add(player.getHeldItemMainhand().getItem().getRegistryName().toString());
		}
		
		if(args.length == 3 && sender instanceof EntityPlayer && (args[0].equals("add"))) {
			int meta = player.getHeldItemMainhand().getMetadata();
			tmpList.add(String.valueOf(meta));
		}
		
		return tmpList;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length >= 1) {
			ITextComponent msg;
			if (args[0].equals("add")) {
				if(args.length == 4) {
					//TODO: Check if item really exists
					BannedBlock blockToban = new BannedBlock(args[1], args[2], args[3]);
					addBannedBlock(blockToban);
					msg = new TextComponentString("[GeiloBan] " + blockToban.toString());
	        		sender.sendMessage(msg);
				}else {
					msg = new TextComponentString("[GeiloBan] Wrong syntax. Usage: /geiloban add <item name> <metadata/all> <all/dimension id>");
			        sender.sendMessage(msg);
				}
	      }
	      if ((args[0].equals("list")) && (args.length == 1)) {
		        for (BannedBlock bannedBlock : ConfigAccess.getBannedBlocksConfig().getBannedBlocks())
		        {
		        	msg = new TextComponentString("[GeiloBan] " + bannedBlock.toString());
			        sender.sendMessage(msg);
		        }
	      }
	      if (args[0].equals("remove")){
	    	  if(args.length == 2) {
	    		  if(!removeBlockFromBlacklist(args[1]).equals("")){
	    			  msg = new TextComponentString("[GeiloBan] removed " + args[1] + " from the blacklist");
	    			  sender.sendMessage(msg);
	    		  }else {
	    			  msg = new TextComponentString("[GeiloBan] " + args[1] + " wasn't found on the blacklist. To get a list of all banned use /geiloban list");
	    			  sender.sendMessage(msg);
	    		  }
	    	  }else {
	    		  msg = new TextComponentString("[GeiloBan] Wrong syntax. Usage: /geiloban remove <item name>");
			      sender.sendMessage(msg);
	    	  }
	      }
	    }else {
	    	ITextComponent msg = new TextComponentString("[GeiloBan] Wrong syntax: Usage: " + this.usage);
	    	sender.sendMessage(msg);
	    }
	}
	
	private void addBannedBlock(BannedBlock bannedBlock)
	  {
	    ConfigAccess.getBannedBlocksConfig().getBannedBlocks().add(bannedBlock);
	    ConfigAccess.writeBannedBlocksFile();
	  }
	  
	private String removeBlockFromBlacklist(String registryName){
		String returnString = "";
	    for (int i = 0; i < ConfigAccess.getBannedBlocksConfig().getBannedBlocks().size(); i++) {
		    if (ConfigAccess.getBannedBlocksConfig().getBannedBlocks().get(i).getRegistryName().equals(registryName)) {
		    	returnString = ConfigAccess.getBannedBlocksConfig().getBannedBlocks().remove(i).getRegistryName();
		    }
	    }
	    ConfigAccess.writeBannedBlocksFile();
		return returnString;
	}
}

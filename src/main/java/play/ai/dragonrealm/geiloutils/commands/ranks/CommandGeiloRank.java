package play.ai.dragonrealm.geiloutils.commands.ranks;

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
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.new_configs.containers.PlayerStatsConfig;
import play.ai.dragonrealm.geiloutils.new_configs.models.Permission;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;
import play.ai.dragonrealm.geiloutils.new_configs.models.Rank;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.utils.ArrayUtils;
import play.ai.dragonrealm.geiloutils.utils.KitUtils;
import play.ai.dragonrealm.geiloutils.utils.PermissionUtils;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

public class CommandGeiloRank extends CmdBase {

	@Override
	public String getName() {
		return "geilorank";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return null;
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos){
		//TODO: Add addUser and delUser
		List<String> tmpList = new ArrayList<String>();
		EntityPlayer player;
		if(sender instanceof EntityPlayer) {
			player = (EntityPlayer) sender;
		}
		
		if(args.length == 1) {
			tmpList.add("addPerm");
			tmpList.add("addUser");
			tmpList.add("delUser");
			tmpList.add("delPerm");
			tmpList.add("create");
			tmpList.add("list");
			tmpList.add("delete");
			tmpList.add("info");

			return ArrayUtils.startsWith(tmpList, args[0]);
		}

        if(args.length == 2 && (args[0].equals("delete") || args[0].equals("info"))){
            return ArrayUtils.startsWith(PermissionUtils.getRankNameList(), args[1]);
        }

        if(args.length == 3 && args[0].equals("addPerm")){
            return ArrayUtils.startsWith(PermissionUtils.getPermissionNames(), args[2]);
        }

        if(args.length == 2 && args[0].equals("addPerm")){
            return ArrayUtils.startsWith(PermissionUtils.getRankNameList(), args[1]);
        }

        if(args.length == 3 && args[0].equals("delPerm")){
            return ArrayUtils.startsWith(PermissionUtils.getPermissionNamesOfRank(args[1]), args[2]);
        }

        if(args.length == 2 && args[0].equals("delPerm")){
            return ArrayUtils.startsWith(PermissionUtils.getRankNameList(), args[1]);
        }
		
		return tmpList;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		ITextComponent msg;
		if(args.length == 3 && args[0].equals("addUser") && !args[1].equals("") && !args[2].equals("")) {
			if(PermissionUtils.doesRankExist(args[1])) {
				if(PlayerUtils.getPlayerByName(args[2]) != null){
					Rank rank = PermissionUtils.getRankFromName(args[1]);
                    Playerstat ps = PlayerUtils.getPlayerstatByUUID(PlayerUtils.getPlayerByName(args[2]).getCachedUniqueIdString());
                    ps.setRank(rank.getName());
					GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).updatePlayerstat(ps);
                    sendMsg(sender, " Set the rank " + rank.getName() + " for user " + ps.getName());
                    
				}else{
                    sendMsg(sender, " Error: Couln't find player. Try tab completion."); //TODO: Tab Completions
                    
				}
			}else{
				sendMsg(sender, " Error: Couln't find rank. Get a list of all ranks with /geilorank list");
				
			}
		}

		if(args.length == 2 && args[0].equals("delUser") && !args[1].equals("")) {
                if(PlayerUtils.getPlayerByName(args[1]) != null){
                    Playerstat ps = PlayerUtils.getPlayerstatByUUID(PlayerUtils.getPlayerByName(args[1]).getCachedUniqueIdString());
                    ps.setRank(ConfigAccess.getGeneralConfig().getStandardRank());
					GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).updatePlayerstat(ps);

                    sendMsg(sender, " Removed all ranks the player " + ps.getName() + " had");
                    
                }else{
                    sendMsg(sender, " Error: Couln't find player. Try tab completion.");
                    
                }
		}

		if(args.length == 3 && args[0].equals("addPerm") && !args[1].equals("") && !args[2].equals("")) {
			if(PermissionUtils.doesRankExist(args[1])) {
				if(PermissionUtils.doesPermissionExist(args[2])) {
					Rank rank = PermissionUtils.getRankFromName(args[1]);
					if(PermissionUtils.doesRankHavePermission(rank, new Permission(args[2]))) {
						sendMsg(sender, "The rank already has that permission");
						
					}else {
						rank.getPermList().add(new Permission(args[2]));
						PermissionUtils.removeRank(rank.getName());
						ConfigAccess.getRanksConfig().getRanks().add(rank);
						ConfigAccess.writeRanksFile();
						
						sendMsg(sender, "Added the permission '" + args[2] + "'");
						
					}
				}else {
					//TODO: Ask him if he wants to create it now
					sendMsg(sender, "Couldnt find the permission '" + args[2] + "'. You can create it with /geiloperm create " + args[2]);
					
				}
			}else {
				sendMsg(sender, "Couldnt find the rank '" + args[1] + "'");
				
			}
		}
		
		if(args.length == 3 && args[0].equals("delPerm") && !args[1].equals("") && !args[2].equals("")) {
			if(PermissionUtils.doesRankExist(args[1])) {
				if(PermissionUtils.doesPermissionExist(args[2])) {
					Rank rank = PermissionUtils.getRankFromName(args[1]);
					if(PermissionUtils.doesRankHavePermission(rank, new Permission(args[2]))) {
						for(Permission perm : rank.getPermList()) {
							if(perm.getName().equals(args[2])) {
								rank.getPermList().remove(perm);
								break;
							}
						}
						
						PermissionUtils.removeRank(rank.getName());
						ConfigAccess.getRanksConfig().getRanks().add(rank);
						ConfigAccess.writeRanksFile();
						
						sendMsg(sender, "Removed the permission " + args[2] + " from the rank " + args[1]);
						
					}else {
						sendMsg(sender, "The rank doesnt have that permission");
						
					}
				}else {
					sendMsg(sender, "Couldnt find the permission '" + args[2] + "'. You can create it with /geiloperm create " + args[2]);
					
				}
			}else {
				sendMsg(sender, "Couldnt find the rank '" + args[1] + "'");
				
			}
		}
		
		if(args.length == 2 && args[0].equals("create") && !args[1].equals("")) {
			
			if(PermissionUtils.doesRankExist(args[1])) {
				sendMsg(sender, "This rank already exists");
				
				return;
			}else {
				Rank rank = new Rank();
				rank.setName(args[1]);
				ConfigAccess.getRanksConfig().getRanks().add(rank);
				ConfigAccess.writeRanksFile();
				
				sendMsg(sender, "Created the rank '" + rank.getName() + "'");
				
			}
		}
		
		// TODO: Test if this works
		if(args.length == 1 && args[0].equals("list")) {
			sendMsg(sender, "Found " + KitUtils.getKitCount() + " ranks. Use /geilorank info <rank> to gte more detailed information about a rank");
			
			for(String s : PermissionUtils.getRankNameList()) {
				sendMsg(sender, "Rank '" + s + "' found");
				
			}
		}
		
		// TODO: Test if this works
		if(args.length == 2 && args[0].equals("delete") && !args[1].equals("")) {
			if(PermissionUtils.removeRank(args[1]).equals("")) {
				// No rank found
				sendMsg(sender, "Couldnt find the rank '" + args[1] + "'");
				
			}else {
				// Rank found and deleted
				sendMsg(sender, "Removed the rank '" + args[1] + "'");
				
			}
		}
		
		if(args.length == 2 && args[0].equals("info") && !args[1].equals("")) {
			if(PermissionUtils.doesRankExist(args[1])) {
				Rank rank = PermissionUtils.getRankFromName(args[1]);
				// Beginning
				sendMsg(sender, "Rank found! ");
				
				// Name
				sendMsg(sender, "Name: " + rank.getName());
				
				// Cooldown
				sendMsg(sender, "Rank inherits the following rank: " + rank.getInherits());
				
				// Member
				if(PermissionUtils.getUsersWithRank(rank).isEmpty()) {
					sendMsg(sender, "Player who own this rank: None");
					
				}else {
					String tmp = "";
					for(String s : PermissionUtils.getUsersWithRank(rank)) {
						tmp = tmp + s + ", ";
					}
					sendMsg(sender, "Players who have this rank: " + tmp.substring(0, tmp.length() - 1));
					
				}
				// PermissionConfig
				if(rank.getPermList().isEmpty()) {
					sendMsg(sender, "PermissionConfig: No permissions added yet");
					
				}else {
					String tmp = "";
					for(Permission perm : rank.getPermList()) {
						tmp = tmp + perm.getName() + ", ";
					}
					sendMsg(sender, "PermissionConfig: " + tmp.substring(0, tmp.length() - 1));
					
				}
			}else {
				sendMsg(sender, "Couldnt find the rank " + args[1] + ". Use /geilorank list for a list of all ranks available");
				
			}
		}
	}

}

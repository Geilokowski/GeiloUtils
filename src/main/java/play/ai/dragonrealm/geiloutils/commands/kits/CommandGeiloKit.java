package play.ai.dragonrealm.geiloutils.commands.kits;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.new_configs.models.Kit;
import play.ai.dragonrealm.geiloutils.new_configs.models.KitItem;
import play.ai.dragonrealm.geiloutils.new_configs.models.Permission;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.utils.ArrayUtils;
import play.ai.dragonrealm.geiloutils.utils.ItemUtils;
import play.ai.dragonrealm.geiloutils.utils.KitUtils;
import play.ai.dragonrealm.geiloutils.utils.PermissionUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CommandGeiloKit extends CmdBase {

	@Override
	public String getName() {
		return "geilokit";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "Manage your kits";
	}

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos){
        List<String> tmpList = new ArrayList<String>();
        EntityPlayer player;
        if(sender instanceof EntityPlayer) {
            player = (EntityPlayer) sender;
        }

        if(args.length == 1) {
            tmpList.add("addPermission");
            tmpList.add("addItem");
            tmpList.add("addInv");
            tmpList.add("delPermission");
            tmpList.add("delItem");
            tmpList.add("create");
            tmpList.add("list");
            tmpList.add("delete");
            tmpList.add("info");
			tmpList.add("setCooldown");

            return ArrayUtils.startsWith(tmpList, args[0]);
        }

        //TODO: Tab Completion

        return tmpList;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		ITextComponent msg;
		if(args.length == 2 && args[0].equals("addInv")){
		    EntityPlayer player;
		    if(sender instanceof EntityPlayer){
                player = (EntityPlayer) sender;
            }else{
                sendMsg(sender, "Sorry, the parameter addInv is player-only");
                
		        return;
            }

            if(KitUtils.doesKitExist(args[1])) {
                Kit kit = KitUtils.getKitByName(args[1]);
                for(int i = 0; i <= player.inventory.getSizeInventory(); i++){
                    if(!player.inventory.getStackInSlot(i).isEmpty()) {
						if (player.inventory.getStackInSlot(i).getTagCompound() != null) {
							kit.getItems().add(new KitItem(player.inventory.getStackInSlot(i).getItem().getRegistryName().toString(), player.inventory.getStackInSlot(i).getMetadata(), player.inventory.getStackInSlot(i).getCount(), player.inventory.getStackInSlot(i).getTagCompound().toString()));
						} else {
							kit.getItems().add(new KitItem(player.inventory.getStackInSlot(i).getItem().getRegistryName().toString(), player.inventory.getStackInSlot(i).getMetadata(), player.inventory.getStackInSlot(i).getCount()));
						}
					}
                }

                KitUtils.updateKit(kit);
            }
        }

		if(args.length == 3 && args[0].equals("setCooldown")) {
			if(KitUtils.doesKitExist(args[1])) {
				int time = 0;
				try{
					time = Integer.parseInt(args[2]);
				} catch (Exception e){
					sendMsg(sender, "Please put in a number");
					
					return;
				}

				Kit kit = KitUtils.getKitByName(args[1]);
				kit.setCooldown(time);
				KitUtils.updateKit(kit);
				sendMsg(sender, "Cooldown set");
				
			}else{
				sendMsg(sender, "kit not found");
				
			}
		}

		if(args.length == 2 && args[0].equals("create") && !args[1].equals("")) {
			if(KitUtils.doesKitExist(args[1])) {
				sendMsg(sender, "This kit already exists");
				
				return;
			}else {
				Kit kit = new Kit();
				kit.setName(args[1]);
				ConfigAccess.getKitConfig().getKits().add(kit);
				ConfigAccess.writeKitFile();
				
				sendMsg(sender, "Created the kit '" + kit.getName() + "'");
				
			}
		}
		
		if(args.length == 2 && args[0].equals("delete") && !args[1].equals("")) {
			String kit = KitUtils.removeKitByName(args[1]); 
			if(!kit.equals("")) {
				ConfigAccess.writeKitFile();
				sendMsg(sender, "Deleted the kit '" + kit + "'");
				
				return;
			}

			sendMsg(sender, "Couldnt find the kit '" + args[1] + "'. Use /kit list for a list of kits");
			
			
		}
		
		if(args.length == 1 && args[0].equals("list")) {
			sendMsg(sender, "Found " + KitUtils.getKitCount() + " kits. Use /kit info <kit> to gte more detailed information about a kit");
			
			for(String s : KitUtils.getKitNameList()) {
				sendMsg(sender, "Kit '" + s + "' found");
				
			}
		}
		
		if(args.length == 5 && args[0].equals("addItem") && !args[1].equals("") && !args[2].equals("")) {
			if(KitUtils.doesKitExist(args[1])) {
				Kit kit = KitUtils.getKitByName(args[1]);
				if(ItemUtils.doesItemExist(args[2])){
					if(!KitUtils.doesKitHaveItem(kit, new KitItem(args[2], Integer.parseInt(args[3]), 0))){
						kit.getItems().add(new KitItem(args[2], Integer.parseInt(args[3]), Integer.parseInt(args[4])));
						KitUtils.updateKit(kit);

						sendMsg(sender, "Added Item to kit");
						
					}else{
						sendMsg(sender, "Kit already has that item");
						
					}
				}else{
					sendMsg(sender, "Couldn't find the item '" + args[2] + "'. Try tab completion");
					
				}
			}else{
				sendMsg(sender, "Couldn't find kit '" + args[1] + "'. Try /geilokit list");
				
			}
		}
		
		if(args.length == 3 && args[0].equals("addPermission") && !args[1].equals("") && !args[2].equals("")) {
			if(KitUtils.doesKitExist(args[1])) {
				if(PermissionUtils.doesPermissionExist(args[2])){
				    if(!KitUtils.doesKitHavePermission(KitUtils.getKitByName(args[1]), new Permission(args[2]))) {
                        Kit kit = KitUtils.getKitByName(args[1]);
                        kit.getPermissionList().add(new Permission(args[2]));
                        KitUtils.updateKit(kit);

                        sendMsg(sender, "Added permission to kit");
                        
                    }else{
                        sendMsg(sender, "Kit already has that permission");
                        
                    }
				}else{
                    sendMsg(sender, "Couldn't find permissions '" + args[2] + "'. Try /geiloperm list");
                    
                }
			}else{
                sendMsg(sender, "Couldn't find kit '" + args[1] + "'. Try /geilokit list");
                
            }
		}
		
		if(args.length == 4 && args[0].equals("removeItem") && !args[1].equals("") && !args[2].equals("")) {
			if(KitUtils.doesKitExist(args[1])) {
				Kit kit = KitUtils.getKitByName(args[1]);
                if(ItemUtils.doesItemExist(args[2])){
                    KitItem kitItem = new KitItem(args[2], Integer.parseInt(args[3]), 0);
                    if(KitUtils.doesKitHaveItem(kit, kitItem)){
                        KitUtils.updateKit(KitUtils.removeItemFromKit(kit, kitItem));

                        sendMsg(sender, "Removed the item from the Kit");
                        
                    }else{
                        sendMsg(sender, "Kit doesn't have that item");
                        
                    }
                }else{
                    sendMsg(sender, "Couldn't find item '" + args[2] + "'. Try tab completion");
                    
                }
			}else{
                sendMsg(sender, "Couldn't find kit '" + args[1] + "'. Try /geilokit list");
                
            }
		}
		
		if(args.length == 3 && args[0].equals("removePermission") && !args[1].equals("") && !args[2].equals("")) {
			if(KitUtils.doesKitExist(args[1])) {
				if(KitUtils.doesKitExist(args[1])) {
					if(PermissionUtils.doesPermissionExist(args[2])){
						if(KitUtils.doesKitHavePermission(KitUtils.getKitByName(args[1]), new Permission(args[2]))) {
							Kit kit = KitUtils.getKitByName(args[1]);
							KitUtils.removePermissionFromKit(kit, new Permission(args[2]));
							KitUtils.updateKit(kit);

							sendMsg(sender, "Removed permission from kit");
							
						}else{
							sendMsg(sender, "Kit doesn't have that permission");
							
						}
					}else{
						sendMsg(sender, "Couldn't find permissions '" + args[2] + "'. Try /geiloperm list");
						
					}
				}else{
					sendMsg(sender, "Couldn't find kit '" + args[1] + "'. Try /geilokit list");
					
				}
			}
		}
		
		if(args.length == 2 && args[0].equals("info") && !args[1].equals("")) {
			if(KitUtils.doesKitExist(args[1])) {
				Kit kit = KitUtils.getKitByName(args[1]);
				// Beginning
				sendMsg(sender, "Kit found! ");
				
				// Name
				sendMsg(sender, "Name: " + kit.getName());
				
				// Cooldown
				if (kit.getCooldown() < 0) {
					sendMsg(sender, "Cooldown: One time kit");
					
				}else {
					sendMsg(sender, "Cooldown: " + (kit.getCooldown() / 1000) + "s");
					
				}
				// Permission
				if(kit.getPermissionList().isEmpty()) {
					sendMsg(sender, "Permission needed: This kit is available to everyone");
					
				}else {
					String tmp = "";
					for(Permission perm : kit.getPermissionList()) {
						tmp = tmp + perm.getName() + ", ";
					}
					sendMsg(sender, "Permission needed: " + tmp.substring(0, tmp.length() - 2));
					
				}
				// Items
				if(kit.getItems().isEmpty()) {
					sendMsg(sender, "Items: No items added yet. Add one with /kit addItem <kit> <registryname> <metadata>");
					
				}else {
					String tmp = "";
					for(KitItem kitItem : kit.getItems()) {
						tmp = tmp + kitItem.getRegistryName() + "[M:" + kitItem.getMetadata() + "]" + "[C:" + kitItem.getCount() + "]" + ", ";
					}
					sendMsg(sender, "Items: " + tmp.substring(0, tmp.length() - 2));
					
				}
			}else{
                sendMsg(sender, "Couldn't find kit");
                
            }
		}
	}

}

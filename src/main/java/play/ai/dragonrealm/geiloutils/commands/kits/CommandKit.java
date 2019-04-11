package play.ai.dragonrealm.geiloutils.commands.kits;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.new_configs.models.Kit;
import play.ai.dragonrealm.geiloutils.new_configs.models.KitItem;
import play.ai.dragonrealm.geiloutils.new_configs.models.Permission;
import play.ai.dragonrealm.geiloutils.utils.ArrayUtils;
import play.ai.dragonrealm.geiloutils.utils.KitUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CommandKit extends CmdBase {

    @Override
    public String getName() {
        return "kit";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Gives you your kits";
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos){
        List<String> tmpList = new ArrayList<String>();
        EntityPlayer player;
        if(sender instanceof EntityPlayer) {
            player = (EntityPlayer) sender;
        }

        if(args.length == 1) {
            tmpList.add("list");
            tmpList.add("info");

            tmpList.addAll(KitUtils.getKitNameList());
            return ArrayUtils.startsWith(tmpList, args[0]);
        }

        if(args.length == 2) {
            tmpList.addAll(KitUtils.getKitNameList());
            return ArrayUtils.startsWith(tmpList, args[0]);
        }

        //TODO: Tab Completion

        return tmpList;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        ITextComponent msg;
        if(sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            if(args.length == 0){
                List<String> kitNames = KitUtils.getKitNameList();
                int count = 0;
                sendMsg(sender, "You have access to the following kits. Use /kit <kit_name> to receive them.");
                
                for (String name : kitNames) {
                    Kit kit = KitUtils.getKitByName(name);
                    if (kit != null && KitUtils.canPlayerUseKit(player, kit)) {
                        sendMsg(sender, "Kit: " + name);
                        
                        count++;
                    }
                }
                if(count == 0) {
                    sendMsg(sender, "No kits available!");
                    
                }
                return;
            }

            if (args.length == 1 && args[0].equals("list")) {
                sendMsg(sender, "Found " + KitUtils.getKitCount() + " kits. Use /kit info <kit> to gte more detailed information about a kit");
                
                for (String s : KitUtils.getKitNameList()) {
                    sendMsg(sender, "Kit '" + s + "' found");
                    
                }
            }

            if (args.length == 1 && !((args[0].equals("list") || args[0].equals("info")))) {
                if (KitUtils.doesKitExist(args[0])) {
                    Kit kit = KitUtils.getKitByName(args[0]);
                    if(KitUtils.canPlayerUseKit(player, kit)){
                        boolean completed = KitUtils.deliverKit(player, kit);
                        if(completed) {
                            sendMsg(sender, "Kit delivered!");
                        } else {
                            sendMsg(sender, "Not enough inventory space to deliver kit!");
                        }
                        
                    }else{
                        sendMsg(sender, "Error: No permission, kit already used or cooldown still active");
                        
                    }
                } else {
                    sendMsg(sender, "Kit not found");
                    
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

        }else{
            sendMsg(sender, "Sorry, this is a play only command");
            
        }
    }
}

package play.ai.dragonrealm.geiloutils.commands.kits;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.new_configs.models.Kit;
import play.ai.dragonrealm.geiloutils.new_configs.models.KitItem;
import play.ai.dragonrealm.geiloutils.new_configs.models.Permission;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.utils.ArrayUtils;
import play.ai.dragonrealm.geiloutils.utils.KitUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CommandKit extends CommandBase {

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
                msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "You have access to the following kits. Use /kit <kit_name> to receive them.");
                sender.sendMessage(msg);
                for (String name : kitNames) {
                    Kit kit = KitUtils.getKitByName(name);
                    if (kit != null && KitUtils.canPlayerUseKit(player, kit)) {
                        msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "Kit: " + name);
                        sender.sendMessage(msg);
                        count++;
                    }
                }
                if(count == 0) {
                    msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "No kits available!");
                    sender.sendMessage(msg);
                }
                return;
            }

            if (args.length == 1 && args[0].equals("list")) {
                msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "Found " + KitUtils.getKitCount() + " kits. Use /kit info <kit> to gte more detailed information about a kit");
                sender.sendMessage(msg);
                for (String s : KitUtils.getKitNameList()) {
                    msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "Kit '" + s + "' found");
                    sender.sendMessage(msg);
                }
            }

            if (args.length == 1 && !(args[0].equals("list"))) {
                if (KitUtils.doesKitExist(args[0])) {
                    Kit kit = KitUtils.getKitByName(args[0]);
                    if(KitUtils.canPlayerUseKit(player, kit)){
                        boolean completed = KitUtils.deliverKit(player, kit);
                        if(completed) {
                            msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "Kit delivered!");
                        } else {
                            msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "Not enough inventory space to deliver kit!");
                        }
                        sender.sendMessage(msg);
                    }else{
                        msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "Error: No permission, kit already used or cooldown still active");
                        sender.sendMessage(msg);
                    }
                } else {
                    msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "Kit not found");
                    sender.sendMessage(msg);
                }
            }

            if(args.length == 2 && args[0].equals("info") && !args[1].equals("")) {
                if(KitUtils.doesKitExist(args[1])) {
                    Kit kit = KitUtils.getKitByName(args[1]);
                    // Beginning
                    msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "Kit found! ");
                    sender.sendMessage(msg);
                    // Name
                    msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "Name: " + kit.getName());
                    sender.sendMessage(msg);
                    // Cooldown
                    if (kit.getCooldown() < 0) {
                        msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "Cooldown: One time kit");
                        sender.sendMessage(msg);
                    }else {
                        msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "Cooldown: " + (kit.getCooldown() / 1000) + "s");
                        sender.sendMessage(msg);
                    }
                    // Permission
                    if(kit.getPermissionList().isEmpty()) {
                        msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "Permission needed: This kit is available to everyone");
                        sender.sendMessage(msg);
                    }else {
                        String tmp = "";
                        for(Permission perm : kit.getPermissionList()) {
                            tmp = tmp + perm.getName() + ", ";
                        }
                        msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "Permission needed: " + tmp.substring(0, tmp.length() - 2));
                        sender.sendMessage(msg);
                    }
                    // Items
                    if(kit.getItems().isEmpty()) {
                        msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "Items: No items added yet. Add one with /kit addItem <kit> <registryname> <metadata>");
                        sender.sendMessage(msg);
                    }else {
                        String tmp = "";
                        for(KitItem kitItem : kit.getItems()) {
                            tmp = tmp + kitItem.getRegistryName() + "[M:" + kitItem.getMetadata() + "]" + "[C:" + kitItem.getCount() + "]" + ", ";
                        }
                        msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "Items: " + tmp.substring(0, tmp.length() - 2));
                        sender.sendMessage(msg);
                    }
                }else{
                    msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "Couldn't find kit");
                    sender.sendMessage(msg);
                }
            }

        }else{
            msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "Sorry, this is a play only command");
            sender.sendMessage(msg);
        }
    }
}

package play.ai.dragonrealm.geiloutils.commands.kits;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.config.kits.Kit;
import play.ai.dragonrealm.geiloutils.config.kits.KitItem;
import play.ai.dragonrealm.geiloutils.config.permissions.Permission;
import play.ai.dragonrealm.geiloutils.config.ranks.Rank;
import play.ai.dragonrealm.geiloutils.utils.ArrayUtils;
import play.ai.dragonrealm.geiloutils.utils.KitUtils;
import play.ai.dragonrealm.geiloutils.utils.PermissionUtils;

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
            if (args.length == 1 && args[0].equals("list")) {
                msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Found " + KitUtils.getKitCount() + " kits. Use /kit info <kit> to gte more detailed information about a kit");
                sender.sendMessage(msg);
                for (String s : KitUtils.getKitNameList()) {
                    msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Kit '" + s + "' found");
                    sender.sendMessage(msg);
                }
            }

            if (args.length == 1 && !(args[0].equals("list"))) {
                if (KitUtils.doesKitExist(args[0])) {
                    Kit kit = KitUtils.getKitByName(args[0]);
                    if(KitUtils.canPlayerUseKit(player, kit)){
                        boolean completed = KitUtils.deliverKit(player, kit);
                        if(completed) {
                            msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Kit devlivered!");
                        } else {
                            msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Not enough inventory space to deliver kit!");
                        }
                        sender.sendMessage(msg);
                    }else{
                        msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Error: No permission, kit already used or coodown still active");
                        sender.sendMessage(msg);
                    }
                } else {
                    msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Kit not found");
                    sender.sendMessage(msg);
                }
            }

            if(args.length == 2 && args[0].equals("info") && !args[1].equals("")) {
                if(KitUtils.doesKitExist(args[1])) {
                    Kit kit = KitUtils.getKitByName(args[1]);
                    // Beginning
                    msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Kit found! ");
                    sender.sendMessage(msg);
                    // Name
                    msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Name: " + kit.getName());
                    sender.sendMessage(msg);
                    // Cooldown
                    if (kit.getCooldown() < 0) {
                        msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Cooldown: One time kit");
                        sender.sendMessage(msg);
                    }else {
                        msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Cooldown: " + (kit.getCooldown() / 1000) + "s");
                        sender.sendMessage(msg);
                    }
                    // Permission
                    if(kit.getPermissionList().isEmpty()) {
                        msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Permission needed: This kit is available to everyone");
                        sender.sendMessage(msg);
                    }else {
                        String tmp = "";
                        for(Permission perm : kit.getPermissionList()) {
                            tmp = tmp + perm.getName() + ", ";
                        }
                        msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Permission needed: " + tmp.substring(0, tmp.length() - 2));
                        sender.sendMessage(msg);
                    }
                    // Items
                    if(kit.getItems().isEmpty()) {
                        msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Items: No items added yet. Add one with /kit addItem <kit> <registryname> <metadata>");
                        sender.sendMessage(msg);
                    }else {
                        String tmp = "";
                        for(KitItem kitItem : kit.getItems()) {
                            tmp = tmp + kitItem.getRegistryName() + "[M:" + kitItem.getMetadata() + "]" + "[C:" + kitItem.getCount() + "]" + ", ";
                        }
                        msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Items: " + tmp.substring(0, tmp.length() - 2));
                        sender.sendMessage(msg);
                    }
                }else{
                    msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Couldn't find kit");
                    sender.sendMessage(msg);
                }
            }

        }else{
            msg = new TextComponentString(ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Sorry, this is a play only command");
            sender.sendMessage(msg);
        }
    }
}

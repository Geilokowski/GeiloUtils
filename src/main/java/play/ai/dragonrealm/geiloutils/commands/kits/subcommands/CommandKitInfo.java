package play.ai.dragonrealm.geiloutils.commands.kits.subcommands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.new_configs.models.Kit;
import play.ai.dragonrealm.geiloutils.new_configs.models.KitItem;
import play.ai.dragonrealm.geiloutils.new_configs.models.Permission;
import play.ai.dragonrealm.geiloutils.utils.KitUtils;

public class CommandKitInfo extends CmdBase {
    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return " ";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 1) {
            if(KitUtils.doesKitExist(args[0])) {
                Kit kit = KitUtils.getKitByName(args[0]);
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

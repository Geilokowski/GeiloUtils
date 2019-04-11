package play.ai.dragonrealm.geiloutils.commands.kits.subcommands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.new_configs.models.Kit;
import play.ai.dragonrealm.geiloutils.new_configs.models.Permission;
import play.ai.dragonrealm.geiloutils.utils.KitUtils;
import play.ai.dragonrealm.geiloutils.utils.PermissionUtils;

public class CommandKitAddPermission extends CmdBase {
    @Override
    public String getName() {
        return "addPernission";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return " ";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 2) {
            if(KitUtils.doesKitExist(args[0])) {
                if(PermissionUtils.doesPermissionExist(args[1])){
                    if(!KitUtils.doesKitHavePermission(KitUtils.getKitByName(args[0]), new Permission(args[1]))) {
                        Kit kit = KitUtils.getKitByName(args[0]);
                        kit.getPermissionList().add(new Permission(args[1]));
                        KitUtils.updateKit(kit);

                        sendMsg(sender, "Added permission to kit");

                    }else{
                        sendMsg(sender, "Kit already has that permission");

                    }
                }else{
                    sendMsg(sender, "Couldn't find permissions '" + args[1] + "'. Try /geiloperm list");

                }
            }else{
                sendMsg(sender, "Couldn't find kit '" + args[0] + "'. Try /geilokit list");

            }
        }
    }
}

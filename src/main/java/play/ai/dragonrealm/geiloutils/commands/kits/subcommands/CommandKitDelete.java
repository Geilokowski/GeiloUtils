package play.ai.dragonrealm.geiloutils.commands.kits.subcommands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.utils.KitUtils;

public class CommandKitDelete extends CmdBase {
    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return " ";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 1) {
            String kit = KitUtils.removeKitByName(args[0]);
            if(!kit.equals("")) {
                ConfigAccess.writeKitFile();
                sendMsg(sender, "Deleted the kit '" + kit + "'");

                return;
            }

            sendMsg(sender, "Couldnt find the kit '" + args[0] + "'. Use /kit list for a list of kits");


        }
    }
}

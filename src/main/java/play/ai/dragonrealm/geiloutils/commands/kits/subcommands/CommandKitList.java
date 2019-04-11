package play.ai.dragonrealm.geiloutils.commands.kits.subcommands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.utils.KitUtils;

public class CommandKitList extends CmdBase {
    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return " ";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        sendMsg(sender, "Found " + KitUtils.getKitCount() + " kits. Use /kit info <kit> to gte more detailed information about a kit");

        for(String s : KitUtils.getKitNameList()) {
            sendMsg(sender, "Kit '" + s + "' found");
        }
    }
}

package play.ai.dragonrealm.geiloutils.commands.multiworld.subcommands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.multiworld.WorldManager;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;

import java.util.List;

public class CommandGeiloWorldList extends CmdBase {
    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args[0].equalsIgnoreCase("list") && args.length == 1){
            List<String> nameList = WorldManager.dimensionNameList();
            String tmp = ConfigAccess.getGeneralConfig().getCommandPrefix() + "Found " + nameList.size() + " dimensions (including non-geilutils one): ";
            for(String name : nameList){
                tmp = tmp + name + "[" + WorldManager.dimensionMap().get(name) + "]" + ", ";
            }

            sendMsg(sender, tmp.substring(0, tmp.length() - 2));
        }
    }
}

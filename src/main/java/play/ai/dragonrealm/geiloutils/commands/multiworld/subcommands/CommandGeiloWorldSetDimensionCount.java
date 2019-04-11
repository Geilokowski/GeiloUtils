package play.ai.dragonrealm.geiloutils.commands.multiworld.subcommands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.utils.PermissionUtils;

public class CommandGeiloWorldSetDimensionCount extends CmdBase {
    @Override
    public String getName() {
        return "setDimensionCount";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 2){
            if(PermissionUtils.doesPermissionExist(args[0])){
                int numberOfDimensions = Integer.parseInt(args[1]);
                if(ConfigAccess.getMultiworldConfig().getNumberOfDimensionForPermission().containsKey(args[0])){
                    ConfigAccess.getMultiworldConfig().getNumberOfDimensionForPermission().replace(args[0], numberOfDimensions);
                }else{
                    ConfigAccess.getMultiworldConfig().getNumberOfDimensionForPermission().put(args[0], numberOfDimensions);
                }

                ConfigAccess.writeMultiworldFile(); //TODO: Remove this
                sendMsg(sender, "Everyone who has the permission '" + args[0] + "' can now create " + numberOfDimensions + " dimensions");

            }else{
                sendMsg(sender, "Couldn't find the permissions '" + args[0] + "'");

            }
        }
    }
}

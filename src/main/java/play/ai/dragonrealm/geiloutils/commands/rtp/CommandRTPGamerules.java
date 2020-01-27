package play.ai.dragonrealm.geiloutils.commands.rtp;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandRTPGamerules extends CommandBase {
    @Override
    public String getName() {
        return "rtprules";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/rtprules <rule name> [value]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 2) {
            switch (args[0]) {
                case "setEnabled":
                    try {
                        boolean updatedValue = parseBoolean(args[1]);
                        ConfigAccess.getRTPConfig().setEnabled(updatedValue);
                        ConfigAccess.writeTeleportFile();
                        String quick = updatedValue ? "enabled" : "disabled";
                        sender.sendMessage(new TextComponentString("[GeiloRTP] Set RTP status to " + quick));
                    } catch (CommandException e) {
                        sender.sendMessage(new TextComponentString("[GeiloRTP] Unable to parse value, use [true|false]"));
                    }
                    break;
                case "setRadius":
                    try {
                        int updatedValue = parseInt(args[1], 0);
                        ConfigAccess.getRTPConfig().setRadius(updatedValue);
                        ConfigAccess.writeTeleportFile();
                        sender.sendMessage(new TextComponentString("[GeiloRTP] Set RTP radius to " + updatedValue));
                    } catch (CommandException e) {
                        sender.sendMessage(new TextComponentString("[GeiloRTP] Unable to parse value, use <int>"));
                    }
                    break;
                case "setMaxTries":
                    try {
                        int updatedValue = parseInt(args[1], -1);
                        ConfigAccess.getRTPConfig().setMaxTries(updatedValue);
                        ConfigAccess.writeTeleportFile();
                        sender.sendMessage(new TextComponentString("[GeiloRTP] Set RTP tries to " + updatedValue));
                    } catch (CommandException e) {
                        sender.sendMessage(new TextComponentString("[GeiloRTP] Unable to parse value, use <int>"));
                    }
                    break;
                default:
                    sender.sendMessage(new TextComponentString("[GeiloRTP] Submitted a non-RTPrule command!"));

            }
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos){
        if(args.length == 1) {
            return getListOfStringsMatchingLastWord(args, "setEnabled", "setRadius", "setMaxTries");
        } else {
            return Collections.emptyList();
        }
    }
}

package play.me.dragonrealm.geiloutils.discord.command.commands;


import net.dv8tion.jda.core.entities.User;
import org.bukkit.command.CommandSender;
import play.me.dragonrealm.geiloutils.GeiloUtils;
import play.me.dragonrealm.geiloutils.discord.command.CommandProcessor;
import play.me.dragonrealm.geiloutils.discord.command.ICommand;

public class HelpCommand implements ICommand {
    @Override
    public String getCommandDesc() {
        return "Details how to use the commands available.";
    }

    @Override
    public String getCommand() {
        return "help";
    }

    @Override
    public String getCommandUsage() {
        return "!help <command>";
    }

    @Override
    public boolean executeCommand(CommandSender sender, User discordUser, String[] commandFeatures) {
        if(commandFeatures.length == 0) {
            String res = getCommand() + ": " + getCommandDesc() + "\nUsage: " + getCommandUsage();
            sender.sendMessage(res);
        } else {
            if(commandFeatures[0].equals("-v")){
                sender.sendMessage("Running " + GeiloUtils.NAME + " -version: " + GeiloUtils.VERSION);
                return false;
            }

            ICommand cmd = CommandProcessor.getCommand(commandFeatures[0]);

            if(cmd == null) {
                sender.sendMessage("Requested command: " + commandFeatures[0] + " was not found");
            } else {
                String res = cmd.getCommand() + ": " + cmd.getCommandDesc() + "\nUsage: " + cmd.getCommandUsage();
                sender.sendMessage(res);
            }
        }
        return false;
    }

    @Override
    public boolean checkPermission() {
        return false;
    }

    @Override
    public boolean doesUserHavePermission(User discordUser) {
        return true;
    }
}

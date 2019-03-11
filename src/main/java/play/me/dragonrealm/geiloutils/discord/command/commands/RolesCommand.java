package play.me.dragonrealm.geiloutils.discord.command.commands;

import net.dv8tion.jda.core.entities.User;
import org.bukkit.command.CommandSender;
import play.me.dragonrealm.geiloutils.discord.command.ICommand;


public class RolesCommand implements ICommand {

    @Override
    public String getCommandDesc() {
        return "Returns the current permission a user has.";
    }

    @Override
    public String getCommand() {
        return "roles";
    }

    @Override
    public String getCommandUsage() {
        return "!roles";
    }

    @Override
    public boolean executeCommand(CommandSender sender, User discordUser, String[] commandFeatures) {
        sender.sendMessage("Your rank is: ERROR 404!");
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

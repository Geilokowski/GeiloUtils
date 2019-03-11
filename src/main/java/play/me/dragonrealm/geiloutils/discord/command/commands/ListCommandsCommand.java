package play.me.dragonrealm.geiloutils.discord.command.commands;

import net.dv8tion.jda.core.entities.User;
import org.bukkit.command.CommandSender;
import play.me.dragonrealm.geiloutils.discord.command.CommandProcessor;
import play.me.dragonrealm.geiloutils.discord.command.ICommand;

public class ListCommandsCommand implements ICommand {
    @Override
    public String getCommandDesc() {
        return "List All Available Commands";
    }

    @Override
    public String getCommand() {
        return "commands";
    }

    @Override
    public String getCommandUsage() {
        return "!commands";
    }

    @Override
    public boolean executeCommand(CommandSender sender, User discordUser, String[] commandFeatures) {
        String res = CommandProcessor.listCommandsForUser(discordUser);
        sender.sendMessage(res);
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

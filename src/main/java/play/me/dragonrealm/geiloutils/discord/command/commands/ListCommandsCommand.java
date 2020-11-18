package play.me.dragonrealm.geiloutils.discord.command.commands;

import org.bukkit.command.CommandSender;
import play.me.dragonrealm.geiloutils.discord.command.CommandProcessor;
import play.me.dragonrealm.geiloutils.discord.command.ICommand;
import play.me.dragonrealm.geiloutils.discord.main.DiscordUser;

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
    public boolean executeCommand(CommandSender sender, DiscordUser discordUser, String[] commandFeatures) {
        String res = CommandProcessor.listCommandsForUser(discordUser);
        sender.sendMessage(res);
        return false;
    }

    @Override
    public boolean checkPermission() {
        return false;
    }

    @Override
    public boolean doesUserHavePermission(DiscordUser discordUser) {
        return true;
    }
}

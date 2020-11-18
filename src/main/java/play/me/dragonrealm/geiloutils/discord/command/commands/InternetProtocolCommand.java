package play.me.dragonrealm.geiloutils.discord.command.commands;

import org.bukkit.command.CommandSender;
import play.me.dragonrealm.geiloutils.discord.command.ICommand;
import play.me.dragonrealm.geiloutils.configs.ConfigAccess;
import play.me.dragonrealm.geiloutils.discord.main.DiscordUser;

public class InternetProtocolCommand implements ICommand {
    @Override
    public String getCommandDesc() {
        return "Returns the ip of the current server.";
    }

    @Override
    public String getCommand() {
        return "ip";
    }

    @Override
    public String getCommandUsage() {
        return "!ip";
    }

    @Override
    public boolean executeCommand(CommandSender sender, DiscordUser discordUser, String[] commandFeatures) {
        sender.sendMessage("``` SERVER IP: " + ConfigAccess.getDiscordConfig().getServerIP() + "```");
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

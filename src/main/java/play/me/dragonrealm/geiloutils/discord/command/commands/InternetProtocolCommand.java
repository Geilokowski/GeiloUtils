package play.me.dragonrealm.geiloutils.discord.command.commands;

import net.dv8tion.jda.core.entities.User;
import org.bukkit.command.CommandSender;
import play.me.dragonrealm.geiloutils.discord.command.ICommand;
import play.me.dragonrealm.geiloutils.configs.ConfigManager;

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
    public boolean executeCommand(CommandSender sender, User discordUser, String[] commandFeatures) {
        sender.sendMessage("``` SERVER IP: " + ConfigManager.getDiscordConfig().getServerIP() + "```");
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

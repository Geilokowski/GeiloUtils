package play.me.dragonrealm.geiloutils.discord.command.commands;

import net.dv8tion.jda.core.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import play.me.dragonrealm.geiloutils.discord.command.BotSender;
import play.me.dragonrealm.geiloutils.discord.command.ICommand;

public class TpsCommand implements ICommand {

    @Override
    public String getCommandDesc() {
        return "Returns the tps of a given dim, or all dims.";
    }

    @Override
    public String getCommand() {
        return "tps";
    }

    @Override
    public String getCommandUsage() {
        return "!tps [dimID]";
    }

    @Override
    public boolean executeCommand(CommandSender sender, User discordUser, String[] commandFeatures) {
        Bukkit.dispatchCommand(BotSender.INSTANCE, "tps");
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
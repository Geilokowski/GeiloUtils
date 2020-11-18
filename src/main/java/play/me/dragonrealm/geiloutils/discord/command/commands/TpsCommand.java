package play.me.dragonrealm.geiloutils.discord.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import play.me.dragonrealm.geiloutils.GeiloUtils;
import play.me.dragonrealm.geiloutils.discord.command.BotSender;
import play.me.dragonrealm.geiloutils.discord.command.ICommand;
import play.me.dragonrealm.geiloutils.discord.main.DiscordUser;

public class TpsCommand implements ICommand {

    @Override
    public String getCommandDesc() {
        return "Returns the tps of all dims.";
    }

    @Override
    public String getCommand() {
        return "tps";
    }

    @Override
    public String getCommandUsage() {
        return "!tps";
    }

    @Override
    public boolean executeCommand(CommandSender sender, DiscordUser discordUser, String[] commandFeatures) {
        Bukkit.getScheduler().runTask(GeiloUtils.getPlugin(GeiloUtils.class), () -> Bukkit.dispatchCommand(BotSender.INSTANCE, "tps"));
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

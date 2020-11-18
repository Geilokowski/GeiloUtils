package play.me.dragonrealm.geiloutils.discord.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import play.me.dragonrealm.geiloutils.GeiloUtils;
import play.me.dragonrealm.geiloutils.discord.command.BotSender;
import play.me.dragonrealm.geiloutils.discord.command.ICommand;

import java.util.concurrent.Callable;
import play.me.dragonrealm.geiloutils.discord.main.DiscordUser;

public class KillEntitiesCommand implements ICommand {

    @Override
    public String getCommandDesc() {
        return "Kills item entities.";
    }

    @Override
    public String getCommand() {
        return "tpsBuster";
    }

    @Override
    public String getCommandUsage() {
        return "!tpsBuster {Number}";
    }

    @Override
    public boolean executeCommand(CommandSender sender, DiscordUser discordUser, String[] commandFeatures) {
        if(commandFeatures.length == 1) {
            Integer number = Integer.getInteger(commandFeatures[0]);
            String cmd = String.format("kill @e[type=Item,r=[%s]]", number);
            Bukkit.getScheduler().runTask(GeiloUtils.getPlugin(GeiloUtils.class), () -> Bukkit.dispatchCommand(BotSender.BLOCK_INSTANCE, cmd));
        } else {
            Bukkit.getScheduler().runTask(GeiloUtils.getPlugin(GeiloUtils.class), () -> Bukkit.dispatchCommand(BotSender.BLOCK_INSTANCE, "kill @e[type=item]"));
        }
        return false;
    }

    @Override
    public boolean checkPermission() {
        return true;
    }

    @Override
    public boolean doesUserHavePermission(DiscordUser discordUser) {
        return false;
    }
}

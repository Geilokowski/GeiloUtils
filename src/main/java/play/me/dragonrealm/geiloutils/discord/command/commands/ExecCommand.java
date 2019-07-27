package play.me.dragonrealm.geiloutils.discord.command.commands;

import net.dv8tion.jda.core.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import play.me.dragonrealm.geiloutils.GeiloUtils;
import play.me.dragonrealm.geiloutils.discord.command.BotSender;
import play.me.dragonrealm.geiloutils.discord.command.ICommand;
import play.me.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.me.dragonrealm.geiloutils.discord.utils.UserRanks;
import play.me.dragonrealm.geiloutils.configs.ConfigAccess;

public class ExecCommand implements ICommand {

    @Override
    public String getCommandDesc() {
        return "Allows Arbitrary command execution straight to the server console.";
    }

    @Override
    public String getCommand() {
        return "exec";
    }

    @Override
    public String getCommandUsage() {
        return "!exec [minecraft command]";
    }

    @Override
    public boolean executeCommand(CommandSender sender, User discordUser, String[] commandFeatures) {
        if(commandFeatures.length > 0) {
            StringBuilder builder = new StringBuilder();
            for (String s : commandFeatures) {
                builder.append(s).append(" ");
            }
            int len = builder.length();
            builder = builder.delete(len - 1, len);
            String finalStr = builder.toString();
            //ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            Bukkit.getScheduler().runTask(GeiloUtils.getPlugin(GeiloUtils.class), () -> Bukkit.dispatchCommand(BotSender.BLOCK_INSTANCE, finalStr));
            ;
        }
        return false;
    }

    @Override
    public boolean checkPermission() {
        return true;
    }

    @Override
    public boolean doesUserHavePermission(User discordUser) {
        UserRanks rank = DiscordBotMain.getInstance().getHighestRankForUser(discordUser.getIdLong());
        int priority = ConfigAccess.getCommandConfig().getPriorityLevel(getCommand());
        if(rank == null || (rank.getPriority() < priority && priority != -1)) {
            return false;
        }
        return true;
    }
}

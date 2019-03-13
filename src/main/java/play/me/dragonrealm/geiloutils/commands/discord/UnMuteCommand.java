package play.me.dragonrealm.geiloutils.commands.discord;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import play.me.dragonrealm.geiloutils.commands.CommandBase;
import play.me.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.me.dragonrealm.geiloutils.utils.PlayerUtils;

public class UnMuteCommand extends CommandBase {

    public UnMuteCommand() {
        super("discordunmute");
    }

    @Override
    public String getCmdDesc() {
        return "Unmute a server so that a player receives messages again.";
    }

    @Override
    public String getCmdUsage() {
        return "/discordunmute <server_bot_name>";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(args.length < 1){
            sender.sendMessage("/discordunmute <Server_bot_name>");
        }
        if(sender instanceof Player) {
            Player player = (Player) sender;
            String botName = args[0].replace("_", " ");

            if (DiscordBotMain.getInstance().getBotsInGuild(false).contains(botName)) {
                if(PlayerUtils.removeFromMuteList(player, botName)) {
                    sender.sendMessage("[GeiloBot] Unmuted " + botName + " chat.");
                } else {
                    sender.sendMessage("[GeiloBot] Channel was not muted!");
                }

            } else {
                sender.sendMessage("Server not found!");
            }
            return true;
        }
        return false;
    }
}

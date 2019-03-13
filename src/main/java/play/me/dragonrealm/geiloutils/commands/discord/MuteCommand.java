package play.me.dragonrealm.geiloutils.commands.discord;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import play.me.dragonrealm.geiloutils.commands.CommandBase;
import play.me.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.me.dragonrealm.geiloutils.utils.PlayerUtils;

public class MuteCommand extends CommandBase {

    public MuteCommand() {
        super("discordmute");
    }

    @Override
    public String getCmdDesc() {
        return "Mute a server bot so you don't hear from that channel";
    }

    @Override
    public String getCmdUsage() {
        return "/discordmute <server_bot_name>";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(args.length < 1){
            sender.sendMessage("Wrong Usage: " + getCmdUsage());
        }
        if(sender instanceof Player) {
            Player player = (Player) sender;
            String botName = args[0].replace("_", " ");

            if (DiscordBotMain.getInstance().getBotsInGuild(false).contains(botName)) {
                PlayerUtils.addToMuteList(player, botName);
                sender.sendMessage("[GeiloBot] Muted " + botName + " chat.");
            } else {
                sender.sendMessage("Server not found!");
            }
            return true;
        }
        return false;
    }
}

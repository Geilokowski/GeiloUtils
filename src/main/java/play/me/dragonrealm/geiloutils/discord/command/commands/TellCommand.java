package play.me.dragonrealm.geiloutils.discord.command.commands;

import net.dv8tion.jda.core.entities.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import play.me.dragonrealm.geiloutils.discord.command.ICommand;

public class TellCommand implements ICommand {
    @Override
    public String getCommandDesc() {
        return "Private message a user in game.";
    }

    @Override
    public String getCommand() {
        return "tell";
    }

    @Override
    public String getCommandUsage() {
        return "!tell <Player> <your message here>";
    }

    @Override
    public boolean executeCommand(CommandSender sender, User discordUser, String[] commandFeatures) {
        Player player = sender.getServer().getPlayer(commandFeatures[0]);
        if(player != null) {
            StringBuilder b = new StringBuilder();
            for(int i = 1; i < commandFeatures.length; i++){
                b.append(commandFeatures[i]).append(" ");
            }
            player.sendMessage("[Discord DM] " + discordUser.getName() + " \u003e\u003e" + b.toString());
            return true;
        }
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

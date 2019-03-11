package play.me.dragonrealm.geiloutils.discord.command.commands;

import net.dv8tion.jda.core.entities.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import play.me.dragonrealm.geiloutils.GeiloUtils;
import play.me.dragonrealm.geiloutils.discord.command.ICommand;

import java.util.Collection;
import java.util.Iterator;

public class OnlineCommand implements ICommand {

    @Override
    public String getCommandDesc() {
        return "Returns all the current online players on the server.";
    }

    @Override
    public String getCommand() {
        return "online";
    }

    @Override
    public String getCommandUsage() {
        return "!online";
    }

    @Override
    public boolean executeCommand(CommandSender sender, User discordUser, String[] commandFeatures) {
        Collection<? extends Player> players = GeiloUtils.getInstanceServer().getOnlinePlayers();
        if(players.size() == 0) {
            sender.sendMessage("No players online!");
        }else{

            String prefix = players.size() == 1 ? "1 Player online: " : players.size() + " Players online";


            StringBuilder output = new StringBuilder();
            for (Iterator<? extends Player> it = players.iterator(); it.hasNext(); ) {
                Player s = it.next();
                output.append(s.getName()).append(", ");
            }
            int len = output.length();
            output.delete(len-2, len-1);

            sender.sendMessage(prefix + output.toString());
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

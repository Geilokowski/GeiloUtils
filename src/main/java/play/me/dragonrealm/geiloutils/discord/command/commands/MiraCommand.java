package play.me.dragonrealm.geiloutils.discord.command.commands;

import net.dv8tion.jda.core.entities.User;
import org.bukkit.command.CommandSender;
import play.me.dragonrealm.geiloutils.discord.command.ICommand;

public class MiraCommand implements ICommand {
    @Override
    public String getCommandDesc() {
        return "tells the current status of mira";
    }

    @Override
    public String getCommand() {
        return "mira";
    }

    @Override
    public String getCommandUsage() {
        return "mira [more words]";
    }

    @Override
    public boolean executeCommand(CommandSender sender, User discordUser, String[] commandFeatures) {
        if(discordUser.getName().equals("Tesca")){
            sender.sendMessage("Mira's a pretty bad guy!");
        } else {
            sender.sendMessage("Mira's pretty cool, eh?");
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

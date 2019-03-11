package play.me.dragonrealm.geiloutils.discord.command.commands;

import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import org.bukkit.command.CommandSender;
import play.me.dragonrealm.geiloutils.discord.command.ICommand;
import play.me.dragonrealm.geiloutils.discord.main.DiscordBotMain;

import java.util.List;

public class DeclareAllCommand implements ICommand {
    @Override
    public String getCommandDesc() {
        return "Returns the role IDs for all roles on a user.";
    }

    @Override
    public String getCommand() {
        return "miserere";
    }

    @Override
    public String getCommandUsage() {
        return "!miserere <username>";
    }

    @Override
    public boolean executeCommand(CommandSender sender, User discordUser, String[] commandFeatures) {
        List<Role> roleList = DiscordBotMain.getInstance().getRolesOnUser(Long.parseLong(commandFeatures[0]));
        for(Role role: roleList) {
            sender.sendMessage("ROLE: " + role.getName() + "|| ID:" + role.getId());
        }
        return false;
    }

    @Override
    public boolean checkPermission() {
        return true;
    }

    @Override
    public boolean doesUserHavePermission(User discordUser) {
        return discordUser.getName().equals("dmf444");
    }
}

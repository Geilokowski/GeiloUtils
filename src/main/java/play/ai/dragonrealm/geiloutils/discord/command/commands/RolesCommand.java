package play.ai.dragonrealm.geiloutils.discord.command.commands;

import net.dv8tion.jda.core.entities.User;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.discord.command.ICommand;


public class RolesCommand implements ICommand {

    @Override
    public String getCommandDesc() {
        return "Returns the current permission a user has.";
    }

    @Override
    public String getCommand() {
        return "roles";
    }

    @Override
    public String getCommandUsage() {
        return "!roles";
    }

    @Override
    public boolean executeCommand(ICommandSender sender, User discordUser, String[] commandFeatures) {
        sender.sendMessage(new TextComponentString("Your rank is: MotherFucker!"));
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

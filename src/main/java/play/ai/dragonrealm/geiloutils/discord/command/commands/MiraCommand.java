package play.ai.dragonrealm.geiloutils.discord.command.commands;

import net.dv8tion.jda.core.entities.User;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.discord.command.ICommand;

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
    public boolean executeCommand(ICommandSender sender, User discordUser, String[] commandFeatures) {
        if(discordUser.getName().equals("Tesca")){
            sender.sendMessage(new TextComponentString("Mira's a pretty bad guy!"));
        } else {
            sender.sendMessage(new TextComponentString("Mira's pretty cool, eh?"));
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

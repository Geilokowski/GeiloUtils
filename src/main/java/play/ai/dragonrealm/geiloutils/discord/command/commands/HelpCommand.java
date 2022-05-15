package play.ai.dragonrealm.geiloutils.discord.command.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.discord.command.CommandProcessor;
import play.ai.dragonrealm.geiloutils.discord.command.ICommand;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordUser;

public class HelpCommand implements ICommand {
    @Override
    public String getCommandDesc() {
        return "Details how to use the commands available.";
    }

    @Override
    public String getCommand() {
        return "help";
    }

    @Override
    public String getCommandUsage() {
        return "!help <command>";
    }

    @Override
    public boolean executeCommand(ICommandSender sender, DiscordUser discordUser, String[] commandFeatures) {
        if(commandFeatures.length == 0) {
            String res = getCommand() + ": " + getCommandDesc() + "\nUsage: " + getCommandUsage();
            sender.sendMessage(new TextComponentString(res));
        } else {
            if(commandFeatures[0].equals("-v")){
                sender.sendMessage(new TextComponentString("Running " + GeiloUtils.NAME + " -version: " + GeiloUtils.VERSION));
                return false;
            }

            ICommand cmd = CommandProcessor.getCommand(commandFeatures[0]);

            if(cmd == null) {
                sender.sendMessage(new TextComponentString("Requested command: " + commandFeatures[0] + " was not found"));
            } else {
                String res = cmd.getCommand() + ": " + cmd.getCommandDesc() + "\nUsage: " + cmd.getCommandUsage();
                sender.sendMessage(new TextComponentString(res));
            }
        }
        return false;
    }

    @Override
    public boolean checkPermission() {
        return false;
    }

    @Override
    public boolean doesUserHavePermission(DiscordUser discordUser) {
        return true;
    }
}

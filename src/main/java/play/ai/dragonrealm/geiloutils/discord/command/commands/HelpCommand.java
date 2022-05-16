package play.ai.dragonrealm.geiloutils.discord.command.commands;

import net.minecraft.command.ICommandSource;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
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
    public boolean executeCommand(ICommandSource sender, DiscordUser discordUser, String[] commandFeatures) {
        if(commandFeatures.length == 0) {
            String res = getCommand() + ": " + getCommandDesc() + "\nUsage: " + getCommandUsage();
            sender.sendMessage(new StringTextComponent(res), Util.NIL_UUID);
        } else {
            if(commandFeatures[0].equals("-v")){
                sender.sendMessage(new StringTextComponent("Running " + GeiloUtils.NAME + " -version: " + GeiloUtils.VERSION), Util.NIL_UUID);
                return false;
            }

            ICommand cmd = CommandProcessor.getCommand(commandFeatures[0]);

            if(cmd == null) {
                sender.sendMessage(new StringTextComponent("Requested command: " + commandFeatures[0] + " was not found"), Util.NIL_UUID);
            } else {
                String res = cmd.getCommand() + ": " + cmd.getCommandDesc() + "\nUsage: " + cmd.getCommandUsage();
                sender.sendMessage(new StringTextComponent(res), Util.NIL_UUID);
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

package play.ai.dragonrealm.geiloutils.discord.command.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.discord.command.CommandProcessor;
import play.ai.dragonrealm.geiloutils.discord.command.ICommand;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordUser;

public class ListCommandsCommand implements ICommand {
    @Override
    public String getCommandDesc() {
        return "List All Available Commands";
    }

    @Override
    public String getCommand() {
        return "commands";
    }

    @Override
    public String getCommandUsage() {
        return "!commands";
    }

    @Override
    public boolean executeCommand(ICommandSender sender, DiscordUser discordUser, String[] commandFeatures) {
        String res = CommandProcessor.listCommandsForUser(discordUser);
        sender.sendMessage(new TextComponentString(res));
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

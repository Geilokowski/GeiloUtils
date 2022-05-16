package play.ai.dragonrealm.geiloutils.discord.command.commands;

import net.minecraft.command.ICommandSource;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
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
    public boolean executeCommand(ICommandSource sender, DiscordUser discordUser, String[] commandFeatures) {
        String res = CommandProcessor.listCommandsForUser(discordUser);
        sender.sendMessage(new StringTextComponent(res), Util.NIL_UUID);
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

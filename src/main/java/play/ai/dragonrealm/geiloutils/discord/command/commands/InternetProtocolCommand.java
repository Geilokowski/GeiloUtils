package play.ai.dragonrealm.geiloutils.discord.command.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.discord.command.ICommand;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordUser;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;

public class InternetProtocolCommand implements ICommand {
    @Override
    public String getCommandDesc() {
        return "Returns the ip of the current server.";
    }

    @Override
    public String getCommand() {
        return "ip";
    }

    @Override
    public String getCommandUsage() {
        return "!ip";
    }

    @Override
    public boolean executeCommand(ICommandSender sender, DiscordUser discordUser, String[] commandFeatures) {
        sender.sendMessage(new TextComponentString("``` SERVER IP: " + ConfigAccess.getDiscordConfig().getServerIP() + "```"));
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

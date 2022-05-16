package play.ai.dragonrealm.geiloutils.discord.command.commands;

import net.minecraft.command.ICommandSource;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import play.ai.dragonrealm.geiloutils.discord.command.ICommand;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordUser;

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
    public boolean executeCommand(ICommandSource sender, DiscordUser discordUser, String[] commandFeatures) {
        if(discordUser.getName().equals("Tesca")){
            sender.sendMessage(new StringTextComponent("Mira's a pretty bad guy!"), Util.NIL_UUID);
        } else {
            sender.sendMessage(new StringTextComponent("Mira's pretty cool, eh?"), Util.NIL_UUID);
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

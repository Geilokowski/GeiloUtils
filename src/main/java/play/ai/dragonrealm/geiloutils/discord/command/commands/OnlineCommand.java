package play.ai.dragonrealm.geiloutils.discord.command.commands;

import net.minecraft.command.ICommandSource;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import play.ai.dragonrealm.geiloutils.discord.command.ICommand;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordUser;
import play.ai.dragonrealm.geiloutils.utils.McFacade;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

public class OnlineCommand implements ICommand {

    @Override
    public String getCommandDesc() {
        return "Returns all the current online players on the server.";
    }

    @Override
    public String getCommand() {
        return "online";
    }

    @Override
    public String getCommandUsage() {
        return "!online";
    }

    @Override
    public boolean executeCommand(ICommandSource sender, DiscordUser discordUser, String[] commandFeatures) {
        if(McFacade.getOnlinePlayers().size() == 0) {
            sender.sendMessage(new StringTextComponent("No players online!"), Util.NIL_UUID);
        }else{

            if(PlayerUtils.getOnlinePlayerNames().length == 1){
                sender.sendMessage(new StringTextComponent("1 Player online: " + PlayerUtils.getOnlinePlayerNames()[0]), Util.NIL_UUID);
                return false;
            }

            StringBuilder output = new StringBuilder();
            for(String s : PlayerUtils.getOnlinePlayerNames()){
                output.append(s).append(", ");
            }
            int len = output.length();
            output.delete(len-2, len-1);

            sender.sendMessage(new StringTextComponent(PlayerUtils.getOnlinePlayerNames().length + " Players online: " + output.toString()), Util.NIL_UUID);
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

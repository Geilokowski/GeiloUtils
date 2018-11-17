package play.ai.dragonrealm.geiloutils.discord.command.commands;

import net.dv8tion.jda.core.entities.User;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.discord.command.ICommand;
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
    public boolean executeCommand(ICommandSender sender, User discordUser, String[] commandFeatures) {
        if(FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers().size() == 0) {
            sender.sendMessage(new TextComponentString("No players online!"));
        }else{

            if(PlayerUtils.getOnlinePlayerNames().length == 1){
                sender.sendMessage(new TextComponentString("1 Player online: " + PlayerUtils.getOnlinePlayerNames()[0]));
                return false;
            }

            StringBuilder output = new StringBuilder();
            for(String s : PlayerUtils.getOnlinePlayerNames()){
                output.append(s).append(", ");
            }
            int len = output.length();
            output.delete(len-2, len-1);

            sender.sendMessage(new TextComponentString(PlayerUtils.getOnlinePlayerNames().length + " Players online: " + output.toString()));
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

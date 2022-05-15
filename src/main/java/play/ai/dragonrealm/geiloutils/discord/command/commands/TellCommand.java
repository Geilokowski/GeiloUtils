package play.ai.dragonrealm.geiloutils.discord.command.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.discord.command.ICommand;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordUser;

public class TellCommand implements ICommand {
    @Override
    public String getCommandDesc() {
        return "Private message a user in game.";
    }

    @Override
    public String getCommand() {
        return "tell";
    }

    @Override
    public String getCommandUsage() {
        return "!tell <Player> <your message here>";
    }

    @Override
    public boolean executeCommand(ICommandSender sender, DiscordUser discordUser, String[] commandFeatures) {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        if(server != null) {
            EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(commandFeatures[0]);
            StringBuilder b = new StringBuilder();
            for(int i = 1; i < commandFeatures.length; i++){
                b.append(commandFeatures[i]).append(" ");
            }
            if(player != null) {
                player.sendMessage(new TextComponentString("[Discord DM] " + discordUser.getName() + " \u003e\u003e" + b.toString()));
                return true;
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

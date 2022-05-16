package play.ai.dragonrealm.geiloutils.discord.command.commands;

import net.minecraft.command.ICommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
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
    public boolean executeCommand(ICommandSource sender, DiscordUser discordUser, String[] commandFeatures) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if(server != null) {
            ServerPlayerEntity player = server.getPlayerList().getPlayerByName(commandFeatures[0]);
            StringBuilder b = new StringBuilder();
            for(int i = 1; i < commandFeatures.length; i++){
                b.append(commandFeatures[i]).append(" ");
            }
            if(player != null) {
                player.sendMessage(new StringTextComponent("[Discord DM] " + discordUser.getName() + " \u003e\u003e" + b.toString()), Util.NIL_UUID);
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

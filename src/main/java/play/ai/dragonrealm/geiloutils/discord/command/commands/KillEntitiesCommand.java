package play.ai.dragonrealm.geiloutils.discord.command.commands;

import net.minecraft.command.ICommandSource;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import play.ai.dragonrealm.geiloutils.discord.command.BotSender;
import play.ai.dragonrealm.geiloutils.discord.command.ICommand;

import java.util.concurrent.Callable;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordUser;

public class KillEntitiesCommand implements ICommand {

    @Override
    public String getCommandDesc() {
        return "Kills item entities.";
    }

    @Override
    public String getCommand() {
        return "tpsBuster";
    }

    @Override
    public String getCommandUsage() {
        return "!tpsBuster {Number}";
    }

    @Override
    public boolean executeCommand(ICommandSource sender, DiscordUser discordUser, String[] commandFeatures) {
        if(commandFeatures.length == 1) {
            Integer number = Integer.getInteger(commandFeatures[0]);
            String cmd = String.format("kill @e[type=Item,r=[%s]]", number);
            ServerLifecycleHooks.getCurrentServer().executeBlocking(() -> {
                ServerLifecycleHooks.getCurrentServer().getCommands().performCommand(BotSender.BLOCK_INSTANCE.getCommandSource(), cmd);
            });
        } else {
            ServerLifecycleHooks.getCurrentServer().executeBlocking(() -> {
                ServerLifecycleHooks.getCurrentServer().getCommands().performCommand(BotSender.BLOCK_INSTANCE.getCommandSource(), "kill @e[type=item]");
            });
        }
        return false;
    }

    @Override
    public boolean checkPermission() {
        return true;
    }

    @Override
    public boolean doesUserHavePermission(DiscordUser discordUser) {
        return false;
    }
}

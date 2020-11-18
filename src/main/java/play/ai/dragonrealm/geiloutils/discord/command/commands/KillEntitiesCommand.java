package play.ai.dragonrealm.geiloutils.discord.command.commands;

import net.minecraft.command.ICommandSender;
import net.minecraftforge.fml.common.FMLCommonHandler;
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
    public boolean executeCommand(ICommandSender sender, DiscordUser discordUser, String[] commandFeatures) {
        if(commandFeatures.length == 1) {
            Integer number = Integer.getInteger(commandFeatures[0]);
            String cmd = String.format("kill @e[type=Item,r=[%s]]", number);
            FMLCommonHandler.instance().getMinecraftServerInstance().callFromMainThread(new Callable<Object>() {

                @Override
                public Object call() throws Exception {
                    FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().executeCommand(BotSender.BLOCK_INSTANCE, cmd);
                    return null;
                }
            });
        } else {
            FMLCommonHandler.instance().getMinecraftServerInstance().callFromMainThread(new Callable<Object>() {

                @Override
                public Object call() throws Exception {
                    FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().executeCommand(BotSender.BLOCK_INSTANCE, "kill @e[type=item]");
                    return null;
                }
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

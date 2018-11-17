package play.ai.dragonrealm.geiloutils.discord.command.commands;

import net.dv8tion.jda.core.entities.User;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.discord.command.BotSender;
import play.ai.dragonrealm.geiloutils.discord.command.ICommand;
import play.ai.dragonrealm.geiloutils.discord.utils.DiscordRank;
import play.ai.dragonrealm.geiloutils.discord.utils.DiscordUtils;

import java.util.concurrent.Callable;

public class ExecCommand implements ICommand {

    @Override
    public String getCommandDesc() {
        return "Allows Arbitrary command execution straight to the server console.";
    }

    @Override
    public String getCommand() {
        return "exec";
    }

    @Override
    public String getCommandUsage() {
        return "!exec [minecraft command]";
    }

    @Override
    public boolean executeCommand(ICommandSender sender, User discordUser, String[] commandFeatures) {
        if(commandFeatures.length > 0) {
            StringBuilder builder = new StringBuilder();
            for (String s : commandFeatures) {
                builder.append(s).append(" ");
            }
            int len = builder.length();
            builder = builder.delete(len - 1, len);
            String finalStr = builder.toString();
            FMLCommonHandler.instance().getMinecraftServerInstance().callFromMainThread(new Callable<Object>() {

                @Override
                public Object call() throws Exception {
                    FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().executeCommand(BotSender.BLOCK_INSTANCE, finalStr);
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
    public boolean doesUserHavePermission(User discordUser) {
        DiscordRank rank = DiscordUtils.getAuthForUser(discordUser);
        return rank == DiscordRank.ADMIN;
    }
}

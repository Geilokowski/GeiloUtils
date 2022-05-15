package play.ai.dragonrealm.geiloutils.commands.discord;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.ai.dragonrealm.geiloutils.new_configs.containers.PlayerStatsConfig;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandMute extends CommandBase {


    @Override
    public String getName() {
        return "discordmute";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/discordmute <server_bot_name>";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length < 1){
            throw new WrongUsageException("/discordmute <Server_bot_name>", new Object[0]);
        }
        if(sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            String botName = args[0].replace("_", " ");

            if (DiscordBotMain.getInstance().getBotsInGuild(false).contains(botName)) {
                GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).addToMuteList(player.getCachedUniqueIdString(), botName);
                sender.sendMessage(new TextComponentString("[GeiloBot] Muted " + botName + " chat."));
            } else {
                throw new WrongUsageException("Server not found!", new Object[0]);
            }
        }
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if (args.length >= 1 && sender instanceof EntityPlayer) {
            return getListOfStringsMatchingLastWord(args, DiscordBotMain.getInstance().getBotsInGuild(true));
        }
        return Collections.<String>emptyList();
    }
}

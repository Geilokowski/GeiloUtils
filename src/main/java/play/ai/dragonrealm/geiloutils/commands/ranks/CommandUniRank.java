package play.ai.dragonrealm.geiloutils.commands.ranks;

import net.minecraft.command.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.discord.command.BotSender;

import java.util.concurrent.Callable;

public class CommandUniRank extends CommandBase {

    public static final String[] FTBU_RANKS = {"mod", "supporter1", "supporter2", "supporter3", "supporter4"};
    public static final String[] LUCKY_PERM_RANKS = {"mod", "sup1", "sup2", "sup3", "sup4"};
    public static final String[] SCOREBOARD_RANKS = {"staff", "support1", "support2", "support3", "support4"};

    @Override
    public String getName() {
        return "unirank";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/unirank <Player> [0 - mod|1|2|3|4]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 2) {
            try {
                Integer rank = Integer.parseInt(args[1]);
                Entity entity = getEntity(server, sender, args[0]);
                if(entity instanceof EntityPlayer) {
                    final String ftbu = "ranks set " + args[0] + " " + FTBU_RANKS[rank];
                    final String lucky = "lp user "+ args[0] +" parent set " + LUCKY_PERM_RANKS[rank];
                    final String scoreboard = "scoreboard teasms join " + args[0] + " " + SCOREBOARD_RANKS[rank];
                    final String geiloClear = "geilorank delUser " + args[0];
                    final String geiloRank = "geilorank addUser " + SCOREBOARD_RANKS[rank] + " " + args[0];

                    FMLCommonHandler.instance().getMinecraftServerInstance().callFromMainThread(new Callable<Object>() {

                        @Override
                        public Object call() throws Exception {
                            FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().executeCommand(sender, ftbu);
                            FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().executeCommand(sender, lucky);
                            FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().executeCommand(sender, scoreboard);
                            FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().executeCommand(sender, geiloClear);
                            FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().executeCommand(sender, geiloRank);
                            return null;
                        }
                    });
                    notifyCommandListener(sender, this, "[UniRank] Completed Ranking!");
                }
            }catch (NumberFormatException | EntityNotFoundException e) {
                throw new WrongUsageException("[UniRank] Unable to complete the unirank for some reason!");
            }
        }
    }
}

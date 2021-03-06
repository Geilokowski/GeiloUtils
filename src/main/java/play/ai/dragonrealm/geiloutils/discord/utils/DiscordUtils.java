package play.ai.dragonrealm.geiloutils.discord.utils;

import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.discord.command.BotSender;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


public class DiscordUtils {


    public static void autoModRankUser(@Nonnull UserRanks rank, String playerUsername) {
        String[] commands = rank.getRawRankCommands();
        FMLCommonHandler.instance().getMinecraftServerInstance().callFromMainThread(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                for (String command : commands) {
                    String entry = String.format(command, playerUsername);
                    FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().executeCommand(BotSender.SILENT_BOT, entry);
                }
                return null;
            }
        });
    }

    public static List<String> getRoleIDList(List<UserRanks> ranks){
        ArrayList<String> roleIDs = new ArrayList<>();
        for(UserRanks rank : ranks) {
            roleIDs.add(rank.getRoleID());
        }
        return roleIDs;
    }

    public static UserRanks getUserRanksFromId(List<UserRanks> ranks, String id) {
        for (UserRanks rank : ranks) {
            if(rank.getRoleID().equals(id)) return rank;
        }
        return null;
    }


}

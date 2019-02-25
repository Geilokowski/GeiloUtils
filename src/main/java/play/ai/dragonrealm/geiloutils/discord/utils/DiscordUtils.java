package play.ai.dragonrealm.geiloutils.discord.utils;

import net.dv8tion.jda.core.entities.User;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.discord.command.BotSender;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigManager;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;


public class DiscordUtils {


    public static DiscordRank getAuthForUser(User user) {
        /*Map<Long, DiscordRank> adminMap = ConfigManager.getDiscordConfig().getAdminMap();
        if(adminMap != null && adminMap.size() > 0) {
            for (Long userId : adminMap.keySet()) {
                if (user.getIdLong() == userId) {
                    return adminMap.get(userId);
                }
            }
        }*/
        return DiscordRank.COMMONER;
    }

    public static void setRankForDiscordUser(long userId, DiscordRank rank) {
        /*Map<Long, DiscordRank> adminMap = ConfigManager.getDiscordConfig().getAdminMap();
        adminMap.put(userId, rank);
        ConfigManager.getDiscordConfig().setAdminMap(adminMap);

        ConfigurationManager.syncFromFields();*/
    }

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

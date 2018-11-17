package play.ai.dragonrealm.geiloutils.discord.utils;

import net.dv8tion.jda.core.entities.User;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import java.util.Map;


public class DiscordUtils {


    public static DiscordRank getAuthForUser(User user) {
        Map<Long, DiscordRank> adminMap = ConfigurationManager.getDiscordConfig().getAdminMap();
        if(adminMap != null && adminMap.size() > 0) {
            for (Long userId : adminMap.keySet()) {
                if (user.getIdLong() == userId) {
                    return adminMap.get(userId);
                }
            }
        }
        return DiscordRank.COMMONER;
    }

    public static void setRankForDiscordUser(long userId, DiscordRank rank) {
        Map<Long, DiscordRank> adminMap = ConfigurationManager.getDiscordConfig().getAdminMap();
        adminMap.put(userId, rank);
        ConfigurationManager.getDiscordConfig().setAdminMap(adminMap);

        ConfigurationManager.syncFromFields();
    }


}

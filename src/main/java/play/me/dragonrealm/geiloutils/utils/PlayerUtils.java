package play.me.dragonrealm.geiloutils.utils;

import org.bukkit.entity.Player;
import play.me.dragonrealm.geiloutils.GeiloUtils;
import play.me.dragonrealm.geiloutils.configs.ConfigAccess;
import play.me.dragonrealm.geiloutils.configs.models.PlayerStats;

import java.util.List;

public class PlayerUtils {

    public static boolean isFirstJoin(Player player) {
        for(PlayerStats ps : ConfigAccess.getPlayerStatsConfig().getPlayerstats()) {
            if(ps.getUuid().equals(player.getUniqueId().toString())) {
                return false;
            }
        }

        return true;
    }

    public static PlayerStats getPlayerstatByUUID(String uuid) {
        for(PlayerStats ps : ConfigAccess.getPlayerStatsConfig().getPlayerstats()) {
            if(ps.getUuid().equals(uuid)) {
                return ps;
            }
        }

        return null;
    }

    public static PlayerStats getPlayerstatByDiscordID(Long id) {
        for(PlayerStats ps : ConfigAccess.getPlayerStatsConfig().getPlayerstats()) {
            if(ps.getDiscordID().equals(id)) {
                return ps;
            }
        }
        return null;
    }

    public static void updatePlayerstat(PlayerStats ps) {
        for(PlayerStats oldPlayerstats : ConfigAccess.getPlayerStatsConfig().getPlayerstats()) {
            if(oldPlayerstats.getUuid().equals(ps.getUuid())) {
                ConfigAccess.getPlayerStatsConfig().getPlayerstats().remove(oldPlayerstats);
                ConfigAccess.getPlayerStatsConfig().getPlayerstats().add(ps);
                ConfigAccess.writePlayerStatsFile();
                return;
            }
        }
    }

    public static void removePlayerMoney(Player player, double amount)
    {
        PlayerStats ps = getPlayerstatByUUID(player.getUniqueId().toString());
        double newMoney = ps.getMoney() - amount;

        ps.setMoney(newMoney);
        updatePlayerstat(ps);
        //ConfigurationManager.syncFromFields();
    }

    public static void addPlayerMoney(Player player, double amount)
    {
        if(player == null) {
            return;
        }

        GeiloUtils.getLog().info(player.getUniqueId().toString());
        PlayerStats ps = getPlayerstatByUUID(player.getUniqueId().toString());
        double newMoney = ps.getMoney() + amount;

        ps.setMoney(newMoney);
        updatePlayerstat(ps);
        //ConfigurationManager.syncFromFields();
    }

    public static void addToMuteList(Player player, String addition) {
        if(player == null) {
            return;
        }

        PlayerStats ps = getPlayerstatByUUID(player.getUniqueId().toString());
        List<String> muted = ps.getMutedChats();
        muted.add(addition);
        ps.setMutedChats(muted);

        updatePlayerstat(ps);
        //ConfigurationManager.syncFromFields();
    }

    public static boolean removeFromMuteList(Player player, String removal) {
        if(player == null) {
            return false;
        }

        PlayerStats ps = getPlayerstatByUUID(player.getUniqueId().toString());
        List<String> muted = ps.getMutedChats();

        if(muted.contains(removal)) {
            muted.remove(removal);
            updatePlayerstat(ps);
            //ConfigurationManager.syncFromFields();
            return true;
        }

        return false;
    }

    public static double getPlayerBalance(Player player)
    {
        PlayerStats ps = getPlayerstatByUUID(player.getUniqueId().toString());
        return ps.getMoney();
    }


}

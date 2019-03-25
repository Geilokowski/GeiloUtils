package play.ai.dragonrealm.geiloutils.commands.ftblib;

import com.feed_the_beast.ftblib.lib.data.ForgeTeam;

import java.util.HashMap;

public class FTBUhelper {



    public static void addPlayerToTeamMemory(String playerUID, String playerTeam){
        /*HashMap<String, String> fromConfig = ConfigurationManager.getFtbUtilsInter().inLocoBigas;
        fromConfig.put(playerUID, playerTeam);
        ConfigurationManager.getFtbUtilsInter().inLocoBigas = fromConfig;
        ConfigurationManager.syncFromFields();*/
    }

    public static ForgeTeam getPlayerTeamFromConfig(String playerUID) {
        /*ForgeTeam returnTeam = Universe.get().getTeam("");

        HashMap<String, String> fromConfig = ConfigurationManager.getFtbUtilsInter().inLocoBigas;
        if(fromConfig.containsKey(playerUID)){
            returnTeam = Universe.get().getTeam(fromConfig.get(playerUID));
        }
        return returnTeam;*/
        return null;
    }
}

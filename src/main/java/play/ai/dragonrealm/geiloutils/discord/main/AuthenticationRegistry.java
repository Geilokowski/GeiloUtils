package play.ai.dragonrealm.geiloutils.discord.main;

import net.dv8tion.jda.core.entities.User;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.config.playerstats.Playerstat;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationRegistry {

    public static AuthenticationRegistry INSTANCE = new AuthenticationRegistry();
    private Map<String, String> nameMap = new HashMap<>();
    private Map<String, User> userMap = new HashMap<>();


    public void addAuthAttempt(String mcUID, String code, User discordUser) {
        nameMap.put(mcUID, code);
        userMap.put(mcUID, discordUser);
    }

    public boolean hasTriedAuthenticating(String mcUID){
        return nameMap.containsKey(mcUID) && !PlayerUtils.getPlayerstatByUUID(mcUID).isDiscordVerified();
    }

    public String getCode(String mcUID) {
        return nameMap.get(mcUID);
    }

    public boolean isCorrectCode(String mcUID, String testCode) {
        String code = nameMap.get(mcUID);
        return testCode.equals(code);
    }

    public void verifyUserAndAdd(String mcUID, String testCode) {
        if(isCorrectCode(mcUID, testCode)) {
            Playerstat stat = PlayerUtils.getPlayerstatByUUID(mcUID);
            stat.setDiscordID(userMap.get(mcUID).getIdLong());
            ConfigurationManager.syncFromFields();

            userMap.remove(mcUID);
            nameMap.remove(mcUID);
        }
    }
}

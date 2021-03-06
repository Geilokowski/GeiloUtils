package play.ai.dragonrealm.geiloutils.discord.utils;


import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordUser;
import play.ai.dragonrealm.geiloutils.new_configs.containers.PlayerStatsConfig;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AuthenticationRegistry {

    public static AuthenticationRegistry INSTANCE = new AuthenticationRegistry();
    private Map<String, String> nameMap = new HashMap<>();
    private Map<String, DiscordUser> userMap = new HashMap<>();


    public void addAuthAttempt(String mcUID, String code, DiscordUser discordUser) {
        nameMap.put(mcUID, code);
        userMap.put(mcUID, discordUser);
    }

    public boolean hasTriedAuthenticating(String mcUID){
        return nameMap.containsKey(mcUID) || PlayerUtils.getPlayerstatByUUID(mcUID).isDiscordVerified();
    }

    public String getCode(String mcUID) {
        return nameMap.get(mcUID);
    }

    public boolean isCorrectCode(String mcUID, String testCode) {
        String code = nameMap.get(mcUID);
        return testCode.equals(code);
    }

    public AuthenticStatus verifyUserAndAdd(String mcUID, String testCode) {
        if(isCorrectCode(mcUID, testCode)) {
            Optional<Playerstat> playerstat = GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).getPlayerStatByUUID(mcUID);
            if(playerstat.isPresent()) {
                Playerstat ps = playerstat.get();
                ps.setDiscordID(userMap.get(mcUID).getUserIdAsLong());
                GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).updatePlayerstat(ps);

                userMap.remove(mcUID);
                nameMap.remove(mcUID);
                return AuthenticStatus.COMPLETED;
            }
            return AuthenticStatus.PLAYER_NOT_FOUND;
        }
        return AuthenticStatus.INCORRECT_CODE;
    }

    public enum AuthenticStatus {
        COMPLETED,
        PLAYER_NOT_FOUND,
        INCORRECT_CODE;
    }
}

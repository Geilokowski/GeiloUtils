package play.ai.dragonrealm.geiloutils.discord.utils;

public class UserRanks {

    private int priority;
    private String commonName;
    private String roleID;
    private String[] rawRankCommands;

    public UserRanks(int priority, String name, String roleID){
        this.priority = priority;
        this.commonName = name;
        this.roleID = roleID;
        this.rawRankCommands = new String[1];
    }


    public int getPriority() {
        return priority;
    }

    public String getCommonName() {
        return commonName;
    }

    public String[] getRawRankCommands() {
        return rawRankCommands;
    }

    public String getRoleID() {
        return roleID;
    }
}

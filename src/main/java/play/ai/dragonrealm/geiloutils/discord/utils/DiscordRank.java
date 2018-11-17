package play.ai.dragonrealm.geiloutils.discord.utils;

public enum DiscordRank {
    ADMIN(4),
    MOD(3),
    UNUSED(2), //Used for spamming users!
    COMMONER(1);


    private int level;
    DiscordRank(int adminLevel) {
        this.level = adminLevel;
    }

    public int getLevel() {
        return level;
    }
}

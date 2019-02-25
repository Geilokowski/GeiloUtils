package play.ai.dragonrealm.geiloutils.new_configs.discord;

public class CommandsData {

    private boolean enabled;
    private int minPriorityLvl;

    public CommandsData(boolean enabled, int priority){
        this.enabled = enabled;
        this.minPriorityLvl = priority;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getMinPriorityLvl() {
        return minPriorityLvl;
    }
}

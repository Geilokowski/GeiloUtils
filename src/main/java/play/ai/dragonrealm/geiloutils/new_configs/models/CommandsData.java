package play.ai.dragonrealm.geiloutils.new_configs.models;

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

package play.ai.dragonrealm.geiloutils.new_configs.interfaces;

public interface IRandomTeleportConfig {


    public int getRadius();
    public void setRadius(int radius);
    public int getMinY();
    public void setMinY(int minY);
    public boolean isEnabled();
    public void setEnabled(boolean enabled);
    public int getMaxTries();
    public void setMaxTries(int maxTries);

}

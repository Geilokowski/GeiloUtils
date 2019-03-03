package play.ai.dragonrealm.geiloutils.new_configs.containers;

import play.ai.dragonrealm.geiloutils.new_configs.IJsonFile;
import play.ai.dragonrealm.geiloutils.new_configs.interfaces.IRandomTeleportConfig;

import javax.annotation.Nonnull;

public class RandomTPConfig implements IRandomTeleportConfig, IJsonFile {

    private int radius;
    private int minY;
    private int maxTries = -1;
    private boolean enabled;

    public int getRadius() {
        return radius;
    }
    public void setRadius(int radius) {
        this.radius = radius;
    }
    public int getMinY() {
        return minY;
    }
    public void setMinY(int minY) {
        this.minY = minY;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getMaxTries() {
        return maxTries;
    }

    public void setMaxTries(int maxTries) {
        this.maxTries = maxTries;
    }

    @Nonnull
    @Override
    public String getFileName() {
        return "rtp.json";
    }

    @Nonnull
    @Override
    public IJsonFile getDefaultJson() {
        RandomTPConfig configRTP = new RandomTPConfig();
        configRTP.setMinY(60);
        configRTP.setEnabled(true);
        configRTP.setRadius(10000);
        configRTP.setMaxTries(-1);
        return configRTP;
    }
}

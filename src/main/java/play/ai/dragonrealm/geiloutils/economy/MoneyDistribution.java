package play.ai.dragonrealm.geiloutils.economy;

import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigManager;

import java.util.Timer;

public class MoneyDistribution {

    public static void enablePaymentTimer() {
        if (ConfigManager.getEconomyConfig().isEnabled() && ConfigManager.getEconomyConfig().isPaymentTimerEnabled()) {
            Timer t = new Timer();
            PayUsersTask mTask = new PayUsersTask();

            t.scheduleAtFixedRate(mTask, 0, ConfigManager.getEconomyConfig().getPaymentTimeInSeconds() * 1000);

        }
    }

}

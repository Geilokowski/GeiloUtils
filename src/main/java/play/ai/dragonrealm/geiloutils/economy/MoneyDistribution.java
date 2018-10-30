package play.ai.dragonrealm.geiloutils.economy;

import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;

import java.util.Timer;

public class MoneyDistribution {

    public static void enablePaymentTimer() {
        if (ConfigurationManager.getEconomyConfig().isEnabled() && ConfigurationManager.getEconomyConfig().isPaymentTimerEnabled()) {
            Timer t = new Timer();
            PayUsersTask mTask = new PayUsersTask();

            t.scheduleAtFixedRate(mTask, 0, ConfigurationManager.getEconomyConfig().getPaymentTimeInSeconds() * 1000);

        }
    }

}

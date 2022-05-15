package play.ai.dragonrealm.geiloutils.economy;

import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;

import java.util.Timer;

public class MoneyDistribution {

    public static void enablePaymentTimer() {
        if (ConfigAccess.getEconomyConfig().isEnabled() && ConfigAccess.getEconomyConfig().isPaymentTimerEnabled()) {
            Timer t = new Timer();
            PayUsersTask mTask = new PayUsersTask();

            t.scheduleAtFixedRate(mTask, 0, ConfigAccess.getEconomyConfig().getPaymentTimeInSeconds() * 1000);

        }
    }

}

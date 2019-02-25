package play.ai.dragonrealm.geiloutils.new_configs.economy;

import play.ai.dragonrealm.geiloutils.config.economy.EcoItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IEcononmyConfig {



    public boolean isEnablePlayerNameIdentification();
    public void setEnablePlayerNameIdentification(boolean enablePlayerNameIdentification);


    public boolean isEnabled();
    public void setEnabled(boolean enabled);

    public double getStartingMoney();
    public void setStartingMoney(double startingMoney);
    public boolean isGoodOlCurrencyIntegration();
    public void setGoodOlCurrencyIntegration(boolean goodOlCurrencyIntegration);

    public List<EcoItem> getBuyItems();
    public void setBuyItems(List<EcoItem> buyItems);

    public List<EcoItem> getSellItems();
    public void setSellItems(List<EcoItem> sellItems);

    public double getBaseMultiplier();
    public void setBaseMultiplier(double baseMultiplier);

    public int getPaymentTimeInSeconds();
    public void setPaymentTimeInSeconds(int paymentTimeInSeconds);

    public int getBaseTierIncome();
    public void setBaseTierIncome(int baseTierIncome);

    public boolean isPaymentTimerEnabled();
    public void setEnablePaymentTimer(boolean enablePaymentTimer);

    public HashMap<String, Integer> getPermPaymentMap();
    public void setPermPaymentMap(HashMap<String, Integer> permPaymentMap);

}

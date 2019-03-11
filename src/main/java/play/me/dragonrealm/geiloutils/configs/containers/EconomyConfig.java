package play.me.dragonrealm.geiloutils.configs.containers;

import play.me.dragonrealm.geiloutils.configs.models.EcoItem;
import play.me.dragonrealm.geiloutils.configs.IJsonFile;
import play.me.dragonrealm.geiloutils.configs.interfaces.IEcononmyConfig;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EconomyConfig implements IEcononmyConfig, IJsonFile {

    private boolean enabled;
    private double startingMoney;
    private boolean goodOlCurrencyIntegration;
    private boolean enablePlayerNameIdentification;
    private boolean enablePaymentTimer;
    private int paymentTimeInSeconds;
    private int baseTierIncome;
    private double baseMultiplier;
    private HashMap<String, Integer> permPaymentMap;


    public boolean isEnablePlayerNameIdentification() {
        return enablePlayerNameIdentification;
    }
    public void setEnablePlayerNameIdentification(boolean enablePlayerNameIdentification) {
        this.enablePlayerNameIdentification = enablePlayerNameIdentification;
    }
    private List<EcoItem> buyItems = new ArrayList<EcoItem>();
    private List<EcoItem> sellItems = new ArrayList<EcoItem>();

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    public double getStartingMoney() {
        return startingMoney;
    }
    public void setStartingMoney(double startingMoney) {
        this.startingMoney = startingMoney;
    }
    public boolean isGoodOlCurrencyIntegration() {
        return goodOlCurrencyIntegration;
    }
    public void setGoodOlCurrencyIntegration(boolean goodOlCurrencyIntegration) {
        this.goodOlCurrencyIntegration = goodOlCurrencyIntegration;
    }
    public List<EcoItem> getBuyItems() {
        return buyItems;
    }
    public void setBuyItems(List<EcoItem> buyItems) {
        this.buyItems = buyItems;
    }
    public List<EcoItem> getSellItems() {
        return sellItems;
    }
    public void setSellItems(List<EcoItem> sellItems) {
        this.sellItems = sellItems;
    }

    public double getBaseMultiplier() {
        return baseMultiplier;
    }
    public int getPaymentTimeInSeconds() {
        return paymentTimeInSeconds;
    }

    public int getBaseTierIncome() {
        return baseTierIncome;
    }

    public boolean isPaymentTimerEnabled() {
        return enablePaymentTimer;
    }

    public void setBaseMultiplier(double baseMultiplier) {
        this.baseMultiplier = baseMultiplier;
    }

    public void setPaymentTimeInSeconds(int paymentTimeInSeconds) {
        this.paymentTimeInSeconds = paymentTimeInSeconds;
    }

    public void setBaseTierIncome(int baseTierIncome) {
        this.baseTierIncome = baseTierIncome;
    }

    public void setEnablePaymentTimer(boolean enablePaymentTimer) {
        this.enablePaymentTimer = enablePaymentTimer;
    }

    public HashMap<String, Integer> getPermPaymentMap() {
        return permPaymentMap;
    }

    public void setPermPaymentMap(HashMap<String, Integer> permPaymentMap) {
        this.permPaymentMap = permPaymentMap;
    }

    @Nonnull
    @Override
    public String getFileName() {
        return "economy.json";
    }

    @Nonnull
    @Override
    public IJsonFile getDefaultJson() {
        EconomyConfig defaultEconomyConfig = new EconomyConfig();
        defaultEconomyConfig.setEnabled(true);
        defaultEconomyConfig.setGoodOlCurrencyIntegration(false);
        defaultEconomyConfig.setStartingMoney(50);
        defaultEconomyConfig.setEnablePaymentTimer(false);
        defaultEconomyConfig.setPaymentTimeInSeconds(0);
        defaultEconomyConfig.setBaseTierIncome(0);
        defaultEconomyConfig.setBaseMultiplier(0.0);
        HashMap<String, Integer> payements = new HashMap<>();
        defaultEconomyConfig.setPermPaymentMap(payements);
        return defaultEconomyConfig;
    }
}

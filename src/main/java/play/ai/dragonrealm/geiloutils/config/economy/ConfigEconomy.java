package play.ai.dragonrealm.geiloutils.config.economy;

import java.util.ArrayList;
import java.util.List;

public class ConfigEconomy {
	private boolean enabled;
	private double startingMoney;
	private boolean goodOlCurrencyIntegration;
	private boolean enablePlayerNameIdentification;
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
}

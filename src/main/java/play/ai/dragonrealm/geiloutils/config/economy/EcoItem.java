package play.ai.dragonrealm.geiloutils.config.economy;

public class EcoItem {
	private String registryName;
	private int metadata;
	private double price;
	public String getRegistryName() {
		return registryName;
	}
	public void setRegistryName(String registryName) {
		this.registryName = registryName;
	}
	public int getMetadata() {
		return metadata;
	}
	public void setMetadata(int metadata) {
		this.metadata = metadata;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}

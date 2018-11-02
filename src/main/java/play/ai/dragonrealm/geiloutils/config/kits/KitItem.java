package play.ai.dragonrealm.geiloutils.config.kits;

public class KitItem {
	private String registryName;
	private int metadata;
	private int count;
	
	public KitItem(String registryName, int metadata, int count) {
		this.registryName = registryName;
		this.metadata = metadata;
		this.count = count;
	}
	
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
	public int getCount() {return count;}
	public void setCount(int count) {this.count = count;}
}

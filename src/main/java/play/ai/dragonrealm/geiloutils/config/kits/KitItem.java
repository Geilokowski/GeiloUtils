package play.ai.dragonrealm.geiloutils.config.kits;

public class KitItem {
	private String registryName;
	private int metadata;
	
	public KitItem(String registryName, int metadata) {
		this.registryName = registryName;
		this.metadata = metadata;
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
}

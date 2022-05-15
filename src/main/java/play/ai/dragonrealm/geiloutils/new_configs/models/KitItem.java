package play.ai.dragonrealm.geiloutils.new_configs.models;

import java.util.Map;

public class KitItem {
	private String registryName;
	private int metadata;
	private int count;
	private String nbtMap;
	
	public KitItem(String registryName, int metadata, int count, String nbt) {
		this.registryName = registryName;
		this.metadata = metadata;
		this.count = count;
		this.nbtMap = nbt;
	}

	public KitItem(String registryName, int metadata, int count) {
		this.registryName = registryName;
		this.metadata = metadata;
		this.count = count;
		this.nbtMap = null;
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

	public String getNbtMap() {
		return nbtMap;
	}

	public void setNbtMap(String nbtMap) {
		this.nbtMap = nbtMap;
	}
}

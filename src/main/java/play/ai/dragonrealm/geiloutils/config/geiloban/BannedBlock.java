package play.ai.dragonrealm.geiloutils.config.geiloban;

import net.minecraft.util.text.TextComponentString;

public class BannedBlock {
	private String registryName;
	private String metadata;
	private String dimension;
	private String permission;
	private String bypassPerm;

	public String getBypassPerm() {
		return bypassPerm;
	}

	public void setBypassPerm(String bypassPerm) {
		this.bypassPerm = bypassPerm;
	}

	public BannedBlock(String registryName, String metadata, String dimension) {
		this.registryName = registryName;
		this.metadata = metadata;
		this.dimension = dimension;
	}
	
	@Override
	public String toString() {
		String returnStr;
		if (this.getDimension().equals("all")){
			if(this.getMetadata().equals("all")) {
				returnStr = this.getRegistryName() + " is banned from all dimensions";
      	  	}else {
      	  		returnStr = this.getRegistryName() + "[metadata=" + this.getMetadata() + "] is banned from all dimensions";
      	  	}
        }else {
      	  	if(this.getMetadata().equals("all")) {
      	  		returnStr = this.getRegistryName() + " is banned from dimension " + this.getDimension();
      	  	} else {
      	  		returnStr = this.getRegistryName() + "[metadata=" + this.getMetadata() + "] is banned from dimension " + this.getDimension();
      	  	}
        }
		
		return returnStr;
	}

	public String getRegistryName() {
		return registryName;
	}

	public void setRegistryName(String registryName) {
		this.registryName = registryName;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	
}

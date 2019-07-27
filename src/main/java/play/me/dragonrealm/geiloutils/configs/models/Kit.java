package play.me.dragonrealm.geiloutils.configs.models;

import java.util.ArrayList;
import java.util.List;

public class Kit {
	private String name;
	private List<KitItem> items = new ArrayList<KitItem>();
	private List<Permission> permissions = new ArrayList<Permission>();
	private int cooldown = 0;
	
	public List<Permission> getPermissionList(){
		return permissions;
	}
	
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	
	public int getCooldown() {
		return cooldown;
	}
	
	public List<KitItem> getItems(){
		return items;
	}
	
	public void setItems(List<KitItem> items) {
		this.items = items;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		//TODO: Maybe add this to clean up the other classes
		/**
		String tmp = name;
		Kit kit = KitUtils.getKitByName(args[1]);
		// Beginning
		tmp = tmp + ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Kit found! ");
		
		// Name
		tmp = tmp + ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Name: " + kit.getName());
		
		// Cooldown
		tmp = tmp + ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Cooldown: " + kit.getCooldown() + "s");
		
		// Permission
		if(kit.getPermissionList().isEmpty()) {
			tmp = tmp + ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Permission needed: This kit is available to everyone");
			
		}else {
			String tmp = "";
			for(Permission perm : kit.getPermissionList()) {
				tmp = tmp +  perm.getName() + ", ";
			}
			tmp = tmp + ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Permission needed: " + tmp.substring(0, tmp.length() - 1));
			
		}
		// Items
		if(kit.getItems().isEmpty()) {
			tmp = tmp + ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Items: No items added yet. Add one with /kit addItem <kit> <registryname> <metadata>");
			
		}else {
			String tmp2 = "";
			for(KitItem kitItem : kit.getItems()) {
				tmp = tmp +  kitItem.getRegistryName() + "[" + kitItem.getMetadata() + "]" + ", ";
			}
			tmp = tmp + ConfigurationManager.getGeneralConfig().getCommandPrefix() + "Items: " + tmp.substring(0, tmp.length() - 1));
			
		}
		return tmp;
		**/
		
		return "This is WIP...";
	}
}

package play.ai.dragonrealm.geiloutils.config.permissions;

public class Permission {
	private String name;
	
	public Permission(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "Permission: " + name;
	}
}

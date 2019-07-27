package play.me.dragonrealm.geiloutils.configs.models;

import java.util.Date;

public class KitLastUsed {
	private String kitname;
	private Date lastUsed;
	
	public KitLastUsed(String kitname, Date lastUsed) {
		this.kitname = kitname;
		this.lastUsed = lastUsed;
	}
	
	public String getKitname() {
		return kitname;
	}
	public void setKitname(String kitname) {
		this.kitname = kitname;
	}
	public Date getLastUsed() {
		return lastUsed;
	}
	public void setLastUsed(Date lastUsed) {
		this.lastUsed = lastUsed;
	}
}

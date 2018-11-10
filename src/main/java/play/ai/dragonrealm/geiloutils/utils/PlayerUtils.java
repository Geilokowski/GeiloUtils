package play.ai.dragonrealm.geiloutils.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.config.playerstats.Playerstat;

import java.util.List;

public class PlayerUtils {
	public static String[] getOnlinePlayerNames() {
		return FMLCommonHandler.instance().getMinecraftServerInstance().getOnlinePlayerNames();
	}
	
	public static boolean isFirstJoin(EntityPlayer player) {
		for(Playerstat ps : ConfigurationManager.getPlayerstats().getPlayerstats()) {
			if(ps.getUuid().equals(player.getCachedUniqueIdString())) {
				return false;
			}
		}
		
		return true;
	}
	
	public static int removeItemByName(EntityPlayer player, String unlocalizedName){
		int counter = 0;
	    for (int i = 0; i < player.inventory.getSizeInventory(); i++)
	    {
	    	ItemStack currentItem = player.inventory.getStackInSlot(i);
	    	if (currentItem.getUnlocalizedName().toString().equals(unlocalizedName)){
	    		player.inventory.removeStackFromSlot(i);
	    		counter += currentItem.getCount();
	    	}
	    }
	    return counter;
	  }
	  
	  public static void removeItemFromMainhand(EntityPlayer player)
	  {
	    for (int i = 0; i < player.inventory.getSizeInventory(); i++)
	    {
	      ItemStack currentItem = player.inventory.getStackInSlot(i);
	      if (currentItem.equals(player.getHeldItemMainhand())) {
	        player.inventory.removeStackFromSlot(i);
	      }
	    }
	  }
	  
	  public static Playerstat getPlayerstatByUUID(String uuid) {
		  for(Playerstat ps : ConfigurationManager.getPlayerstats().getPlayerstats()) {
			  if(ps.getUuid().equals(uuid)) {
				  return ps;
			  }
		  }
		  
		  return null;
	  }
	  
	  public static void updatePlayerstat(Playerstat ps) {
		  for(Playerstat oldPlayerstats : ConfigurationManager.getPlayerstats().getPlayerstats()) {
			  if(oldPlayerstats.getUuid().equals(ps.getUuid())) {
				  ConfigurationManager.getPlayerstats().getPlayerstats().remove(oldPlayerstats);
				  ConfigurationManager.getPlayerstats().getPlayerstats().add(ps);
				  ConfigurationManager.syncFromFields();
				  return;
			  }
		  }
	  }
	  
	  public static void removePlayerMoney(EntityPlayer player, double amount)
	  {
		  Playerstat ps = getPlayerstatByUUID(player.getCachedUniqueIdString());
		  double newMoney = ps.getMoney() - amount;
		  
		  ps.setMoney(newMoney);
		  updatePlayerstat(ps);
		  ConfigurationManager.syncFromFields();
	  }
	  
	  public static void addPlayerMoney(EntityPlayer player, double amount)
	  {
		  if(player == null) {
			  return;
		  }
		  
		  GeiloUtils.getLogger().info(player.getCachedUniqueIdString());
		  Playerstat ps = getPlayerstatByUUID(player.getCachedUniqueIdString());
		  double newMoney = ps.getMoney() + amount;
	    
		  ps.setMoney(newMoney);
		  updatePlayerstat(ps);
		  ConfigurationManager.syncFromFields();
	  }

	  public static void addToMuteList(EntityPlayer player, String addition) {
		if(player == null) {
			return;
		}

		Playerstat ps = getPlayerstatByUUID(player.getCachedUniqueIdString());
		List<String> muted = ps.getMutedChats();
		muted.add(addition);
		ps.setMutedChats(muted);

		updatePlayerstat(ps);
		ConfigurationManager.syncFromFields();
	  }

	public static boolean removeFromMuteList(EntityPlayer player, String removal) {
		if(player == null) {
			return false;
		}

		Playerstat ps = getPlayerstatByUUID(player.getCachedUniqueIdString());
		List<String> muted = ps.getMutedChats();

		if(muted.contains(removal)) {
			muted.remove(removal);
			updatePlayerstat(ps);
			ConfigurationManager.syncFromFields();
			return true;
		}

		return false;
	}
	  
	  public static double getPlayerBalance(EntityPlayer player)
	  {
		  Playerstat ps = getPlayerstatByUUID(player.getCachedUniqueIdString());
		  return ps.getMoney();
	  }
	  
	  public static EntityPlayer getPlayerByName(String name)
	  {
		  return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(name);
	  }
	  
	  public static boolean addItemByName(EntityPlayer player, String itemName, int amount, int meta)
	  {
	    return player.inventory.addItemStackToInventory(new ItemStack(ItemUtils.getItemFromMod(itemName), amount, meta));
	  }
}

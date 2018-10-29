package play.ai.dragonrealm.geiloutils.utils;

import java.util.Collection;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemUtils {
	public static Item getItemFromMod(String name)
	  {
	    Collection<Item> itemCollection = ForgeRegistries.ITEMS.getValuesCollection();
	    for (Item item : itemCollection) {
	      if (item.getRegistryName().toString().equals(name)) {
	        return item;
	      }
	    }
	    return null;
	  }
	  
	  public static Item getItemFromUnlocalized(String name)
	  {
	    Collection<Item> itemCollection = ForgeRegistries.ITEMS.getValuesCollection();
	    for (Item item : itemCollection) {
	      if (item.getUnlocalizedName().toString().equals(name)) {
	        return item;
	      }
	    }
	    return null;
	  }
}

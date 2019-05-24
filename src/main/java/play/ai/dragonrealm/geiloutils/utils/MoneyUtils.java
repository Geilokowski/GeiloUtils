package play.ai.dragonrealm.geiloutils.utils;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class MoneyUtils {
	public static Map<String, Double> moneyValues = new HashMap();
	  public static Map<Double, String> moneyValuesDS = new HashMap();
	  
	  public static void init()
	  {
	    moneyValues.put("item.modcurrency:coin_0", 0.01D);
	    moneyValues.put("item.modcurrency:coin_1", 0.05D);
	    moneyValues.put("item.modcurrency:coin_2", 0.1D);
	    moneyValues.put("item.modcurrency:coin_3", 0.25D);
	    moneyValues.put("item.modcurrency:coin_4", 1.0D);
	    moneyValues.put("item.modcurrency:coin_5", 2.0D);
	    
	    //moneyValues.put("item.modcurrency:banknote_0", 1.0D);
	    moneyValues.put("item.modcurrency:banknote_1", 5.0D);
	    moneyValues.put("item.modcurrency:banknote_2", 10.0D);
	    moneyValues.put("item.modcurrency:banknote_3", 20.0D);
	    moneyValues.put("item.modcurrency:banknote_4", 50.0D);
	    moneyValues.put("item.modcurrency:banknote_5", 100.0D);
	    
	    moneyValuesDS.put(0.01D, "item.modcurrency:coin_0");
	    moneyValuesDS.put(0.05D, "item.modcurrency:coin_1");
	    moneyValuesDS.put(0.1D, "item.modcurrency:coin_2");
	    moneyValuesDS.put(0.25D, "item.modcurrency:coin_3");
	    moneyValuesDS.put(1.0D, "item.modcurrency:coin_4");
	    moneyValuesDS.put(2.0D, "item.modcurrency:coin_5");
	    
	    //moneyValuesDS.put(1.0D, "item.modcurrency:banknote_0");
	    moneyValuesDS.put(5.0D, "item.modcurrency:banknote_1");
	    moneyValuesDS.put(10.0D, "item.modcurrency:banknote_2");
	    moneyValuesDS.put(20.0D, "item.modcurrency:banknote_3");
	    moneyValuesDS.put(50.0D, "item.modcurrency:banknote_4");
	    moneyValuesDS.put(100.0D, "item.modcurrency:banknote_5");
	  }
	  
	  public static double getMoneyValueAndRemove(EntityPlayer player)
	  {
	    double counter = 0.0D;
	    for (int i = 0; i < player.inventory.getSizeInventory(); i++){
	      ItemStack currentItem = player.inventory.getStackInSlot(i);
	      if (moneyValues.get(currentItem.getUnlocalizedName()) != null)
	      {  
	        counter += ((Double)moneyValues.get(currentItem.getUnlocalizedName())) * currentItem.getCount();
	        player.inventory.removeStackFromSlot(i);
	      }
	    }
	    return counter;
	  }
}

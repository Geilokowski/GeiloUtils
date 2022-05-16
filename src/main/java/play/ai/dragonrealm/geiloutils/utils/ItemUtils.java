package play.ai.dragonrealm.geiloutils.utils;

import java.util.Collection;

import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistries;


public class ItemUtils {
	public static Item getItemFromMod(String registryName) {
        return ForgeRegistries.ITEMS.getValues().stream().filter(item -> item.getRegistryName().toString().equals(registryName)).findFirst().orElse(null);
    }

    public static boolean doesItemExist(String registryName){
	    return (getItemFromMod(registryName) != null);
    }
	  
	public static Item getItemFromUnlocalized(String name) {
        return ForgeRegistries.ITEMS.getValues().stream().filter(item -> item.getRegistryName().toString().equals(name)).findFirst().orElse(null);
    }
}

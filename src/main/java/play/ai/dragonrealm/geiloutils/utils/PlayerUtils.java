package play.ai.dragonrealm.geiloutils.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.new_configs.containers.PlayerStatsConfig;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;

import java.util.List;

public class PlayerUtils {

	public static String[] getOnlinePlayerNames() {
		return FMLCommonHandler.instance().getMinecraftServerInstance().getOnlinePlayerNames();
	}

	public static int removeItemByName(EntityPlayer player, String unlocalizedName){
		int counter = 0;
	    for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
	    	ItemStack currentItem = player.inventory.getStackInSlot(i);
	    	if (currentItem.getUnlocalizedName().toString().equals(unlocalizedName)){
	    		player.inventory.removeStackFromSlot(i);
	    		counter += currentItem.getCount();
	    	}
	    }
	    return counter;
	}
	  
	public static void removeItemFromMainhand(EntityPlayer player) {
		for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack currentItem = player.inventory.getStackInSlot(i);
			if (currentItem.equals(player.getHeldItemMainhand())) {
				player.inventory.removeStackFromSlot(i);
			}
	    }
	}

	@Deprecated
	public static Playerstat getPlayerstatByUUID(String uuid) {
		return GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).getPlayerStatByUUID(uuid).orElse(null);
	}


	public static EntityPlayer getPlayerByName(String name) {
		return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(name);
	}
	  
	public static boolean addItemByName(EntityPlayer player, String itemName, int amount, int meta) {
		return player.inventory.addItemStackToInventory(new ItemStack(ItemUtils.getItemFromMod(itemName), amount, meta));
	}

	public static boolean addItemByName(EntityPlayer player, String itemName, int amount, int meta, NBTTagCompound nbtCompound)
	{
        ItemStack stack = new ItemStack(ItemUtils.getItemFromMod(itemName), amount, meta);
        stack.setTagCompound(nbtCompound);
		return player.inventory.addItemStackToInventory(stack);
	}

}

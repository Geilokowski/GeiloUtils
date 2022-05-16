package play.ai.dragonrealm.geiloutils.utils;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.new_configs.containers.PlayerStatsConfig;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;

import java.util.List;

public class PlayerUtils {

	public static String[] getOnlinePlayerNames() {
		return McFacade.getServerPlayerList().getPlayerNamesArray();
	}

	public static int removeItemByName(PlayerEntity player, String unlocalizedName){
		int counter = 0;
	    for (int i = 0; i < player.inventory.getContainerSize(); i++) {
	    	ItemStack currentItem = player.inventory.getItem(i);
	    	if (currentItem.getItem().getRegistryName().toString().equals(unlocalizedName)){
	    		player.inventory.removeItemNoUpdate(i);
	    		counter += currentItem.getCount();
	    	}
	    }
	    return counter;
	}
	  
	public static void removeItemFromMainhand(PlayerEntity player) {
		for (int i = 0; i < player.inventory.getContainerSize(); i++) {
			ItemStack currentItem = player.inventory.getItem(i);
			if (currentItem.equals(player.getItemInHand(Hand.MAIN_HAND))) {
				player.inventory.removeItemNoUpdate(i);
			}
	    }
	}

	@Deprecated
	public static Playerstat getPlayerstatByUUID(String uuid) {
		return GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).getPlayerStatByUUID(uuid).orElse(null);
	}


	public static PlayerEntity getPlayerByName(String name) {
		return McFacade.getServerPlayerList().getPlayerByName(name);
	}
	  
	public static boolean addItemByName(ServerPlayerEntity player, String itemName, int amount, int meta) {
		return false; //player.inventory.addItemStackToInventory(new ItemStack(ItemUtils.getItemFromMod(itemName), amount, meta));
	}

	public static boolean addItemByName(PlayerEntity player, String itemName, int amount, int meta, CompoundNBT nbtCompound)
	{
        ItemStack stack = new ItemStack(ItemUtils.getItemFromMod(itemName), amount, nbtCompound);
		return player.inventory.add(stack);
	}

}

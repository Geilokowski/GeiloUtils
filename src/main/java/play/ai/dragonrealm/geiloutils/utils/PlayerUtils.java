package play.ai.dragonrealm.geiloutils.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.exceptions.NoSuchDimensionException;
import play.ai.dragonrealm.geiloutils.new_configs.containers.PlayerStatsConfig;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;
import play.ai.dragonrealm.geiloutils.discord.command.GeiloPorter;

public class PlayerUtils {

	public static void teleportToDimension(EntityPlayer player, int dimension, double x, double y, double z) throws NoSuchDimensionException{
		if(!DimensionManager.isDimensionRegistered(dimension)){
			throw new NoSuchDimensionException();
		}

		int oldDimension = player.getEntityWorld().provider.getDimension();
		EntityPlayerMP entityPlayerMP = (EntityPlayerMP) player;
		MinecraftServer server = player.getEntityWorld().getMinecraftServer();
		WorldServer worldServer = server.getWorld(dimension);
		player.addExperienceLevel(0);

		worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(entityPlayerMP, dimension, new GeiloPorter(worldServer, x, y, z));
		player.setPositionAndUpdate(x, y, z);
		if (oldDimension == 1) {
			// For some reason teleporting out of the end does weird things.
			player.setPositionAndUpdate(x, y, z);
			//WorldTools.spawnEntity(worldServer, player);
			worldServer.updateEntityWithOptionalForce(player, false);
		}
	}

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

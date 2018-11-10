package play.ai.dragonrealm.geiloutils.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.config.geiloban.BannedBlock;

public class EventHandlerBlocks {
	@SubscribeEvent(priority=EventPriority.NORMAL)
	  public void onBlockPlace(BlockEvent.PlaceEvent event)
	  {
	    if (isBannedBlock(event.getPlacedBlock().getBlock().getRegistryName().toString(), event.getPlayer().getHeldItemMainhand().getMetadata(), event.getPlayer()) && !event.getPlayer().getName().equals("Geilokowski"))
	    {
	      event.setCanceled(true);
	      ITextComponent msg = new TextComponentString("[GeiloUtils] You are not allowed to place that here. To get the block back just click on the inventory slot it was in.");
	      event.getPlayer().sendMessage(msg);
	    }
	  }
	  
	  public static boolean isBannedBlock(String checkName, int checkMetadata, EntityPlayer player)
	  {
	    for (BannedBlock bannedBlock : ConfigurationManager.getBannedBlocksConfig().getBannedBlocks()) {
	      if (bannedBlock.getDimension().equals("all")){
		      if (checkName.equals(bannedBlock.getRegistryName()) && (bannedBlock.getMetadata().equals("all") || Integer.parseInt(bannedBlock.getMetadata()) == checkMetadata)) {
		    	  return true;
		      }
	      } else {
		      if ((bannedBlock.getDimension().equals("all") || player.dimension == Integer.parseInt(bannedBlock.getDimension())) && (checkName.equals(bannedBlock.getRegistryName()) && (bannedBlock.getMetadata().equals("all") || Integer.parseInt(bannedBlock.getMetadata()) == checkMetadata))) {
		        return true;
		      }
	      }
	    }
	    return false;
	  }
}

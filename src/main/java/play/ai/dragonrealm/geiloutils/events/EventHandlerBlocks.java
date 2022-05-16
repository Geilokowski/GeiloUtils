package play.ai.dragonrealm.geiloutils.events;


import net.minecraft.entity.Entity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import play.ai.dragonrealm.geiloutils.new_configs.models.BannedBlock;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;

public class EventHandlerBlocks {

	@SubscribeEvent(priority= EventPriority.NORMAL)
	  public void onBlockPlace(BlockEvent.EntityPlaceEvent event)
	  {
	    if (isBannedBlock(event.getPlacedBlock().getBlock().getRegistryName().toString(), event.getPlacedBlock().getBlockState().toString(), event.getEntity()) && !event.getEntity().getName().equals("Geilokowski"))
	    {
	      event.setCanceled(true);
	      ITextComponent msg = new StringTextComponent("[GeiloUtils] You are not allowed to place that here. To get the block back just click on the inventory slot it was in.");
	      event.getEntity().sendMessage(msg, event.getEntity().getUUID());
	    }
	  }
	  
	  public static boolean isBannedBlock(String checkName, String blockstate, Entity player)
	  {
	    for (BannedBlock bannedBlock : ConfigAccess.getBannedBlocksConfig().getBannedBlocks()) {
	      if (bannedBlock.getDimension().equals("all")){
		      if (checkName.equals(bannedBlock.getRegistryName()) && (bannedBlock.getMetadata().equals("all") /*|| Integer.parseInt(bannedBlock.getMetadata()) == checkMetadata*/)) {
		    	  return true;
		      }
	      } else {
		      if ((bannedBlock.getDimension().equals("all") || player.getCommandSenderWorld().dimension().getRegistryName().getPath().equals(bannedBlock.getDimension())) &&
				  (checkName.equals(bannedBlock.getRegistryName()) && (bannedBlock.getMetadata().equals("all") /*|| Integer.parseInt(bannedBlock.getMetadata()) == checkMetadata*/))) {
		        return true;
		      }
	      }
	    }
	    return false;
	  }
}

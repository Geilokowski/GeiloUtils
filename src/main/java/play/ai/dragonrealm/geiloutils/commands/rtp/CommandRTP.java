package play.ai.dragonrealm.geiloutils.commands.rtp;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;

public class CommandRTP extends CommandBase{

	Random r = new Random();
	public static Block[] dangerBlockArray = { Blocks.LAVA, Blocks.FLOWING_LAVA, Blocks.WATER, Blocks.FLOWING_WATER, Blocks.AIR };
	@Override
	public String getName() {
	    return "rtp";
	}

	@Override
	public String getUsage(ICommandSender sender) {
	    return "Teleports you to a random location";
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if ((sender instanceof EntityPlayer)) {
			EntityPlayer player = (EntityPlayer)sender;
	    	if(!ConfigAccess.getRTPConfig().isEnabled()){
				ITextComponent msg = new TextComponentString("[GeiloRTP] RTP is disabled on the server!");
				player.sendMessage(msg);
				return;
			}

		  	int newX = this.r.nextInt(ConfigAccess.getRTPConfig().getRadius() * 2) - ConfigAccess.getRTPConfig().getRadius();
		  	int newY = ConfigAccess.getRTPConfig().getMinY();
		  	int newZ = this.r.nextInt(ConfigAccess.getRTPConfig().getRadius() * 2) - ConfigAccess.getRTPConfig().getRadius();
		  	int maxTries = ConfigAccess.getRTPConfig().getMaxTries();
		  	while (!isSafe(player, newX, newY, newZ) && (maxTries == -1 || maxTries > 0)) {
				newY++;
				if (newY > 120) {
			  		newX = this.r.nextInt(ConfigAccess.getRTPConfig().getRadius() * 2) - ConfigAccess.getRTPConfig().getRadius();
			  		newY = ConfigAccess.getRTPConfig().getMinY();
			  		newZ = this.r.nextInt(ConfigAccess.getRTPConfig().getRadius() * 2) - ConfigAccess.getRTPConfig().getRadius();
				}
				if(maxTries > 0){
					maxTries--;
				}
				if(maxTries == 0) {
					ITextComponent msg = new TextComponentString("[GeiloRTP] Could not find a safe space to RTP, please try again!");
					player.sendMessage(msg);
					return;
				}
		  	}

		  	player.setPositionAndUpdate(newX, newY, newZ);

		  	ITextComponent msg = new TextComponentString("[GeiloRTP] You were teleported to X: " + newX + " Y: " + newY + " Z: " + newZ);
		  	player.sendMessage(msg);
	    }
	}
	
	public boolean isSafe(EntityPlayer player, int newX, int newY, int newZ)
	  {
	    if ((isEmpty(player.world, newX, newY, newZ)) && 
	      (!isDangerBlock(player.world, newX, newY - 1, newZ))) {
	      return true;
	    }
	    return false;
	  }
	  
	  public boolean isEmpty(World world, int newX, int newY, int newZ)
	  {
	    if ((world.isAirBlock(new BlockPos(newX, newY, newZ))) && (world.isAirBlock(new BlockPos(newX, newY + 1, newZ))) && 
	      (world.isAirBlock(new BlockPos(newX + 1, newY, newZ))) && (world.isAirBlock(new BlockPos(newX - 1, newY, newZ))) && 
	      (world.isAirBlock(new BlockPos(newX, newY, newZ + 1))) && (world.isAirBlock(new BlockPos(newX, newY, newZ - 1)))) {
	      return true;
	    }
	    return false;
	  }
	  
	  public boolean isDangerBlock(World world, int newX, int newY, int newZ)
	  {
	    for (Block block : dangerBlockArray) {
	      if (block.equals(world.getBlockState(new BlockPos(newX, newY, newZ)).getBlock())) {
	        return true;
	      }
	    }
	    return false;
	  }

}

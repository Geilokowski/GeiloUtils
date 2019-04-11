package play.ai.dragonrealm.geiloutils.commands.admin;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class CommandRN extends CommandBase{

	@Override
	public String getName() {
		return "rn";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "rn";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
	    if ((sender instanceof EntityPlayer))
	    {
	      EntityPlayer player = (EntityPlayer)sender;
	      ITextComponent msg = new TextComponentString("[GeiloAdmin] The registry name of the item in your hand is: " + player.getHeldItemMainhand().getItem().getRegistryName() + ". The Metadata is: " + player.getHeldItemMainhand().getMetadata());
	      player.sendMessage(msg);
	      //msg = new TextComponentString("[GeiloAdmin] The unlocalized name of the item in your hand is: " + player.getHeldItemMainhand().getUnlocalizedName());
	    }
	}

}

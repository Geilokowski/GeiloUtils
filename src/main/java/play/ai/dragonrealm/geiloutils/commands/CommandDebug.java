package play.ai.dragonrealm.geiloutils.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class CommandDebug extends CmdBase implements IInventoryChangedListener {
	EntityPlayer player;
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "debug";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		// TODO Auto-generated method stub
		player = (EntityPlayer) sender;
		InventoryBasic testInv = new InventoryBasic("TestInv",false,10);
		testInv.addInventoryChangeListener(this);
		testInv.setInventorySlotContents(2, new ItemStack(Items.ARROW));
		player.displayGUIChest(testInv);
	}

	public void onInventoryChanged(IInventory invBasic){
		player.closeScreen();
	}
}

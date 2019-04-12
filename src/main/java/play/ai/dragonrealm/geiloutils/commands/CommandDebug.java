package play.ai.dragonrealm.geiloutils.commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.ILockableContainer;
import play.ai.dragonrealm.geiloutils.gui.inventories.ChestInventory;
import play.ai.dragonrealm.geiloutils.gui.listeners.IInventoryChangedListener;

public class CommandDebug extends CmdBase implements IInventoryChangedListener {
	EntityPlayerMP player;
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
		player = (EntityPlayerMP) sender;
		ChestInventory testInv = new ChestInventory("TestInv",false,10, player);

		player.connection.sendPacket(new SPacketOpenWindow(player.currentWindowId,"minecraft:container",testInv.getDisplayName(),testInv.getSizeInventory()));
		//player.openContainer = new ContainerChest(player.inventory,testInv,player);
		player.displayGUIChest(testInv);
		testInv.addInventoryChangeListener(this);
		//player.connection.sendPacket(new SPacketSetSlot(player.currentWindowId,0, new ItemStack(Items.ARROW)));
	}

	boolean flag = true;
	public void onInventoryChanged(ChestInventory chestInv, EntityPlayer player) {
		//chestInv.setInventorySlotContentsWithoutNotifiy(0, new ItemStack(Items.CARROT));
	}
}

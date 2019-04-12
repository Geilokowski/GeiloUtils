package play.ai.dragonrealm.geiloutils.gui.listeners;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import play.ai.dragonrealm.geiloutils.gui.inventories.ChestInventory;

public interface IInventoryChangedListener {
    /**
     * Called by InventoryBasic.onInventoryChanged() on a array that is never filled.
     */
    void onInventoryChanged(ChestInventory chestInv, EntityPlayer player);
}

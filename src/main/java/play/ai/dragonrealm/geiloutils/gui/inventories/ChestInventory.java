package play.ai.dragonrealm.geiloutils.gui.inventories;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.inventory.InventoryBasic;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import play.ai.dragonrealm.geiloutils.gui.listeners.IInventoryChangedListener;

import java.util.List;

public class ChestInventory extends InventoryBasic {
    private List<IInventoryChangedListener> changeListeners;
    private final NonNullList<ItemStack> inventoryContents;
    EntityPlayer player;
    public ChestInventory(String title, boolean customName, int slotCount, EntityPlayer player) {
        super(title, customName, slotCount);
        this.inventoryContents = NonNullList.withSize(slotCount, ItemStack.EMPTY);
        this.player = player;
    }

    public void addInventoryChangeListener(IInventoryChangedListener listener)
    {
        if (this.changeListeners == null)
        {
            this.changeListeners = Lists.newArrayList();
        }

        this.changeListeners.add(listener);
    }

    @Override
    public void markDirty()
    {
        if (this.changeListeners != null)
        {
            for (int i = 0; i < this.changeListeners.size(); ++i)
            {
                (this.changeListeners.get(i)).onInventoryChanged(this, player);
            }
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections) without notifying the listeners
     */
    public void setInventorySlotContentsWithoutNotifiy(int index, ItemStack stack)
    {
        this.inventoryContents.set(index, stack);

        if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }
    }

    public void setInventorySlotContents(int index, ItemStack stack)
    {
        this.inventoryContents.set(index, stack);

        if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        this.markDirty();
    }

    /**
     * Returns the stack in the given slot.
     */
    public ItemStack getStackInSlot(int index)
    {
        return index >= 0 && index < this.inventoryContents.size() ? (ItemStack)this.inventoryContents.get(index) : ItemStack.EMPTY;
    }

}

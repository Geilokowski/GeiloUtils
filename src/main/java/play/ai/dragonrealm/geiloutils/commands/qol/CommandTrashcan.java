package play.ai.dragonrealm.geiloutils.commands.qol;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;

public class CommandTrashcan extends CmdBase
{
    @Override
    public String getName() {
        return "trashcan";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            player.displayGUIChest(new InventoryBasic("Trashcan", false, 10));
        }else{
            sendMsg(sender, "This is a in-game only command");
        }
    }
}

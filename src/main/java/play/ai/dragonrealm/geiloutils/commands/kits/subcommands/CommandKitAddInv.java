package play.ai.dragonrealm.geiloutils.commands.kits.subcommands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.new_configs.models.Kit;
import play.ai.dragonrealm.geiloutils.new_configs.models.KitItem;
import play.ai.dragonrealm.geiloutils.utils.KitUtils;

public class CommandKitAddInv extends CmdBase {
    @Override
    public String getName() {
        return "addInv";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return " ";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 1){
            EntityPlayer player;
            if(sender instanceof EntityPlayer){
                player = (EntityPlayer) sender;
            }else{
                sendMsg(sender, "Sorry, the command addInv is player-only");
                return;
            }

            if(KitUtils.doesKitExist(args[0])) {
                Kit kit = KitUtils.getKitByName(args[0]);
                for(int i = 0; i <= player.inventory.getSizeInventory(); i++){
                    if(!player.inventory.getStackInSlot(i).isEmpty()) {
                        if (player.inventory.getStackInSlot(i).getTagCompound() != null) {
                            kit.getItems().add(new KitItem(player.inventory.getStackInSlot(i).getItem().getRegistryName().toString(), player.inventory.getStackInSlot(i).getMetadata(), player.inventory.getStackInSlot(i).getCount(), player.inventory.getStackInSlot(i).getTagCompound().toString()));
                        } else {
                            kit.getItems().add(new KitItem(player.inventory.getStackInSlot(i).getItem().getRegistryName().toString(), player.inventory.getStackInSlot(i).getMetadata(), player.inventory.getStackInSlot(i).getCount()));
                        }
                    }
                }

                KitUtils.updateKit(kit);
            }
        }
    }
}

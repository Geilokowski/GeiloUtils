package play.ai.dragonrealm.geiloutils.commands.kits.subcommands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.new_configs.models.Kit;
import play.ai.dragonrealm.geiloutils.new_configs.models.KitItem;
import play.ai.dragonrealm.geiloutils.utils.ItemUtils;
import play.ai.dragonrealm.geiloutils.utils.KitUtils;

public class CommandKitRemoveItem extends CmdBase {
    @Override
    public String getName() {
        return "removeItem";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return " ";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 3) {
            if(KitUtils.doesKitExist(args[0])) {
                Kit kit = KitUtils.getKitByName(args[0]);
                if(ItemUtils.doesItemExist(args[1])){
                    KitItem kitItem = new KitItem(args[1], Integer.parseInt(args[2]), 0);
                    if(KitUtils.doesKitHaveItem(kit, kitItem)){
                        KitUtils.updateKit(KitUtils.removeItemFromKit(kit, kitItem));

                        sendMsg(sender, "Removed the item from the Kit");

                    }else{
                        sendMsg(sender, "Kit doesn't have that item");

                    }
                }else{
                    sendMsg(sender, "Couldn't find item '" + args[1] + "'. Try tab completion");

                }
            }else{
                sendMsg(sender, "Couldn't find kit '" + args[0] + "'. Try /geilokit list");

            }
        }
    }
}

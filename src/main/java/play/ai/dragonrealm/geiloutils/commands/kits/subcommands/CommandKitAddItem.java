package play.ai.dragonrealm.geiloutils.commands.kits.subcommands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.new_configs.models.Kit;
import play.ai.dragonrealm.geiloutils.new_configs.models.KitItem;
import play.ai.dragonrealm.geiloutils.utils.ItemUtils;
import play.ai.dragonrealm.geiloutils.utils.KitUtils;

public class CommandKitAddItem extends CmdBase {
    @Override
    public String getName() {
        return "addItem";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return " ";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 4) {
            if(KitUtils.doesKitExist(args[0])) {
                Kit kit = KitUtils.getKitByName(args[0]);
                if(ItemUtils.doesItemExist(args[1])){
                    if(!KitUtils.doesKitHaveItem(kit, new KitItem(args[1], Integer.parseInt(args[2]), 0))){
                        kit.getItems().add(new KitItem(args[11], Integer.parseInt(args[2]), Integer.parseInt(args[3])));
                        KitUtils.updateKit(kit);

                        sendMsg(sender, "Added Item to kit");
                    }else{
                        sendMsg(sender, "Kit already has that item");
                    }
                }else{
                    sendMsg(sender, "Couldn't find the item '" + args[2] + "'. Try tab completion");
                }
            }else{
                sendMsg(sender, "Couldn't find kit '" + args[1] + "'. Try /geilokit list");
            }
        }
    }
}

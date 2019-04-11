package play.ai.dragonrealm.geiloutils.commands.kits.subcommands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.new_configs.models.Kit;
import play.ai.dragonrealm.geiloutils.utils.KitUtils;

public class CommandKitSetCooldown extends CmdBase {
    @Override
    public String getName() {
        return "setCooldown";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Sets a cooldown";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 2) {
            if(KitUtils.doesKitExist(args[0])) {
                int time = 0;
                try{
                    time = Integer.parseInt(args[1]);
                } catch (Exception e){
                    sendMsg(sender, "Please put in a number");

                    return;
                }

                Kit kit = KitUtils.getKitByName(args[0]);
                kit.setCooldown(time);
                KitUtils.updateKit(kit);
                sendMsg(sender, "Cooldown set");

            }else{
                sendMsg(sender, "kit not found");

            }
        }
    }
}

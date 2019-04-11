package play.ai.dragonrealm.geiloutils.commands.kits.subcommands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.new_configs.models.Kit;
import play.ai.dragonrealm.geiloutils.utils.KitUtils;

public class CommandKitCreate extends CmdBase {
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return " ";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 1 && !args[0].equals("")) {
            if(KitUtils.doesKitExist(args[0])) {
                sendMsg(sender, "This kit already exists");
                return;
            }else {
                Kit kit = new Kit();
                kit.setName(args[0]);
                ConfigAccess.getKitConfig().getKits().add(kit);
                ConfigAccess.writeKitFile();

                sendMsg(sender, "Created the kit '" + kit.getName() + "'");
            }
        }
    }
}

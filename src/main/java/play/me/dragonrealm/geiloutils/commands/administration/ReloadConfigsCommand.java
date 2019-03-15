package play.me.dragonrealm.geiloutils.commands.administration;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import play.me.dragonrealm.geiloutils.GeiloUtils;
import play.me.dragonrealm.geiloutils.commands.CommandBase;

public class ReloadConfigsCommand extends CommandBase {

    public ReloadConfigsCommand() {
        super("geiloreload");
    }

    @Override
    public String getCmdDesc() {
        return "Reloads all in memory data from the JSON files.";
    }

    @Override
    public String getCmdUsage() {
        return "/geiloreload";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(sender instanceof Player && ((Player)sender).hasPermission("geiloutils.admin_perms")) {
            GeiloUtils.getManager().readFilesToRuntime();
            sender.sendMessage("GeiloUtils Reloaded Configs.");
            return true;
        } else {
            if(!(sender instanceof Player)){
                GeiloUtils.getManager().readFilesToRuntime();
                sender.sendMessage("GeiloUtils Reloaded Configs.");
                return true;
            }
        }
        return false;
    }
}

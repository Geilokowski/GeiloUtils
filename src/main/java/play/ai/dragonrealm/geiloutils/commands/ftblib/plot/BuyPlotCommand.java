package play.ai.dragonrealm.geiloutils.commands.ftblib.plot;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.commands.ftblib.FTBIntegrationCommandBase;
import play.ai.dragonrealm.geiloutils.new_configs.models.SellablePlots;


public class BuyPlotCommand extends FTBIntegrationCommandBase {

    @Override
    public String getName() {
        return "buy";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "buy [plot_name]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length != 1){
            messageSender(sender, "Must specify a plot to purchase!");
            return;
        }

        if(!(sender instanceof EntityPlayer)){
            messageSender(sender, "Only a player can run this command!");
            return;
        }

        SellablePlots plot = null;
        for (SellablePlots plots: getConfig().getSellables()) {
            if(plots.getPlotName().toLowerCase().equals(args[0].toLowerCase())){
                plot = plots;
                break;
            }
        }

        if (plot == null) {
            messageSender(sender, "Unable to locate plot, did you put the correct name?");
            return;
        }

        if (!plot.getOwnerUID().equals("")) {
            messageSender(sender, "Plot is already owned by someone!");
            return;
        }

        transferProperty(plot.getContainedChunks(), sender);
    }
}

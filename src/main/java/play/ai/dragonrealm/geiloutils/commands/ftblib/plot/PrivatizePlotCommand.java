package play.ai.dragonrealm.geiloutils.commands.ftblib.plot;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.commands.ftblib.FTBIntegrationCommandBase;
import play.ai.dragonrealm.geiloutils.new_configs.models.SellablePlots;

public class PrivatizePlotCommand extends FTBIntegrationCommandBase {

    @Override
    public String getName() {
        return "unpublish";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "unpublish [plot_name]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length != 1){
            messageSender(sender, "Must specify a plot to privatize!");
            return;
        }

        if(!(sender instanceof EntityPlayer)){
            messageSender(sender, "You're not a player!");
            return;
        }

        SellablePlots plot = getConfig().getPlotByName(args[0]);

        EntityPlayer player = (EntityPlayer) sender;
        if(plot != null){
            if(plot.getOwnerUID().equals(player.getCachedUniqueIdString())){

                if(hasTeam(sender)) {
                    transferProperty(plot.getContainedChunks(), sender);
                    messageSender(sender, "Plot is now privatized.");
                } else { messageSender(sender, "You're not on a team!"); }

            } else { messageSender(sender, "You don't own this plot!"); }
        } else { messageSender(sender, "You entered an invalid plot name."); }
    }
}

package play.ai.dragonrealm.geiloutils.commands.ftblib.plot;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.commands.ftblib.FTBIntegrationCommandBase;
import play.ai.dragonrealm.geiloutils.new_configs.FileEnum;
import play.ai.dragonrealm.geiloutils.new_configs.containers.PlayerStatsConfig;
import play.ai.dragonrealm.geiloutils.new_configs.models.SellablePlots;
import play.ai.dragonrealm.geiloutils.utils.MoneyUtils;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

public class ResellPropertyCommand extends FTBIntegrationCommandBase {

    @Override
    public String getName() {
        return "resell";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "resell [plot_name]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length != 1){
            messageSender(sender, "Must specify a plot to resell!");
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
                double price = plot.getPlotPrice() * 0.7;

                if(((PlayerStatsConfig)GeiloUtils.getManager().getConfig(FileEnum.PLAYER_STATS)).getPlayerStatByUUID(player.getCachedUniqueIdString()).isPresent()) {

                    // Transfer the land back to the server account, then pay the player.
                    transferPropertyToServer(plot.getContainedChunks());
                    ((PlayerStatsConfig)GeiloUtils.getManager().getConfig(FileEnum.PLAYER_STATS)).addPlayerMoney(player.getCachedUniqueIdString(), price);
                    messageSender(sender, "You have sold your plot for $" + price);

                } else { messageSender(sender, "Cannot access your current balance!?"); }
            } else { messageSender(sender, "You don't own this plot!"); }
        } else { messageSender(sender, "You entered an invalid plot name."); }
    }
}

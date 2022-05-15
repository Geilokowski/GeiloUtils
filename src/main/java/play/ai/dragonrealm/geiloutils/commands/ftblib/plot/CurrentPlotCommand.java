package play.ai.dragonrealm.geiloutils.commands.ftblib.plot;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.commands.ftblib.FTBIntegrationCommandBase;
import play.ai.dragonrealm.geiloutils.new_configs.FileEnum;
import play.ai.dragonrealm.geiloutils.new_configs.containers.PlayerStatsConfig;
import play.ai.dragonrealm.geiloutils.new_configs.models.ChunkStorage;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;
import play.ai.dragonrealm.geiloutils.new_configs.models.SellablePlots;

import java.util.Optional;

public class CurrentPlotCommand extends FTBIntegrationCommandBase {

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "info";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            ChunkStorage storage = new ChunkStorage(player.chunkCoordX, player.chunkCoordZ, player.dimension);

            SellablePlots plot = getConfig().getPlotByChunkCoord(storage);
            if(plot != null) {
                String owner = "Nobody";
                if(!plot.getOwnerUID().equals("")){
                    Optional<Playerstat> stat = ((PlayerStatsConfig)GeiloUtils.getManager().getConfig(FileEnum.PLAYER_STATS)).getPlayerStatByUUID(plot.getOwnerUID());
                    if(stat.isPresent()) {
                        owner = stat.get().getName();
                    }
                }

                messageSender(sender,
                        "The plot you are standing on is:\n" +
                                "Plot Name: " + plot.getPlotName() + "\n" +
                                "Plot Owner: " + owner + "\n" +
                                "Plot Price: " + plot.getPlotPrice() + "\n" +
                                "Plot Size: " + 256 * plot.getContainedChunks().size());
            }
        }
    }
}

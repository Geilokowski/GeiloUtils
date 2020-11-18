package play.ai.dragonrealm.geiloutils.discord.command.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.discord.command.ICommand;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordUser;

public class TpsCommand implements ICommand {

    @Override
    public String getCommandDesc() {
        return "Returns the tps of a given dim, or all dims.";
    }

    @Override
    public String getCommand() {
        return "tps";
    }

    @Override
    public String getCommandUsage() {
        return "!tps [dimID]";
    }

    @Override
    public boolean executeCommand(ICommandSender sender, DiscordUser discordUser, String[] commandFeatures) {
        if(commandFeatures.length == 1){
            Integer nub = Integer.valueOf(commandFeatures[0]);
            double tickTime = getTickTime(nub);
            String ticks = String.format("%.2f", tickTime);
            sender.sendMessage(new TextComponentString("Dim " + nub + ": " + ticks + "tps."));
        } else {
            Integer[] allDims = DimensionManager.getIDs();
            StringBuilder builder = new StringBuilder();
            for(Integer dim : allDims){
                double tickTime = getTickTime(dim);
                String ticks = String.format("Dim %s: %.2f tps.\n", dim, tickTime);
                builder.append(ticks);
            }
            builder.deleteCharAt(builder.length() - 1);
            sender.sendMessage(new TextComponentString(builder.toString()));
        }
        return false;
    }

    @Override
    public boolean checkPermission() {
        return false;
    }

    @Override
    public boolean doesUserHavePermission(DiscordUser discordUser) {
        return true;
    }

    /**
     * Get the tick time for a single dimension.
     * @param dimension id to find
     * @return mean tick time for said world
     *
     * @see net.minecraftforge.server.command.CommandTps
     */
    public double getTickTime(int dimension) {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        long[] tickTimes = server.worldTickTimes.get(dimension);
        long sum = 0;
        for (int i = 0; i < tickTimes.length; i++){
            sum += tickTimes[i];
        }

        double worldTickTime = (sum / tickTimes.length) * 1.0E-6D;
        return Math.min(1000.0/worldTickTime, 20);
    }
}

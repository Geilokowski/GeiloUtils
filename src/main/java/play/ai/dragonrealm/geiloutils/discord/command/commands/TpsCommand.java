package play.ai.dragonrealm.geiloutils.discord.command.commands;

import net.dv8tion.jda.core.entities.User;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.discord.command.ICommand;

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
    public boolean executeCommand(ICommandSender sender, User discordUser, String[] commandFeatures) {
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
    public boolean doesUserHavePermission(User discordUser) {
        return true;
    }

    public double getTickTime(int dimension) {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
        long[] tickTimes = server.worldTickTimes.get(dimension);
        long sum = 0;
        for (int i = 0; i < tickTimes.length; i++){
            sum += tickTimes[i];
        }
        return (sum / tickTimes.length) * 1.0E-6D;
    }
}

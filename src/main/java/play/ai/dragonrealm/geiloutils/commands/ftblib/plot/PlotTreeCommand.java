package play.ai.dragonrealm.geiloutils.commands.ftblib.plot;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.command.CommandTreeBase;
import net.minecraftforge.server.command.CommandTreeHelp;

public class PlotTreeCommand extends CommandTreeBase {

    public PlotTreeCommand(){
        super.addSubcommand(new BuyPlotCommand());
        super.addSubcommand(new CurrentPlotCommand());
        super.addSubcommand(new ResellPropertyCommand());
        super.addSubcommand(new ComercializePlotCommand());
        super.addSubcommand(new PrivatizePlotCommand());
        // As per documentation, this needs to be kept for last!
        super.addSubcommand(new CommandTreeHelp(this));
    }

    @Override
    public String getName() {
        return "plot";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Use /plot <subcommand>";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}

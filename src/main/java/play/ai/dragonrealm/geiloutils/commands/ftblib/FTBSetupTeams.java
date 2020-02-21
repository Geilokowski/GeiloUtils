package play.ai.dragonrealm.geiloutils.commands.ftblib;


import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.discord.command.BotSender;

import java.util.concurrent.Callable;

public class FTBSetupTeams extends FTBIntegrationCommandBase {
    @Override
    public String getName() {
        return "setupServerTeams";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/setupServerTeams";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String claimTeam = getConfig().getSellingTeamName();
        String holdingTeam = getConfig().getServerHoldingTeamName();
        String storeTeam = getConfig().getServerShopTeamName();
        if(!isTeamInUse(holdingTeam) && !isTeamInUse(claimTeam)){
            FMLCommonHandler.instance().getMinecraftServerInstance().callFromMainThread(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    ICommandManager manager = FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager();
                    manager.executeCommand(BotSender.SILENT_BOT, "team create_server_team " + claimTeam);
                    manager.executeCommand(BotSender.SILENT_BOT, "team create_server_team " + holdingTeam);
                    manager.executeCommand(BotSender.SILENT_BOT, "team create_server_team " + storeTeam);
                    return null;
                }
            });
            messageSender(sender, "Teams successfully created.");
        } else {
            messageSender(sender, "One of the team names is already in use! You need to change the config setting");
        }
    }
}

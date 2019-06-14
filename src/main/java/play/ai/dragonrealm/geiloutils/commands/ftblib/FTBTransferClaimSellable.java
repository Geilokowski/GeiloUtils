package play.ai.dragonrealm.geiloutils.commands.ftblib;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class FTBTransferClaimSellable extends FTBIntegrationCommandBase{

    @Override
    public String getName() {
        return "sellClaim";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/sellClaim [cost]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length != 1){
            messageSender(sender, "You need to specify a price for the land!");
            return;
        }

        try {
            int cost = parseInt(args[0]);
            buildLandClaim(cost);


        } catch (NumberFormatException e) {
            messageSender(sender, "You need to have a number as your parameter!");
            return;
        }
    }
}

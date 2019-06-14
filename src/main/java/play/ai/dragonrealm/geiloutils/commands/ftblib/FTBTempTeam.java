package play.ai.dragonrealm.geiloutils.commands.ftblib;

import com.feed_the_beast.ftblib.lib.EnumTeamStatus;
import com.feed_the_beast.ftblib.lib.data.ForgePlayer;
import com.feed_the_beast.ftblib.lib.data.ForgeTeam;
import com.feed_the_beast.ftblib.lib.data.TeamType;
import com.feed_the_beast.ftblib.lib.data.Universe;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

import java.util.Collection;
import java.util.HashMap;

public class FTBTempTeam extends FTBIntegrationCommandBase {
    @Override
    public String getName() {
        return "sudo";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/sudo [server_team]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(!(sender instanceof EntityPlayer)) {
            messageSender(sender, "This only works for players!");
            return;
        }
        if(args.length != 1){
            messageSender(sender, "You must specify the team to sudo into!");
            return;
        }
        if(!(args[0].equals(getConfig().getSellingTeamName()) || args[0].equals(getConfig().getServerHoldingTeamName()) || args[0].equals("personal"))){
            messageSender(sender, "You did not name a valid server team!");
            return;
        }

        ForgePlayer player = Universe.get().getPlayer(sender);

        if(args[0].equals("personal")){
            if(player.team.type == TeamType.SERVER) {
                removePlayerFromServerTeam(player.team, player);
                messageSender(sender, "You have been moved back to your own team");
                return;
            }
        } else {
            ForgeTeam serverTeam = Universe.get().getTeam(args[0]);
            if(player.hasTeam() && player.team.type != TeamType.SERVER){
                addPlayerToTeamMemory(player.getId().toString(), player.team.toString());
            }
            addPlayerToServer(serverTeam, player);
            messageSender(sender, "You were added to the team.");
        }
    }

    public static void addPlayerToServer(ForgeTeam serverTeam, ForgePlayer player) {
        serverTeam.addMember(player, false);
        serverTeam.setStatus(player, EnumTeamStatus.MOD);
        serverTeam.markDirty();
        player.team = serverTeam;
    }

    public void removePlayerFromServerTeam(ForgeTeam serverTeam, ForgePlayer player) {
        serverTeam.setStatus(player, EnumTeamStatus.NONE);
        serverTeam.removeMember(player);

        ForgeTeam newTeam = getPlayerTeamFromConfig(player.getId().toString());
        if(newTeam == null){
            newTeam = Universe.get().getTeam("");
        }
        player.team = newTeam;
        writeConfig();
    }
}

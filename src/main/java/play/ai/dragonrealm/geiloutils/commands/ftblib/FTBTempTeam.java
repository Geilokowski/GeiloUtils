package play.ai.dragonrealm.geiloutils.commands.ftblib;

import com.feed_the_beast.ftblib.lib.EnumTeamStatus;
import com.feed_the_beast.ftblib.lib.data.ForgePlayer;
import com.feed_the_beast.ftblib.lib.data.ForgeTeam;
import com.feed_the_beast.ftblib.lib.data.TeamType;
import com.feed_the_beast.ftblib.lib.data.Universe;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;

import java.util.Collection;
import java.util.HashMap;

public class FTBTempTeam extends CommandBase {
    @Override
    public String getName() {
        return "ilb";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "brah!";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        Collection<ForgeTeam> teams = Universe.get().getTeams();
        ForgeTeam serverTeam = null;
        for(ForgeTeam team: teams) {
            if(team.type == TeamType.SERVER){
                serverTeam = team;
                break;
            }
        }

        if(serverTeam != null) {
            ForgePlayer player = Universe.get().getPlayer(sender);
            if(player.hasTeam()){
                if(player.team.type == TeamType.SERVER) {
                    removePlayerFromServerTeam(serverTeam, player);
                    return;
                }
                FTBUhelper.addPlayerToTeamMemory(player.getId().toString(), player.team.toString());
                //Set the player to the Server team
                addPlayerToServer(serverTeam, player);

            } else {
                addPlayerToServer(serverTeam, player);
            }
        }
    }

    public static void addPlayerToServer(ForgeTeam serverTeam, ForgePlayer player) {
        serverTeam.addMember(player, false);
        serverTeam.setStatus(player, EnumTeamStatus.MOD);
        serverTeam.markDirty();
        player.team = serverTeam;
    }

    public static void removePlayerFromServerTeam(ForgeTeam serverTeam, ForgePlayer player) {
        serverTeam.setStatus(player, EnumTeamStatus.NONE);
        serverTeam.removeMember(player);

        ForgeTeam newTeam = FTBUhelper.getPlayerTeamFromConfig(player.getId().toString());
        player.team = newTeam;
    }
}

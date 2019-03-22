package play.ai.dragonrealm.geiloutils.discord.listener;

import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.new_configs.containers.PlayerStatsConfig;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.ai.dragonrealm.geiloutils.discord.utils.DiscordUtils;
import play.ai.dragonrealm.geiloutils.discord.utils.UserRanks;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

import java.util.List;
import java.util.Optional;

public class RankChangedListener extends ListenerAdapter {

    @Override
    public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event) {
        Optional<Playerstat> stat = GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).getPlayerstatByDiscordID(event.getUser().getIdLong());
        if(stat.isPresent()) {
            UserRanks rank = DiscordBotMain.getInstance().getHighestRankForUser(event.getUser().getIdLong());
            if(rank != null) {
                DiscordUtils.autoModRankUser(rank, stat.get().getName());
            }
        }
    }

    @Override
    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event) {
        Optional<Role> opRole = DiscordBotMain.getInstance().getSupporterRole();
        Optional<Playerstat> stat = GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).getPlayerstatByDiscordID(event.getUser().getIdLong());
        if(stat.isPresent()) {
            if (opRole.isPresent()) {
                if (event.getRoles().contains(opRole.get())) {
                    UserRanks rank = DiscordBotMain.getInstance().getHighestRankForUser(event.getUser().getIdLong());
                    DiscordUtils.autoModRankUser(rank, stat.get().getName());
                    return;
                }
            }

            // So we're dealing with a staff demotion?
            UserRanks prevRank = getPrevRole(event.getRoles());
            if (prevRank != null) {
                UserRanks rank = DiscordBotMain.getInstance().getHighestRankForUser(event.getUser().getIdLong());
                if (prevRank.getPriority() > rank.getPriority()) {
                    DiscordUtils.autoModRankUser(rank, stat.get().getName());
                }
            }
        }
    }

    private UserRanks getPrevRole(List<Role> rolesOnUser){
        List<UserRanks> userRanks = ConfigAccess.getDiscordConfig().getDiscordRankIntegration();
        if(userRanks.isEmpty()) {
            return null;
        }
        List<String> possibleRoleIDs = DiscordUtils.getRoleIDList(userRanks);
        UserRanks validRankForUser = null;

        for(Role role : rolesOnUser) {
            if(possibleRoleIDs.contains(role.getId())) {
                if(validRankForUser == null) {
                    validRankForUser = DiscordUtils.getUserRanksFromId(userRanks, role.getId());
                } else {
                    UserRanks foundRank = DiscordUtils.getUserRanksFromId(userRanks, role.getId());
                    if(foundRank != null && foundRank.getPriority() > validRankForUser.getPriority()) {
                        validRankForUser = foundRank;
                    }
                }
            }
        }
        return validRankForUser;
    }
}

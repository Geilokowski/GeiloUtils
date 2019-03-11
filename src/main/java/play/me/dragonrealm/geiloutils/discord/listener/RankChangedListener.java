package play.me.dragonrealm.geiloutils.discord.listener;

import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import play.me.dragonrealm.geiloutils.configs.models.PlayerStats;
import play.me.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.me.dragonrealm.geiloutils.discord.utils.DiscordUtils;
import play.me.dragonrealm.geiloutils.discord.utils.UserRanks;
import play.me.dragonrealm.geiloutils.configs.ConfigManager;
import play.me.dragonrealm.geiloutils.utils.PlayerUtils;

import java.util.List;
import java.util.Optional;

public class RankChangedListener extends ListenerAdapter {

    @Override
    public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event) {
        PlayerStats stat = PlayerUtils.getPlayerstatByDiscordID(event.getUser().getIdLong());
        if(stat != null) {
            UserRanks rank = DiscordBotMain.getInstance().getHighestRankForUser(event.getUser().getIdLong());
            if(rank != null) {
                DiscordUtils.autoModRankUser(rank, stat.getName());
            }
        }
    }

    @Override
    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event) {
        Optional<Role> opRole = DiscordBotMain.getInstance().getSupporterRole();
        PlayerStats stat = PlayerUtils.getPlayerstatByDiscordID(event.getUser().getIdLong());
        if(stat != null) {
            if (opRole.isPresent()) {
                if (event.getRoles().contains(opRole.get())) {
                    UserRanks rank = DiscordBotMain.getInstance().getHighestRankForUser(event.getUser().getIdLong());
                    DiscordUtils.autoModRankUser(rank, stat.getName());
                    return;
                }
            }

            // So we're dealing with a staff demotion?
            UserRanks prevRank = getPrevRole(event.getRoles());
            if (prevRank != null) {
                UserRanks rank = DiscordBotMain.getInstance().getHighestRankForUser(event.getUser().getIdLong());
                if (prevRank.getPriority() > rank.getPriority()) {
                    DiscordUtils.autoModRankUser(rank, stat.getName());
                }
            }
        }
    }

    private UserRanks getPrevRole(List<Role> rolesOnUser){
        List<UserRanks> userRanks = ConfigManager.getDiscordConfig().getDiscordRankIntegration();
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

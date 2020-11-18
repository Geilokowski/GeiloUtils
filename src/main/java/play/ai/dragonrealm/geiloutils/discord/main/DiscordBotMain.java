package play.ai.dragonrealm.geiloutils.discord.main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;
import play.ai.dragonrealm.geiloutils.discord.listener.MessageListener;
import play.ai.dragonrealm.geiloutils.discord.listener.RankChangedListener;
import play.ai.dragonrealm.geiloutils.discord.listener.ReadyListener;
import play.ai.dragonrealm.geiloutils.discord.utils.DiscordUtils;
import play.ai.dragonrealm.geiloutils.discord.utils.UserRanks;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DiscordBotMain {

    private static DiscordBotMain INSTANCE = null;

    private String textChannelID;
    private String commandChannelID;
    private JDA jda;
    private boolean botActive = false;
    private String supporterRank;


    private DiscordBotMain() {
        textChannelID = ConfigAccess.getDiscordConfig().getChannelIDRelay();
        commandChannelID = ConfigAccess.getDiscordConfig().getChannelIDCommands();
    }

    public void initializeBot() {
        JDABuilder builder = JDABuilder.createDefault(ConfigAccess.getDiscordConfig().getToken());
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.setMemberCachePolicy(MemberCachePolicy.ALL);
        builder.setAutoReconnect(true);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.addEventListeners(new ReadyListener(), new MessageListener(), new RankChangedListener());
        // builder.addEventListener(new MessageListener());
        // builder.addEventListener(new RankChangedListener());

        try {
            jda = builder.build();
            botActive = true;
        } catch (LoginException e) {
            GeiloUtils.getLogger().error("Login Exception thrown by DiscordBot. Disabled until next reload!");
            botActive = false;
        }
    }


    private Optional<TextChannel> getTextChannel(){
        if(botActive)
            return Optional.ofNullable(jda.getTextChannelById(textChannelID));
        return Optional.empty();
    }

    private TextChannel getCommandChannel() {
        if(botActive)
            return jda.getTextChannelById(commandChannelID);
        return null;
    }

    public String getBotID(){
        if(botActive)
            return jda.getSelfUser().getId();
        return "";
    }

    public void sendMessageDiscord(String message) {
        if(botActive)
            getTextChannel().ifPresent(tx -> tx.sendMessage(message).queue());
    }

    public DiscordUser getUserFromPlayerUUID(String mcUUID) {
        if(botActive) {
            Playerstat stat = PlayerUtils.getPlayerstatByUUID(mcUUID);
            if (stat != null) {
                Long id = stat.getDiscordID();
                if (id != null) {
                    return new DiscordUser(jda.retrieveUserById(id).complete());
                }
            }
        }
        return null;
    }

    public ArrayList<String> getBotsInGuild(boolean underscoreSeparated) {
        ArrayList < String > bots = new ArrayList<>();

        if(getTextChannel().isPresent()) {
            List<Member> memberList = getTextChannel().get().getMembers();

            for (Member member : memberList) {
                if (member.getUser().isBot()) {
                    String name = member.getUser().getName();
                    String botName = underscoreSeparated ? name.replace(" ", "_") : name;
                    bots.add(botName);
                }
            }
        }

        return bots;
    }

    public Optional<Role> getSupporterRole() {
        if(supporterRank != null) {
            return Optional.ofNullable(jda.getRoleById(supporterRank));
        } else {
            String patronGlobal = ConfigAccess.getDiscordConfig().getPatronGlobalRank();
            if(patronGlobal.isEmpty()) {
                return Optional.empty();
            } else {
                if(getTextChannel().isPresent()) {
                    Guild guild = getTextChannel().get().getGuild();
                    supporterRank = patronGlobal;
                    return Optional.ofNullable(guild.getRoleById(patronGlobal));
                }
            }
        }
        return Optional.empty();
    }

    public List<DiscordRole> getRolesOnUser(long userId){
        User user = jda.getUserById(userId);
        Member member = jda.getTextChannelById(textChannelID).getGuild().retrieveMember(user).complete();
        return DiscordRole.toList(member.getRoles());
    }

    public UserRanks getHighestRankForUser(Long discordUserId){
        List<UserRanks> userRanks = ConfigAccess.getDiscordConfig().getDiscordRankIntegration();
        if(userRanks.isEmpty()) {
            return null;
        }

        List<DiscordRole> rolesOnUser = getRolesOnUser(discordUserId);
        List<String> possibleRoleIDs = DiscordUtils.getRoleIDList(userRanks);
        UserRanks validRankForUser = null;

        for(DiscordRole role : rolesOnUser) {
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

    public List<DiscordUser> getUsersByName(String name) {
        if(botActive) {
            return DiscordUser.toList(jda.getUsersByName(name, true));
        } else {
            return new ArrayList<DiscordUser>();
        }
    }

    //This is the lazy way to do it.
    public static DiscordBotMain getInstance() {
        if(INSTANCE == null){
            INSTANCE = new DiscordBotMain();
        }

        return INSTANCE;
    }
}

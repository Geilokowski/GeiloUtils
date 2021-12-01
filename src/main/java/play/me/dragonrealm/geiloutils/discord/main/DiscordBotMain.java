package play.me.dragonrealm.geiloutils.discord.main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import play.me.dragonrealm.geiloutils.GeiloUtils;
import play.me.dragonrealm.geiloutils.configs.models.PlayerStats;
import play.me.dragonrealm.geiloutils.discord.listener.MessageListener;
import play.me.dragonrealm.geiloutils.discord.listener.RankChangedListener;
import play.me.dragonrealm.geiloutils.discord.listener.ReadyListener;
import play.me.dragonrealm.geiloutils.discord.utils.DiscordUtils;
import play.me.dragonrealm.geiloutils.discord.utils.UserRanks;
import play.me.dragonrealm.geiloutils.configs.ConfigAccess;
import play.me.dragonrealm.geiloutils.utils.PlayerUtils;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DiscordBotMain {

    private static DiscordBotMain INSTANCE = null;

    private List<String> textChannelID;
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

        try {
            jda = builder.build();
            botActive = true;
        } catch (LoginException e) {
            GeiloUtils.getLog().warning("Login Exception thrown by DiscordBot. Disabled until next reload!");
            botActive = false;
        }
    }

    public void shutdown() {
        jda.shutdown();
    }

    private Optional<List<TextChannel>> getTextChannel(){
        if(botActive) {
            ArrayList<TextChannel> channels = new ArrayList<>();
            for(String id: textChannelID) {
                channels.add(jda.getTextChannelById(id));
            }
            return Optional.of(channels);
        }

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
        this.sendMessageDiscord(message, null);
    }

    public void sendMessageDiscord(String message, String specificChannel) {
        if(botActive)
            getTextChannel().ifPresent(tx -> tx.forEach(textChannel -> {
                if(specificChannel == null || specificChannel.equals(textChannel.getId()))
                    textChannel.sendMessage(message).queue();
            }));
    }

    public DiscordUser getUserFromPlayerUUID(String mcUUID) {
        if(botActive) {
            PlayerStats stat = PlayerUtils.getPlayerstatByUUID(mcUUID);
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
            List<TextChannel> textChannels = getTextChannel().get();
            for(TextChannel textChannel : textChannels) {
                List<Member> memberList = textChannel.getMembers();
                for (Member member : memberList) {
                    if (member.getUser().isBot()) {
                        String name = member.getUser().getName();
                        String botName = underscoreSeparated ? name.replace(" ", "_") : name;
                        bots.add(botName);
                    }
                }
            }
        }

        return bots;
    }

    public Optional<DiscordRole> getSupporterRole() {
        if(supporterRank != null) {
            return Optional.of(new DiscordRole(jda.getRoleById(supporterRank)));
        } else {
            String patronGlobal = ConfigAccess.getDiscordConfig().getPatronGlobalRank();
            if(patronGlobal.isEmpty()) {
                return Optional.empty();
            } else {
                if(getTextChannel().isPresent()) {
                    List<TextChannel> channels = getTextChannel().get();
                    for(TextChannel channel : channels) {
                        if(channel.getGuild().getRoleById(patronGlobal) != null) {
                            supporterRank = patronGlobal;
                            return Optional.of(new DiscordRole(channel.getGuild().getRoleById(patronGlobal)));
                        }
                    }

                }
            }
        }
        return Optional.empty();
    }

    public List<DiscordRole> getRolesOnUser(long userId){
        User user = jda.getUserById(userId);
        if(getTextChannel().isPresent()) {
            List<TextChannel> channels = getTextChannel().get();
            for (TextChannel channel : channels) {
                if(channel.getGuild().isMember(user)) {
                    return DiscordRole.toList(channel.getGuild().getMember(user).getRoles());
                }
            }
        }
        return null;
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

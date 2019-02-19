package play.ai.dragonrealm.geiloutils.discord.main;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.*;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.config.playerstats.Playerstat;
import play.ai.dragonrealm.geiloutils.discord.listener.MessageListener;
import play.ai.dragonrealm.geiloutils.discord.listener.ReadyListener;
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
    private Long supporterRank;


    private DiscordBotMain() {
        textChannelID = ConfigurationManager.getDiscordConfig().getChannelIDRelay();
        commandChannelID = ConfigurationManager.getDiscordConfig().getChannelIDCommands();
    }

    public void initializeBot() {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(ConfigurationManager.getDiscordConfig().getToken());
        builder.setAutoReconnect(true);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.addEventListener(new ReadyListener());
        builder.addEventListener(new MessageListener());
        botActive = true;
        try {
            jda = builder.build();
        } catch (LoginException e) {
            GeiloUtils.getLogger().error("Login Exception thrown by DiscordBot. Disabled until next reload!");
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

    public User getUserFromPlayerUUID(String mcUUID) {
        if(botActive) {
            Playerstat stat = PlayerUtils.getPlayerstatByUUID(mcUUID);
            if (stat != null) {
                Long id = stat.getDiscordID();
                if (id != null) {
                    return jda.getUserById(id);
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
            String patronGlobal = ConfigurationManager.getDiscordConfig().getPatronGlobalRank();
            Guild guild = jda.getTextChannelById(ConfigurationManager.getDiscordConfig().getChannelIDRelay()).getGuild();
            List<Role> roles = guild.getRolesByName(patronGlobal, true);
            if(!roles.isEmpty()) {
                supporterRank = roles.get(0).getIdLong();
                return Optional.ofNullable(jda.getRoleById(supporterRank));
            }
        }
        return Optional.empty();
    }

    public List<Role> getRolesOnUser(String userName){
        User user = jda.getUsersByName(userName, true).get(0);
        Member member = jda.getTextChannelById(textChannelID).getGuild().getMember(user);
        return member.getRoles();
    }

    //This is the lazy way to do it.
    public static DiscordBotMain getInstance() {
        if(INSTANCE == null){
            INSTANCE = new DiscordBotMain();
        }

        return INSTANCE;
    }
}

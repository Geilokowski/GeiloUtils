package play.ai.dragonrealm.geiloutils.discord.main;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.config.playerstats.Playerstat;
import play.ai.dragonrealm.geiloutils.discord.listener.MessageListener;
import play.ai.dragonrealm.geiloutils.discord.listener.ReadyListener;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

import javax.security.auth.login.LoginException;

public class DiscordBotMain {

    private static DiscordBotMain INSTANCE = null;

    private String textChannelID;
    private String commandChannelID;
    private JDA jda;


    private DiscordBotMain(){
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
        try {
            jda = builder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    private TextChannel getTextChannel() {
        return jda.getTextChannelById(textChannelID);
    }

    private TextChannel getCommandChannel() {
        return jda.getTextChannelById(commandChannelID);
    }

    public String getBotID(){
        return jda.getSelfUser().getId();
    }

    public void sendMessageDiscord(String message) {
        getTextChannel().sendMessage(message).queue();
    }

    public User getUserFromPlayerUUID(String mcUUID) {
        Playerstat stat = PlayerUtils.getPlayerstatByUUID(mcUUID);
        if(stat != null) {
            Long id = stat.getDiscordID();
            if(id != null){
                return jda.getUserById(id);
            }
        }
        return null;
    }



    public static DiscordBotMain getInstance() {
        if(INSTANCE == null){
            INSTANCE = new DiscordBotMain();
        }

        return INSTANCE;
    }
}

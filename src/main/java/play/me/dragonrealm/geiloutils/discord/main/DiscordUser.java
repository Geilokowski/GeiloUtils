package play.me.dragonrealm.geiloutils.discord.main;

import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.RestAction;

public class DiscordUser {

    private User user;

    public DiscordUser(User user) {
        this.user = user;
    }


    public Long getUserIdAsLong() {
        return user.getIdLong();
    }

    public String getName() {
        return this.user.getName();
    }

    public String getDiscriminator() {
        return this.user.getDiscriminator();
    }

    public RestAction<PrivateChannel> openPrivateChannel() {
        return this.user.openPrivateChannel();
    }

    public static List<DiscordUser> toList(List<User> users) {
        ArrayList<DiscordUser> discordUsers = new ArrayList<>();
        for (User user: users) {
            discordUsers.add(new DiscordUser(user));
        }
        return discordUsers;
    }


}
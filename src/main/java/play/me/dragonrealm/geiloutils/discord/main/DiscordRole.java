package play.me.dragonrealm.geiloutils.discord.main;

import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.entities.Role;

public class DiscordRole {

    private Role role;

    public DiscordRole(Role role) {
        this.role = role;
    }

    public String getId() {
        return role.getId();
    }

    public String getName() {
        return this.role.getName();
    }



    public static List<DiscordRole> toList(List<Role> roles) {
        ArrayList<DiscordRole> discordRoles = new ArrayList<>();
        for (Role role: roles) {
            discordRoles.add(new DiscordRole(role));
        }
        return discordRoles;
    }

}

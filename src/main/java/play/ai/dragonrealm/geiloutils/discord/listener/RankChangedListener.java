package play.ai.dragonrealm.geiloutils.discord.listener;

import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.config.playerstats.Playerstat;
import play.ai.dragonrealm.geiloutils.discord.command.BotSender;
import play.ai.dragonrealm.geiloutils.discord.main.GeiloBot;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

public class RankChangedListener extends ListenerAdapter {

    @Override
    public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event) {
        Role patronRole = GeiloBot.getPatronRole();
        Playerstat stat = PlayerUtils.getPlayerstatByDiscordID(event.getMember().getUser().getIdLong());
        if(patronRole != null && stat != null) {
            List<Role> roles = event.getMember().getRoles();
            if(roles.contains(patronRole)) {
                Set<String> teiredRoles = ConfigurationManager.getDiscordConfig().getPatronRanks().keySet();
                for(Role role: event.getRoles()) {
                    if(teiredRoles.contains(role.getName().toLowerCase().replace(" ", "_"))) {

                        List<String> commands = ConfigurationManager.getDiscordConfig().getPatronRanks().get(role);
                        FMLCommonHandler.instance().getMinecraftServerInstance().callFromMainThread(new Callable<Object>() {
                            @Override
                            public Object call() throws Exception {
                                for (String command : commands) {
                                    String entry = String.format(command, stat.getName());
                                    FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().executeCommand(BotSender.SILENT_BOT, entry);
                                }
                                return null;
                            }
                        });
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent event) {
        Role patronRole = GeiloBot.getPatronRole();
        if(patronRole != null) {
            event.getRoles().contains(patronRole);
        }
    }
}

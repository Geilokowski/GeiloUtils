package play.ai.dragonrealm.geiloutils.commands.discord;

import net.dv8tion.jda.core.entities.User;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.discord.main.AuthenticationRegistry;
import play.ai.dragonrealm.geiloutils.discord.main.GeiloBot;

import java.util.List;
import java.util.Random;

public class CommandVerify extends CommandBase {


    @Override
    public String getName() {
        return "verify";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/verify <discordId> [code]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 1){
            String[] names = args[0].split("#");
            if(names.length == 2) {
                List<User> users = GeiloBot.jda.getUsersByName(names[0], true);
                User target = null;
                for (User player : users) {
                    if (player.getDiscriminator().equals(names[1])) {
                        target = player;
                        break;
                    }
                }
                if(target != null) {
                    EntityPlayer entityplayer = getCommandSenderAsPlayer(sender);
                    String uuid = entityplayer.getCachedUniqueIdString();
                    if(AuthenticationRegistry.INSTANCE.hasTriedAuthenticating(uuid)){

                        String directMessage = String.format("To authenticate, please use the following command in Minecraft: /verify %s %s", args[0], AuthenticationRegistry.INSTANCE.getCode(uuid));
                        sendPrivateMessage(target, directMessage);

                    } else {
                        Random rand = new Random();
                        int result = rand.nextInt(9999 - 1) + 1;
                        String code = String.format("%04d", result);
                        String directMessage = String.format("To authenticate, please use the following command in Minecraft: /verify %s %s", args[0], code);
                        sendPrivateMessage(target, directMessage);

                        AuthenticationRegistry.INSTANCE.addAuthAttempt(uuid, code, target);
                    }
                }
            }
        }


    }


    public void sendPrivateMessage(User user, String content)
    {
        // notice that we are not placing a semicolon (;) in the callback this time!
        user.openPrivateChannel().queue( (channel) -> channel.sendMessage(content).queue() );
    }

}

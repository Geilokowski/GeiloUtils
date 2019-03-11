package play.me.dragonrealm.geiloutils.commands.discord;

import net.dv8tion.jda.core.entities.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import play.me.dragonrealm.geiloutils.GeiloUtils;
import play.me.dragonrealm.geiloutils.commands.CommandBase;
import play.me.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.me.dragonrealm.geiloutils.discord.utils.AuthenticationRegistry;
import play.me.dragonrealm.geiloutils.discord.utils.DiscordUtils;
import play.me.dragonrealm.geiloutils.discord.utils.UserRanks;
import play.me.dragonrealm.geiloutils.utils.PlayerUtils;

import java.util.List;
import java.util.Random;

public class VerifyCommand extends CommandBase {

    public VerifyCommand() {
        super("verify");
    }

    @Override
    public String getCmdDesc() {
        return "Allows a user to attach their discord account to their MC account, for this server.";
    }

    @Override
    public String getCmdUsage() {
        return "/verify discordId#CODE [verification code]";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        Player entityplayer = sender instanceof Player ? (Player) sender : null;
        String uuid = entityplayer != null ? entityplayer.getUniqueId().toString() : "";
        if(uuid.equals("")){
            return false;
        }
        if(args.length == 1){
            String[] names = args[0].split("#");
            if(names.length == 2) {
                List<User> users = DiscordBotMain.getInstance().getUsersByName(names[0]);
                User target = null;
                for (User player : users) {
                    if (player.getDiscriminator().equals(names[1])) {
                        target = player;
                        break;
                    }
                }
                if(target != null) {
                    if(AuthenticationRegistry.INSTANCE.hasTriedAuthenticating(uuid)){

                        sendMessageToPlayer(sender, "[DiscordVerify]: Your account is already linked!");

                    } else {
                        Random rand = new Random();
                        int result = rand.nextInt(9999 - 1) + 1;
                        String code = String.format("%04d", result);
                        String directMessage = String.format("To authenticate, please use the following command in Minecraft: /verify %s %s", args[0], code);
                        sendPrivateMessage(target, directMessage);

                        AuthenticationRegistry.INSTANCE.addAuthAttempt(uuid, code, target);
                        sendMessageToPlayer(sender, "[DiscordVerify]: A DM has been sent to you from the server chat bot with additional details.");
                    }
                    return true;
                }
            }
        } else if(args.length == 2) {
            AuthenticationRegistry.AuthenticStatus status = AuthenticationRegistry.INSTANCE.verifyUserAndAdd(uuid, args[1]);
            switch (status) {
                case INCORRECT_CODE:
                    sendMessageToPlayer(sender, "[DiscordVerify]: Verification unsuccessful, incorrect pin code.");
                    break;
                case PLAYER_NOT_FOUND:
                    sendMessageToPlayer(sender, "[DiscordVerify]: Verification unsuccessful, player not found!");
                    break;
                case COMPLETED:
                    sendMessageToPlayer(sender,  "[DiscordVerify]: Account successfully linked!");
                    UserRanks rank = DiscordBotMain.getInstance().getHighestRankForUser(PlayerUtils.getPlayerstatByUUID(uuid).getDiscordID());
                    if(rank != null) {
                        DiscordUtils.autoModRankUser(rank, PlayerUtils.getPlayerstatByUUID(uuid).getName());
                    }
                    GeiloUtils.getLog().info(String.format("The user: %s has authed with discord.", PlayerUtils.getPlayerstatByUUID(uuid).getName()));
                    break;
            }
            return true;
        }
        return false;
    }

    public void sendPrivateMessage(User user, String content)
    {
        // notice that we are not placing a semicolon (;) in the callback this time!
        user.openPrivateChannel().queue( (channel) -> channel.sendMessage(content).queue() );
    }

    public void sendMessageToPlayer(CommandSender sender, String message){
        sender.sendMessage(message);
    }

}

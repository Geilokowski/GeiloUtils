package play.ai.dragonrealm.geiloutils.commands.discord;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordBotMain;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordUser;
import play.ai.dragonrealm.geiloutils.discord.utils.AuthenticationRegistry;
import play.ai.dragonrealm.geiloutils.discord.utils.DiscordUtils;
import play.ai.dragonrealm.geiloutils.discord.utils.UserRanks;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

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
                List<DiscordUser> users = DiscordBotMain.getInstance().getUsersByName(names[0]);
                DiscordUser target = null;
                for (DiscordUser player : users) {
                    if (player.getDiscriminator().equals(names[1])) {
                        target = player;
                        break;
                    }
                }
                if(target != null) {
                    EntityPlayer entityplayer = getCommandSenderAsPlayer(sender);
                    String uuid = entityplayer.getCachedUniqueIdString();
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
                }
            }
        } else if(args.length == 2) {
            EntityPlayer entityplayer = getCommandSenderAsPlayer(sender);
            String uuid = entityplayer.getCachedUniqueIdString();
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
                    notifyCommandListener(sender, this, "The user: %s has authed with discord.", PlayerUtils.getPlayerstatByUUID(uuid).getName());
                    break;
            }
        }

    }


    public void sendPrivateMessage(DiscordUser user, String content)
    {
        // notice that we are not placing a semicolon (;) in the callback this time!
        user.openPrivateChannel().queue( (channel) -> channel.sendMessage(content).queue() );
    }

    public void sendMessageToPlayer(ICommandSender sender, String message){
        ITextComponent msg = new TextComponentString(message);
        sender.sendMessage(msg);
    }

    public boolean checkPermission(MinecraftServer server, ICommandSender sender)
    {
        return true;
    }

}

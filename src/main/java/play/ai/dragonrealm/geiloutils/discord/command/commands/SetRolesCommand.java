package play.ai.dragonrealm.geiloutils.discord.command.commands;


import play.ai.dragonrealm.geiloutils.discord.command.ICommand;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordBotMain;


import java.util.List;

public class SetRolesCommand /*implements ICommand*/ {

    /*@Override
    public String getCommandDesc() {
        return "Set the role of any models user.";
    }

    @Override
    public String getCommand() {
        return "setRole";
    }

    @Override
    public String getCommandUsage() {
        return "!setRole <models#user> <ADMIN|MOD|UNUSED|COMMONER>";
    }

    @Override
    public boolean executeCommand(ICommandSender sender, User discordUser, String[] commandFeatures) {
        if(commandFeatures.length == 2){
            String[] splited = commandFeatures[0].split("#");
            if(splited.length == 2) {
                List<User> users = DiscordBotMain.getInstance().getUsersByName(splited[0]);
                User actualUser = null;
                for(User user: users) {
                    if(user.getDiscriminator().equals(splited[1])){
                        actualUser = user;
                        break;
                    }
                }

                if(actualUser != null) {

                    try {
                        DiscordRank rank = DiscordRank.valueOf(commandFeatures[1].toUpperCase());
                        DiscordUtils.setRankForDiscordUser(actualUser.getIdLong(), rank);
                        sender.sendMessage(new TextComponentString("Rank updated to: " + commandFeatures[1].toUpperCase() + " for user: " + actualUser.getName()));
                    } catch (IllegalArgumentException e) {
                        sender.sendMessage(new TextComponentString("Unable to convert: " + commandFeatures[1].toUpperCase() + " to a valid rank"));
                    }
                } else {
                    sender.sendMessage(new TextComponentString("Unable to find models user!"));
                }
            } else {
                sender.sendMessage(new TextComponentString("Discord name must include discriminator!"));
            }
        } else {
            sender.sendMessage(new TextComponentString("Not enough parameters!"));
        }
        return false;
    }

    @Override
    public boolean checkPermission() {
        return true;
    }

    @Override
    public boolean doesUserHavePermission(User discordUser) {
        DiscordRank rank = DiscordUtils.getAuthForUser(discordUser);
        return rank == DiscordRank.ADMIN;
    }*/
}

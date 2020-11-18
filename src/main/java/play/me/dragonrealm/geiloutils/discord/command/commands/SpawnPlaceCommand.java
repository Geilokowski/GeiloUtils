package play.me.dragonrealm.geiloutils.discord.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import play.me.dragonrealm.geiloutils.discord.command.ICommand;
import play.me.dragonrealm.geiloutils.discord.main.DiscordUser;

public class SpawnPlaceCommand implements ICommand {
    @Override
    public String getCommandDesc() {
        return "Moves the player back to spawn. Doesn't work if player is offline.";
    }

    @Override
    public String getCommand() {
        return "rspawn";
    }

    @Override
    public String getCommandUsage() {
        return "!rspawn <player_name>";
    }

    @Override
    public boolean executeCommand(CommandSender sender, DiscordUser discordUser, String[] commandFeatures) {
        if (commandFeatures.length > 0) {
            Player player = sender.getServer().getPlayer(commandFeatures[0]);

            if (player != null) {
                player.teleport(player.getWorld().getSpawnLocation());
                sender.sendMessage("Player moved to world spawn!");
            } else {
                sender.sendMessage("Unable to find player: " + commandFeatures[0]);
            }

        } else {
            sender.sendMessage("Args not formatted propertly!");
        }
        return false;
    }

    @Override
    public boolean checkPermission(){
        return true;
    }

    @Override
    public boolean doesUserHavePermission(DiscordUser discordUser) {
        /*DiscordRank rank = DiscordUtils.getAuthForUser(discordUser);
        return rank.getLevel() >= DiscordRank.MOD.getLevel();*/
        return true;
    }
}

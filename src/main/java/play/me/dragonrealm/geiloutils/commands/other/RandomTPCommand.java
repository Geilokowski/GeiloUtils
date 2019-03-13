package play.me.dragonrealm.geiloutils.commands.other;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import play.me.dragonrealm.geiloutils.commands.CommandBase;
import play.me.dragonrealm.geiloutils.configs.ConfigManager;

import java.util.Random;

public class RandomTPCommand  extends CommandBase {

    Random r = new Random();
    public static Material[] dangerBlockArray = { Material.LAVA, Material.WATER, Material.AIR };

    public RandomTPCommand() {
        super("rtp");
    }

    @Override
    public String getCmdDesc() {
        return "Teleports you to a random location";
    }

    @Override
    public String getCmdUsage() {
        return "/rtp";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if ((sender instanceof Player))
        {
            Player player = (Player)sender;
            int newX = this.r.nextInt(ConfigManager.getRTPConfig().getRadius() * 2) - ConfigManager.getRTPConfig().getRadius();
            int newY = ConfigManager.getRTPConfig().getMinY();
            int newZ = this.r.nextInt(ConfigManager.getRTPConfig().getRadius() * 2) - ConfigManager.getRTPConfig().getRadius();
            int maxTries = ConfigManager.getRTPConfig().getMaxTries();
            while (!isSafe(player, newX, newY, newZ) && (maxTries == -1 || maxTries > 0))
            {
                newY++;
                if (newY > 120)
                {
                    newX = this.r.nextInt(ConfigManager.getRTPConfig().getRadius() * 2) - ConfigManager.getRTPConfig().getRadius();
                    newY = ConfigManager.getRTPConfig().getMinY();
                    newZ = this.r.nextInt(ConfigManager.getRTPConfig().getRadius() * 2) - ConfigManager.getRTPConfig().getRadius();
                }
                if(maxTries > 0){
                    maxTries--;
                }
                if(maxTries == 0) {

                    player.sendMessage("[GeiloRTP] Could not find a safe space to RTP, please try again!");
                    return true;
                }
            }
            player.teleport(new Location(player.getWorld(), newX, newY, newZ));

            player.sendMessage("[GeiloRTP] You were teleported to X: " + newX + " Y: " + newY + " Z: " + newZ);
            return true;
        }
        return false;
    }



    public boolean isSafe(Player player, int newX, int newY, int newZ)
    {
        if (isEmpty(player.getWorld(), newX, newY, newZ) && !isDangerBlock(player.getWorld(), newX, newY - 1, newZ)) {
            return true;
        }
        return false;
    }

    public boolean isEmpty(World world, int newX, int newY, int newZ)
    {
        if (
                (world.getBlockAt(newX, newY, newZ).isEmpty()) &&
                (world.getBlockAt(newX, newY + 1, newZ).isEmpty()) &&
                (world.getBlockAt(newX + 1, newY , newZ).isEmpty()) &&
                (world.getBlockAt(newX - 1, newY , newZ).isEmpty()) &&
                (world.getBlockAt(newX, newY, newZ + 1).isEmpty()) &&
                (world.getBlockAt(newX, newY, newZ + 1).isEmpty())
                ) {
            return true;
        }
        return false;
    }

    public boolean isDangerBlock(World world, int newX, int newY, int newZ)
    {
        for (Material block : dangerBlockArray) {

            if (block.equals(world.getBlockAt(newX, newY, newZ).getType())) {
                return true;
            }
        }
        return false;
    }
}

package play.ai.dragonrealm.geiloutils.discord.command.commands;

import net.dv8tion.jda.core.entities.User;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.discord.command.GeiloPorter;
import play.ai.dragonrealm.geiloutils.discord.command.ICommand;
import play.ai.dragonrealm.geiloutils.discord.utils.DiscordRank;
import play.ai.dragonrealm.geiloutils.discord.utils.DiscordUtils;

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
    public boolean executeCommand(ICommandSender sender, User discordUser, String[] commandFeatures) {
        if(commandFeatures.length > 0) {
            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            WorldServer world = server.getWorld(0);

            BlockPos spawnPos = world.getSpawnPoint();
            EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(commandFeatures[0]);

            if(player != null) {
                if(player.dimension != 0) {
                    int dimID = player.dimension;
                    server.getPlayerList().transferPlayerToDimension(player, 0, new GeiloPorter(world));


                    if (dimID == 1 && player.isEntityAlive()) {
                        world.spawnEntity(player);
                        world.updateEntityWithOptionalForce(player, false);
                    }
                }

                player.setPositionAndUpdate(spawnPos.getX() + 0.5d, spawnPos.getY() + 0.5d, spawnPos.getZ() + 0.5d);
            }
            return true;
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
        return rank.getLevel() >= DiscordRank.MOD.getLevel();
    }
}

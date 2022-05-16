package play.ai.dragonrealm.geiloutils.discord.command.commands;


import com.mojang.authlib.GameProfile;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import play.ai.dragonrealm.geiloutils.discord.command.GeiloPorter;
import play.ai.dragonrealm.geiloutils.discord.command.ICommand;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordUser;

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
    public boolean executeCommand(ICommandSource sender, DiscordUser discordUser, String[] commandFeatures) {
        /*if(commandFeatures.length > 0) {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            ServerWorld world = server.overworld();

            BlockPos spawnPos = world.getSharedSpawnPos();
            ServerPlayerEntity player = server.getPlayerList().getPlayerByName(commandFeatures[0]);

            if(player != null) {
                if(!player.getLevel().dimension().getRegistryName().getPath().equals("overworld")) {
                    int dimID = player.dimension;
                    server.getPlayerList().transferPlayerToDimension(player, 0, new GeiloPorter(world));


                    if (dimID == 1 && player.isEntityAlive()) {
                        world.spawnEntity(player);
                        world.updateEntityWithOptionalForce(player, false);
                    }
                }

                player.setPositionAndUpdate(spawnPos.getX() + 0.5d, spawnPos.getY() + 0.5d, spawnPos.getZ() + 0.5d);

            } else {
                // This is not my own, I took it from ShadowFactsDev, released here
                // https://github.com/shadowfacts/DiscordChat/blob/rewrite/1.12.2/src/main/java/net/shadowfacts/discordchat/one_twelve_two/OneTwelveTwoAdapter.java
                // GNU LESSER GENERAL PUBLIC LICENSE
                GameProfile profile = server.getPlayerProfileCache().getGameProfileForUsername(commandFeatures[0]);

                if (profile != null && profile.isComplete()) {
                    FakePlayer fakePlayer = FakePlayerFactory.get(world, profile);
                    IPlayerFileData saveHandler = world.getSaveHandler().getPlayerNBTManager();
                    NBTTagCompound tag = saveHandler.readPlayerData(fakePlayer);

                    if (tag == null) {
                        sender.sendMessage(new TextComponentString("Unable to find player: " + commandFeatures[0]));
                        return false;
                    }

                    fakePlayer.dimension = 0;
                    fakePlayer.posX = spawnPos.getX() + 0.5d;
                    fakePlayer.posY = spawnPos.getY();
                    fakePlayer.posZ = spawnPos.getZ() + 0.5d;

                    saveHandler.writePlayerData(fakePlayer);
                } else {
                    sender.sendMessage(new TextComponentString("Unable to find player: " + commandFeatures[0]));
                    return false;
                }
            }
        }
        sender.sendMessage(new TextComponentString("Player moved to world spawn!"));*/
        return false;
    }

    @Override
    public boolean checkPermission() {
        return true;
    }

    @Override
    public boolean doesUserHavePermission(DiscordUser discordUser) {
        /*DiscordRank rank = DiscordUtils.getAuthForUser(discordUser);
        return rank.getLevel() >= DiscordRank.MOD.getLevel();*/
        return true;
    }
}

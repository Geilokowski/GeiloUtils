package play.ai.dragonrealm.geiloutils.commands.admin;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.exceptions.NoSuchDimensionException;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.new_configs.models.Rank;
import play.ai.dragonrealm.geiloutils.utils.PermissionUtils;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

public class CommandGeiloPort extends CommandBase {
    @Override
    public String getName() {
        return "geiloport";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "teleports you";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 4){
            if(sender instanceof EntityPlayer){
                EntityPlayer player = (EntityPlayer) sender;
                Rank rank = PermissionUtils.getRankFromPlayer(player);
                if(PermissionUtils.doesRankHavePermission(rank, ConfigAccess.getMultiworldConfig().getGeiloportPerm())) { // TODO: This warning is true, if the user doesn't have a rank it throws a NullPinterException
                    try {
                        int x = Integer.parseInt(args[0]);
                        int y = Integer.parseInt(args[1]);
                        int z = Integer.parseInt(args[2]);

                        int dimension = Integer.parseInt(args[3]);

                        PlayerUtils.teleportToDimension(player, dimension, x, y, z);
                        ITextComponent msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "You got teleported to x: " + x + " y: " + y + " z:" + z + " [Dimension:" + dimension + "]");
                        sender.sendMessage(msg);
                    } catch (NoSuchDimensionException e) {
                        ITextComponent msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "There is no dimension with this ID. Try using /geiloworld list");
                        sender.sendMessage(msg);
                    } catch (NumberFormatException e) {
                        ITextComponent msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "You can only use numbers as an argument");
                        sender.sendMessage(msg);
                    }
                }else{
                    ITextComponent msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "You do not have the permission to execute this command");
                    sender.sendMessage(msg);
                }
            }
        }else{
            ITextComponent msg = new TextComponentString(ConfigAccess.getGeneralConfig().getCommandPrefix() + "You can only use numbers as an argument");
            sender.sendMessage(msg);
        }
    }
}

package play.ai.dragonrealm.geiloutils.commands.geilokill;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeiloKill extends CommandBase {
    @Override
    public String getName() {
        return "geilokill";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Kills a player even if its name is Miradae";
    }

    public int getRequiredPermissionLevel()
    {
        return 3;
    }

    public List<String> getAliases()
    {
        ArrayList<String> list = new ArrayList<>();
        list.add("disintegrate"); // Mira helped test this command, i figured he'd get some naming choice.
        return list;
    }

    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        return args.length >= 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        //Has been tested by Miradae x dmf444 and works fine.
        if (args.length == 0)
        {
            EntityPlayer entityplayer = getCommandSenderAsPlayer(sender);
            entityplayer.setHealth(0);
            entityplayer.onDeath(DamageSource.OUT_OF_WORLD);
            entityplayer.setDead();
            notifyCommandListener(sender, this, "commands.kill.successful", new Object[] {entityplayer.getDisplayName()});
        }
        else
        {
            Entity entity = getEntity(server, sender, args[0]);
            if(entity instanceof EntityPlayer){
                ((EntityPlayer) entity).setHealth(0);
                ((EntityPlayer) entity).onDeath(DamageSource.OUT_OF_WORLD);
                entity.setDead();
                notifyCommandListener(sender, this, "GeiloKill: %s, completed", new Object[] {entity.getDisplayName()});
            } else {
                notifyCommandListener(sender, this, "GeiloKill: not completed, target is not a player", new Object[] {});
            }

        }
    }
}

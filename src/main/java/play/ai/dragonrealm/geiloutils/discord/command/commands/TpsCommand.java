package play.ai.dragonrealm.geiloutils.discord.command.commands;

import net.minecraft.command.ICommandSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import play.ai.dragonrealm.geiloutils.discord.command.ICommand;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordUser;

public class TpsCommand implements ICommand {

    @Override
    public String getCommandDesc() {
        return "Returns the tps of a given dim, or all dims.";
    }

    @Override
    public String getCommand() {
        return "tps";
    }

    @Override
    public String getCommandUsage() {
        return "!tps [dimID]";
    }

    @Override
    public boolean executeCommand(ICommandSource sender, DiscordUser discordUser, String[] commandFeatures) {
        if(commandFeatures.length == 1){
            RegistryKey<World> registrykey = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(commandFeatures[0]));
            ServerWorld serverworld = ServerLifecycleHooks.getCurrentServer().getLevel(registrykey);
            if(serverworld != null){
                double tickTime = getTickTime(serverworld);
                String ticks = String.format("%.2f", tickTime);
                sender.sendMessage(new StringTextComponent("Dim " + commandFeatures[0] + ": " + ticks + "tps."), Util.NIL_UUID);
            } else {
                sender.sendMessage(new StringTextComponent("Dim does not exist!"), Util.NIL_UUID);
            }

        } else {

            StringBuilder builder = new StringBuilder();
            for (ServerWorld dim : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {
                double tickTime = getTickTime(dim);
                String ticks = String.format("Dim %s: %.2f tps.\n", dim, tickTime);
                builder.append(ticks);
            }

            builder.deleteCharAt(builder.length() - 1);
            sender.sendMessage(new StringTextComponent(builder.toString()), Util.NIL_UUID);
        }
        return false;
    }

    @Override
    public boolean checkPermission() {
        return false;
    }

    @Override
    public boolean doesUserHavePermission(DiscordUser discordUser) {
        return true;
    }

    /**
     * Get the tick time for a single dimension.
     * @param dim id to find
     * @return mean tick time for said world
     *
     * @see net.minecraftforge.server.command.CommandTps
     */
    public double getTickTime(ServerWorld dim) {
        long[] times = ServerLifecycleHooks.getCurrentServer().getTickTime(dim.dimension());

        if (times == null) // Null means the world is unloaded. Not invalid. That's taken car of by DimensionArgument itself.
            times = UNLOADED;

        final Registry<DimensionType> reg = ServerLifecycleHooks.getCurrentServer().registryAccess().registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
        double worldTickTime = mean(times) * 1.0E-6D;
        double worldTPS = Math.min(1000.0 / worldTickTime, 20);
        return worldTPS;
    }

    private static long mean(long[] values)
    {
        long sum = 0L;
        for (long v : values)
            sum += v;
        return sum / values.length;
    }

    private static final long[] UNLOADED = new long[] {0};
}

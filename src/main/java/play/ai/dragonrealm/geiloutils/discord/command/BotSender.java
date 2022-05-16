package play.ai.dragonrealm.geiloutils.discord.command;

import java.util.UUID;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordBotMain;


public class BotSender implements ICommandSource {

    public static BotSender INSTANCE = new BotSender();
    public static BotSender BLOCK_INSTANCE = new BotSender(true);
    public static BotSender SILENT_BOT = new BotSender(false, true);



    private boolean useBlockResp = false;
    private boolean isSilent = false;

    public BotSender(boolean useBlockResp) {
        this.useBlockResp = useBlockResp;
    }

    public BotSender(boolean useBlockResp, boolean isSilent){
        this.useBlockResp = useBlockResp;
        this.isSilent = isSilent;
    }

    public BotSender(){}

    public CommandSource getCommandSource() {
        return new CommandSource(this, Vector3d.ZERO, Vector2f.ZERO, this.getEntityWorld(), 4, "Server", new StringTextComponent("Server"), this.getServer(), (Entity)null);
    }

    public String getName() {
        return "[GeiloBot]";
    }


    public ServerWorld getEntityWorld() {
        return getServer().overworld();
    }


    public MinecraftServer getServer() {
        return ServerLifecycleHooks.getCurrentServer();
    }

    public void sendMessage(ITextComponent component){
        if(!isSilent) {
            if (useBlockResp) {
                DiscordBotMain.getInstance().sendMessageDiscord("```" + component.getString() + "```");
                return;
            }
            DiscordBotMain.getInstance().sendMessageDiscord(component.getContents());
        }
    }

    @Override
    public void sendMessage(ITextComponent component, UUID uuid) {
        this.sendMessage(component);
    }

    @Override
    public boolean acceptsSuccess() {
        return false;
    }

    @Override
    public boolean acceptsFailure() {
        return false;
    }

    @Override
    public boolean shouldInformAdmins() {
        return false;
    }
}

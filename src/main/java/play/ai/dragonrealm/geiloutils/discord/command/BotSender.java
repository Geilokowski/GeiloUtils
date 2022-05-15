package play.ai.dragonrealm.geiloutils.discord.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import play.ai.dragonrealm.geiloutils.discord.main.DiscordBotMain;

import javax.annotation.Nullable;

public class BotSender implements ICommandSender {

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


    public String getName() {
        return "[GeiloBot]";
    }


    @Override
    public World getEntityWorld() {
        return getServer().getWorld(0);
    }

    @Nullable
    @Override
    public MinecraftServer getServer() {
        return FMLCommonHandler.instance().getMinecraftServerInstance();
    }

    public void sendMessage(ITextComponent component){
        if(!isSilent) {
            if (useBlockResp) {
                DiscordBotMain.getInstance().sendMessageDiscord("```" + component.getString() + "```");
                return;
            }
            DiscordBotMain.getInstance().sendMessageDiscord(component.getUnformattedText());
        }
    }
}

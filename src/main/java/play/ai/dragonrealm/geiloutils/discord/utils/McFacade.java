package play.ai.dragonrealm.geiloutils.discord.utils;

import java.util.List;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class McFacade {

    public static List<ServerPlayerEntity> getOnlinePlayers() {
        return getServerPlayerList().getPlayers();
    }

    public static PlayerList getServerPlayerList() {
        return ServerLifecycleHooks.getCurrentServer().getPlayerList();
    }

    public static void messageAllPlayers(ITextComponent formattedMessage) {
        getServerPlayerList().broadcastMessage(formattedMessage, ChatType.CHAT, Util.NIL_UUID);
    }

}

package play.ai.dragonrealm.geiloutils.gui;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.server.MinecraftServer;

public class NetHandlerGUI extends NetHandlerPlayServer {
    public NetHandlerGUI(MinecraftServer server, NetworkManager networkManagerIn, EntityPlayerMP playerIn) {
        super(server, networkManagerIn, playerIn);
    }

    @Override
    public void processTryUseItem(CPacketPlayerTryUseItem event){
        System.out.println("Hallo Welt");
    }

    @Override
    public void processChatMessage(CPacketChatMessage packetIn){
        System.out.println("Hallo Welt");
    }
}

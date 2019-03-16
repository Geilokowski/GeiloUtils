package play.ai.dragonrealm.geiloutils.events;


import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.server.FMLServerHandler;
import play.ai.dragonrealm.geiloutils.GeiloUtils;

public class RightClickSignEvent {

    @SubscribeEvent
    public void onRightClickEvent(PlayerInteractEvent.RightClickBlock event){
        Block eventBlock = event.getWorld().getBlockState(event.getPos()).getBlock();
        if(eventBlock == Blocks.WALL_SIGN){
            TileEntitySign sign = (TileEntitySign) event.getWorld().getTileEntity(event.getPos());
            if(sign != null && sign.signText[0].getUnformattedText().equals("[STORE]")){
                //GeiloUtils.getLogger().fatal("FOUND A STORE SIGN!");
                if(!event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).isEmpty()){
                    event.getEntityPlayer().displayGUIChest(new TileEntityChest());
                    ItemStack stack = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);

                    sign.signText[0] = new TextComponentString("§4[STORE]§r");
                    sign.signText[1] = new TextComponentString(stack.getDisplayName());
                    sign.signText[2] = new TextComponentString("C: 999 $ || Q: 999");
                    sign.signText[3] = new TextComponentString("§a[BUY]");


                    IBlockState state = event.getWorld().getBlockState(event.getPos());
                    sign.markDirty();
                    event.getWorld().notifyBlockUpdate(event.getPos(), state, state, 3);
                }
            }
        }
    }
}

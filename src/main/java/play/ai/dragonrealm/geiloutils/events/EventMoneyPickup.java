package play.ai.dragonrealm.geiloutils.events;

import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import play.ai.dragonrealm.geiloutils.config.ConfigurationManager;
import play.ai.dragonrealm.geiloutils.utils.MoneyUtils;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

public class EventMoneyPickup {

    @SubscribeEvent
    public void onPlayerPickupEvent(ItemTossEvent event){
        if(ConfigurationManager.getEconomyConfig().isEnabled()){
            if(MoneyUtils.moneyValues.containsKey(event.getEntityItem().getItem().getUnlocalizedName())){
                Double value = MoneyUtils.moneyValues.get(event.getEntityItem().getItem().getUnlocalizedName()) * event.getEntityItem().getItem().getCount();
                PlayerUtils.addPlayerMoney(event.getPlayer(), value);
                event.getEntityItem().setDead();
                event.setCanceled(true);
            }
        }

    }

}

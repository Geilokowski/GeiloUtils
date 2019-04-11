package play.ai.dragonrealm.geiloutils.commands.economy;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.new_configs.containers.PlayerStatsConfig;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;
import play.ai.dragonrealm.geiloutils.utils.MathUtils;
import play.ai.dragonrealm.geiloutils.utils.PlayerUtils;

public class CommandWithdraw extends CmdBase {

    String usage = "/withdraw <amount>";
    @Override
    public String getName() {
        return "withdraw";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/withdraw <amount>";
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List < String > getAliases() {
        ArrayList < String > al = new ArrayList < String > ();
        al.add("wd");
        al.add("Wd");
        al.add("Withdraw");
        return al;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if ((args.length == 1) && ((sender instanceof EntityPlayer))) {
            EntityPlayer player = (EntityPlayer) sender;
            double money = Double.parseDouble(args[0]);
            if (money > getPlayerBalance(player)) {
                sendMsg(player, "You dont have enought money. Your balance is: %s$", getPlayerBalance(player));
                return;
            }

            removePlayerBalance(player, money);

            Playerstat ps = PlayerUtils.getPlayerstatByUUID(player.getCachedUniqueIdString());
            ps.setMoney(MathUtils.truncateDecimal(ps.getMoney(), 2).doubleValue());

            GeiloUtils.getManager().getConfig(PlayerStatsConfig.class).updatePlayerstat(ps);

            while (money > 0) {

                if (money >= 100.0) {
                    PlayerUtils.addItemByName(player, "modcurrency:banknote", 1, 5);
                    money -= 100.0;
                } else if (money >= 50.0) {
                    PlayerUtils.addItemByName(player, "modcurrency:banknote", 1, 4);
                    money -= 50.0;
                } else if (money >= 20.0) {
                    PlayerUtils.addItemByName(player, "modcurrency:banknote", 1, 3);
                    money -= 20.0;
                } else if (money >= 10.0) {
                    PlayerUtils.addItemByName(player, "modcurrency:banknote", 1, 2);
                    money -= 10.0;
                } else if (money >= 5.0) {
                    PlayerUtils.addItemByName(player, "modcurrency:banknote", 1, 1);
                    money -= 5.0;
                } else if (money >= 2.0) {
                    PlayerUtils.addItemByName(player, "modcurrency:coin", 1, 5);
                    money -= 2.0;
                } else if (money >= 1.0) {
                    PlayerUtils.addItemByName(player, "modcurrency:banknote", 1, 0);
                    money -= 1.0;
                } else if (money >= 0.25) {
                    PlayerUtils.addItemByName(player, "modcurrency:coin", 1, 3);
                    money -= 0.25;
                } else if (money >= 0.1) {
                    PlayerUtils.addItemByName(player, "modcurrency:coin", 1, 2);
                    money -= 0.1;
                } else if (money >= 0.05) {
                    PlayerUtils.addItemByName(player, "modcurrency:coin", 1, 1);
                    money -= 0.05;
                } else if (money >= 0.01) {
                    PlayerUtils.addItemByName(player, "modcurrency:coin", 1, 0);
                    money -= 0.01;
                } else if (money > 0.00 && money < 0.01) {
                    PlayerUtils.addItemByName(player, "modcurrency:coin", 1, 0);
                    money -= 0.01;
                }
            }


            ITextComponent msg = new TextComponentString("[GeiloEconomy] You just withdrew " + args[0] + "$. Your new balance is: " + getPlayerBalance(player) + "$.");
            player.sendMessage(msg);
        }
    }

}
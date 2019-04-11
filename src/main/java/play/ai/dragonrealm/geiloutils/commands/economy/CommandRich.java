package play.ai.dragonrealm.geiloutils.commands.economy;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import play.ai.dragonrealm.geiloutils.commands.CmdBase;
import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;
import play.ai.dragonrealm.geiloutils.new_configs.models.Playerstat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandRich extends CmdBase {
    @Override
    public String getName() {
        return "rich";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "Returns the richest player on the server";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        List<Double> moneyList = new ArrayList<>();
        for(Playerstat ps : ConfigAccess.getPlayerStatsConfig().getPlayerstats()){
            moneyList.add(ps.getMoney());
        }

        double max = Collections.max(moneyList);

        List<String> usernameList = new ArrayList<>();
        for(Playerstat ps : ConfigAccess.getPlayerStatsConfig().getPlayerstats()){
            if(ps.getMoney() == max){
                usernameList.add(ps.getName());
            }
        }

        String usernames = "";
        for(String username : usernameList){
            usernames = usernames + ", " + username;
        }

        usernames = usernames.substring(2);

        if(usernameList.size() > 1){
            sendMsg(sender, "The richest players are: " + usernames + ". They all have a balance of " + max + "$");
        }else{
            sendMsg(sender, "The richest player is: " + usernames + ". He has a balance of " + max + "$");
        }
    }
}

package play.ai.dragonrealm.geiloutils.new_configs.containers;


import play.ai.dragonrealm.geiloutils.new_configs.IJsonFile;
import play.ai.dragonrealm.geiloutils.new_configs.models.ChunkStorage;
import play.ai.dragonrealm.geiloutils.new_configs.models.SellablePlots;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FTBUtilsConfig implements IJsonFile {

    public FTBUtilsConfig(){}

    public boolean enabled;
    public String sellingTeamName = "server_seller";
    public String serverHoldingTeamName = "land_holdings_incorporated";
    public HashMap<String, String> inLocoBigas;
    public List<SellablePlots> plots;

    public String getSellingTeamName() {
        return sellingTeamName;
    }

    public String getServerHoldingTeamName() {
        return serverHoldingTeamName;
    }

    public String getTeamIfExist(String uuid) {
        return inLocoBigas.getOrDefault(uuid, null);
    }

    public void addTeamMemory(String uuid, String teamName){
        inLocoBigas.put(uuid, teamName);
    }

    public void clearTeamMemory(String uuid){
        inLocoBigas.remove(uuid);
    }

    public int getNextPlotNumber(){
        return plots.size();
    }

    public void addSellables(SellablePlots plot) {
        plots.add(plot);
    }

    public List<SellablePlots> getSellables(){
        return plots;
    }

    @Nonnull
    @Override
    public String getFileName() {
        return "ftbutilities.json";
    }

    @Nonnull
    @Override
    public IJsonFile getDefaultJson() {
        FTBUtilsConfig inter = new FTBUtilsConfig();
        inter.sellingTeamName = "server_seller";
        inter.serverHoldingTeamName = "land_holdings_incorporated";
        inter.enabled = false;
        inter.inLocoBigas = new HashMap<>();
        inter.plots = new ArrayList<>();
        return inter;
    }
}

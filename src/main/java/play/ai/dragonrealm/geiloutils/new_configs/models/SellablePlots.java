package play.ai.dragonrealm.geiloutils.new_configs.models;

import play.ai.dragonrealm.geiloutils.new_configs.ConfigAccess;

import java.util.ArrayList;
import java.util.List;

public class SellablePlots {

    private String plotName;
    private int plotPrice;
    private List<ChunkStorage> containedChunks;

    public SellablePlots(int plotPrice) {
        this.plotName = "plot" + ConfigAccess.getFTBConfig().getNextPlotNumber();
        this.plotPrice = plotPrice;
        this.containedChunks = new ArrayList<>();
    }

    public List<ChunkStorage> getContainedChunks() {
        return containedChunks;
    }

    public void addChunk(ChunkStorage storage) {
        this.containedChunks.add(storage);
    }
}

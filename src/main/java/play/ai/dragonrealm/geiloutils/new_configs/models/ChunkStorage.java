package play.ai.dragonrealm.geiloutils.new_configs.models;

public class ChunkStorage {

    int posX;
    int posZ;
    int dim;

    public ChunkStorage(int posX, int posZ, int dim){
        this.posX = posX;
        this.posZ = posZ;
        this.dim = dim;
    }

    public int getDim() {
        return dim;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosZ() {
        return posZ;
    }
}

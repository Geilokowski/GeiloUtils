package play.ai.dragonrealm.geiloutils.multiworld.Void;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
import play.ai.dragonrealm.geiloutils.GeiloUtils;
import play.ai.dragonrealm.geiloutils.multiworld.Void.ChunkGeneratorVoid;

public class WorldProviderVoid extends WorldProvider {
    private DimensionType dimensionType;
    public WorldProviderVoid(){

    }

    public WorldProviderVoid(DimensionType dimensionType){
        this.dimensionType = dimensionType;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new ChunkGeneratorVoid(world);
    }

    @Override
    public DimensionType getDimensionType() {
        return GeiloUtils.type;
    }

    @Override
    public boolean isSurfaceWorld(){
        return true;
    }

    /* (non-Javadoc)
     * @see net.minecraft.world.WorldProvider#canDoLightning(net.minecraft.world.chunk.Chunk)
     */
    @Override
    public boolean canDoLightning(net.minecraft.world.chunk.Chunk chunk)
    {
        return true;
    }

    /* (non-Javadoc)
     * @see net.minecraft.world.WorldProvider#canDoRainSnowIce(net.minecraft.world.chunk.Chunk)
     */
    @Override
    public boolean canDoRainSnowIce(Chunk chunk)
    {
        return false;
    }

    /* (non-Javadoc)
     * @see net.minecraft.world.WorldProvider#canSnowAt(net.minecraft.util.math.BlockPos, boolean)
     */
    @Override
    public boolean canSnowAt(BlockPos pos, boolean checkLight)
    {
        return false;
    }
}

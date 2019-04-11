package play.ai.dragonrealm.geiloutils.discord.command;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class GeiloPorter extends Teleporter {
    private final WorldServer worldServerInstance;

    private double x;
    private double y;
    private double z;

    public GeiloPorter(WorldServer worldIn) {
        super(worldIn);
        this.worldServerInstance = world;
    }

    public GeiloPorter(WorldServer world, double x, double y, double z) {
        super(world);
        this.worldServerInstance = world;
        this.x = x;
        this.y = y;
        this.z = z;

    }

    @Override
    public boolean placeInExistingPortal(Entity entity, float rotationYaw) {
        entity.motionX = 0;
        entity.motionY = 0;
        entity.motionZ = 0;
        entity.fallDistance = 0;
        return true;
    }


    @Override
    public boolean makePortal(Entity entity) {
        return true;
    }

    @Override
    public void removeStalePortalLocations(long worldTime) {

    }


}

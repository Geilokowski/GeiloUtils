package play.ai.dragonrealm.geiloutils.discord.command;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class GeiloPorter extends Teleporter {

    public GeiloPorter(WorldServer worldIn) {
        super(worldIn);
    }

    @Override
    public void placeInPortal(Entity entity, float rotationYaw) {

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

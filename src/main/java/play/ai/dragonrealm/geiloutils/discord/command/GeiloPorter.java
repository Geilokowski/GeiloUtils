package play.ai.dragonrealm.geiloutils.discord.command;

import java.util.function.Function;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Teleporter;
import net.minecraft.world.server.ServerWorld;

public class GeiloPorter extends Teleporter {

    public GeiloPorter(ServerWorld worldIn) {
        super(worldIn);
    }

    @Override
    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        entity.setDeltaMovement(Vector3d.ZERO);
        entity.fallDistance = 0;
        return entity;
    }


}

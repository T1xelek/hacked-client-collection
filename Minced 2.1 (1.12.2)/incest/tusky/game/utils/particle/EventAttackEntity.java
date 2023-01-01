package incest.tusky.game.utils.particle;
import incest.tusky.game.event.events.Event;
import net.minecraft.entity.Entity;

public abstract class EventAttackEntity implements Event {
    private Entity entity;

    public EventAttackEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }


}

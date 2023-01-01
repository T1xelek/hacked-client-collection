package incest.tusky.game.event.events.impl.packet;

import incest.tusky.game.event.events.callables.EventCancellable;
import net.minecraft.entity.Entity;

public class EventAttackSilent extends EventCancellable {

    private final Entity targetEntity;

    public EventAttackSilent(Entity targetEntity) {
        this.targetEntity = targetEntity;
    }

    public Entity getTargetEntity() {
        return this.targetEntity;
    }
}

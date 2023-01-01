package incest.tusky.game.event.events.impl.player;

import incest.tusky.game.event.events.Event;
import net.minecraft.util.EnumHandSide;

public class EventViewModel implements Event {

    private final EnumHandSide enumHandSide;

    public EventViewModel(EnumHandSide enumHandSide) {
        this.enumHandSide = enumHandSide;
    }

    public EnumHandSide getEnumHandSide() {
        return this.enumHandSide;
    }
}

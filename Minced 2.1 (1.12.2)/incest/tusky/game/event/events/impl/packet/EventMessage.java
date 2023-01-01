package incest.tusky.game.event.events.impl.packet;

import incest.tusky.game.event.events.Event;
import incest.tusky.game.event.events.callables.EventCancellable;

public class EventMessage extends EventCancellable implements Event {

    public String message;

    public EventMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}

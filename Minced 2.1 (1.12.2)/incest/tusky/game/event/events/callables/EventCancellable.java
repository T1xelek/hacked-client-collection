package incest.tusky.game.event.events.callables;

import incest.tusky.game.event.events.Cancellable;
import incest.tusky.game.event.events.Event;

public abstract class EventCancellable implements Event, Cancellable {

    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean state) {
        cancelled = state;
    }

}

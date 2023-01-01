package incest.tusky.game.utils.otherutils.gayutil;

import incest.tusky.game.event.events.Cancellable;
import incest.tusky.game.event.events.Event;

public abstract class EventCancellable
        implements Event, Cancellable
{
    private boolean cancelled;

    public boolean isCancelled() {
        /* 12 */     return this.cancelled;
    }


    public void setCancelled(boolean state) {
        /* 17 */     this.cancelled = state;
    }

    public void cancel() {
        /* 21 */     this.cancelled = true;
    }
}
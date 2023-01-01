/*
 * Decompiled with CFR 0.150.
 */
package baritone.events.events.network;

import incest.tusky.game.event.events.Event;
import incest.tusky.game.event.events.callables.EventCancellable;

public class EventSendChat
extends EventCancellable
implements Event {
    private String message;

    public EventSendChat(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


package incest.tusky.game.event.events.impl.input;

import incest.tusky.game.event.events.Event;

public class EventInputKey implements Event {

    private int key;

    public EventInputKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}

package incest.tusky.game.event.events.impl.render;

import incest.tusky.game.event.events.Event;

public class EventBossBar implements Event {
    public String bossName;

    public EventBossBar(String bossName) {
        this.bossName = bossName;
    }

}
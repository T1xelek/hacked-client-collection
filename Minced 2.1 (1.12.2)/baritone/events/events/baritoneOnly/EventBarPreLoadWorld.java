/*
 * Decompiled with CFR 0.150.
 */
package baritone.events.events.baritoneOnly;

import net.minecraft.client.multiplayer.WorldClient;
import incest.tusky.game.event.events.Event;
import incest.tusky.game.event.events.callables.EventCancellable;
public class EventBarPreLoadWorld
extends EventCancellable
implements Event {
    public WorldClient world;

    public EventBarPreLoadWorld(WorldClient worldIn) {
        this.world = worldIn;
    }
}


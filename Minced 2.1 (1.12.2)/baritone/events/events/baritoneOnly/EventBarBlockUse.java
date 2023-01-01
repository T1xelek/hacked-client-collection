/*
 * Decompiled with CFR 0.150.
 */
package baritone.events.events.baritoneOnly;

import net.minecraft.util.math.BlockPos;
import incest.tusky.game.event.events.Event;
import incest.tusky.game.event.events.callables.EventCancellable;
public class EventBarBlockUse
extends EventCancellable
implements Event {
    public BlockPos position;

    public EventBarBlockUse(BlockPos pos) {
        this.position = pos;
    }
}


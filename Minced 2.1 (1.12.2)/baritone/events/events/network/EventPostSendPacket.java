/*
 * Decompiled with CFR 0.150.
 */
package baritone.events.events.network;

import net.minecraft.network.Packet;
import incest.tusky.game.event.events.Event;
import incest.tusky.game.event.events.callables.EventCancellable;

public class EventPostSendPacket
extends EventCancellable
implements Event {
    private Packet packet;

    public EventPostSendPacket(Packet packet) {
        this.packet = packet;
    }

    public Packet getPacket() {
        return this.packet;
    }
}


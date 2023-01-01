package incest.tusky.game.utils.otherutils.pon;

import incest.tusky.game.event.events.Event;
import net.minecraft.client.renderer.chunk.RenderChunk;

public class EventRenderChunkContainer
        implements Event {
    public net.minecraft.client.renderer.chunk.RenderChunk RenderChunk;

    public EventRenderChunkContainer(RenderChunk renderChunk) {
        this.RenderChunk = renderChunk;
    }
}


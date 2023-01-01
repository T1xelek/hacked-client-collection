package incest.tusky.game.event.events;

import incest.tusky.game.event.events.callables.EventCancellable;
import net.minecraft.block.BlockWeb;
import net.minecraft.util.math.BlockPos;

public class EventWebSolid extends EventCancellable {

    private final BlockWeb blockWeb;
    private final BlockPos pos;

    public EventWebSolid(BlockWeb blockLiquid, BlockPos pos) {
        this.blockWeb = blockLiquid;
        this.pos = pos;
    }

    public BlockWeb getBlock() {
        return blockWeb;
    }

    public BlockPos getPos() {
        return pos;
    }
}

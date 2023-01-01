package incest.tusky.game.utils.render;

import incest.tusky.game.utils.Helper;
import net.minecraft.network.play.server.SPacketPlayerListItem;

public class EventPacketPlayerList implements Helper {

    private final SPacketPlayerListItem.AddPlayerData addPlayerData;
    private final SPacketPlayerListItem.Action action;

    public EventPacketPlayerList(SPacketPlayerListItem.AddPlayerData addPlayerData, SPacketPlayerListItem.Action action) {
        this.addPlayerData = addPlayerData;
        this.action = action;
    }

    public SPacketPlayerListItem.AddPlayerData getPlayerData() {
        return this.addPlayerData;
    }

    public SPacketPlayerListItem.Action getAction() {
        return this.action;
    }
}

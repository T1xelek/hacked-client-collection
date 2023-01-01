package incest.tusky.game.module.impl.Player;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.packet.EventSendPacket;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import net.minecraft.network.play.client.CPacketCloseWindow;

public class XCarry extends Module {
    public XCarry() {
        super("XCarry", ModuleCategory.Player);
    }

    @EventTarget
    public void onSendPacket(EventSendPacket event) {
        if (event.getPacket() instanceof CPacketCloseWindow) {
            event.setCancelled(true);
        }
    }
}
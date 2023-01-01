package incest.tusky.game.module.impl.Player;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.packet.EventReceivePacket;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.utils.Helper;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class NoServerRotations extends Module {

    public NoServerRotations() {
        super("NoRotate", "Отключает ротации сервера", ModuleCategory.Player);
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook packet = (SPacketPlayerPosLook) event.getPacket();
            packet.yaw = Helper.mc.player.rotationYaw;
            packet.pitch = Helper.mc.player.rotationPitch;
        }
    }
}
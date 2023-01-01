package incest.tusky.game.module.impl.Movement;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.packet.EventSendPacket;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.utils.movement.MovementUtils;
import net.minecraft.network.play.client.CPacketPlayer;

public class MatrixElytra extends Module {

    public MatrixElytra() {
        super("MatrixElytra", "Позволяет летать на Elytra на RW", ModuleCategory.Movement);
        addSettings();
    }
    @EventTarget
    public void onSendPacket(EventSendPacket event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            CPacketPlayer cPacketPlayer = (CPacketPlayer)event.getPacket();
            cPacketPlayer.onGround = false;
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        MovementUtils.setSpeed(MovementUtils.getSpeed() + 0.004f);
        if (mc.gameSettings.keyBindJump.isKeyDown() || mc.player.isCollidedHorizontally) {
            mc.player.motionY = 0.25D;
        } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.motionY = -0.30000001192092896D;
        } else {
            mc.player.motionY = (mc.player.ticksExisted % 2 != 0) ? -0.05D : 0.09D;
        }
    }
}
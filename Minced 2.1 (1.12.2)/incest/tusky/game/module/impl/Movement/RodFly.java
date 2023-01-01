package incest.tusky.game.module.impl.Movement;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.utils.movement.MovementUtils;

public class RodFly extends Module {

    public RodFly() {
        super("RodFly", "Позволяет вам летать на удочке на SunRise", ModuleCategory.Movement);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        mc.player.motionY = 0.001f;
        MovementUtils.setSpeed(MovementUtils.getSpeed() + 0.1f);
        if (mc.gameSettings.keyBindJump.isKeyDown() || mc.player.isCollidedHorizontally) {
            mc.player.motionY = 0.25D;
        } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
            mc.player.motionY = -0.30000001192092896D;
        }
        if (mc.gameSettings.keyBindLeft.isKeyDown()) {
            MovementUtils.strafe(MovementUtils.getSpeed());
        }
        if (mc.gameSettings.keyBindRight.isKeyDown()) {
            MovementUtils.strafe(MovementUtils.getSpeed());
        }
        if (mc.gameSettings.keyBindBack.isKeyDown()) {
            MovementUtils.strafe(MovementUtils.getSpeed());
        }
        if (mc.gameSettings.keyBindForward.isKeyDown()) {
            MovementUtils.strafe(MovementUtils.getSpeed());
        }
    }
}



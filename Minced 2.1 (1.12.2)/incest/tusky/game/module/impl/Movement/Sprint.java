package incest.tusky.game.module.impl.Movement;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.utils.Helper;
import incest.tusky.game.utils.movement.MovementUtils;

public class Sprint extends Module {
    public Sprint() {
        super("AutoSprint","Позволяет быстрее передвигаться", ModuleCategory.Movement);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (Helper.mc.player.getFoodStats().getFoodLevel() / 2 > 3) {
            Helper.mc.player.setSprinting(MovementUtils.isMoving());
        }
    }

    @Override
    public void onDisable() {
        Helper.mc.player.setSprinting(false);
        super.onDisable();
    }
}

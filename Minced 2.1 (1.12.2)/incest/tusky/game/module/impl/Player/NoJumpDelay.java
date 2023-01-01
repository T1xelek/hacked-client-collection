package incest.tusky.game.module.impl.Player;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.Module;
import incest.tusky.game.utils.Helper;
import incest.tusky.game.module.ModuleCategory;

public class NoJumpDelay extends Module {
    public NoJumpDelay() {
        super("NoJumpDelay", "Нету задержки на прыжок", ModuleCategory.Player);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        Helper.mc.player.setJumpTicks(0);
    }
}

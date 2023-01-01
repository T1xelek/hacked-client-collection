package incest.tusky.game.module.impl.Combat;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.module.Module;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.ModuleCategory;

public class KeepSprint extends Module {

    public static NumberSetting speed;
    public static BooleanSetting setSprinting;

    public KeepSprint() {
        super("KeepSprint", "Регулировка скорости пред ударом", ModuleCategory.Combat);
        speed = new NumberSetting("Keep Speed", 1, 0.5F, 2, 0.01F, () -> true);
        setSprinting = new BooleanSetting("Set Sprinting", true, () -> true);
        addSettings(setSprinting, speed);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
    }
}
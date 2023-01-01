package incest.tusky.game.module.impl.Combat;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.Module;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.module.ModuleCategory;

public class HitBox extends Module {

    public static NumberSetting hitboxSize = new NumberSetting("HitBox Size", "Размер хитбокса игрока", 0.25f, 0.1f, 2.f, 0.1f, () -> true);

    public HitBox() {
        super("HitBox", "Увеличивает хитбокс игрока.", ModuleCategory.Combat);
        addSettings(hitboxSize);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
    }
}

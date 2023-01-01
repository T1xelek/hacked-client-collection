package incest.tusky.game.module.impl.Render;

import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.ListSetting;

public class Notifications extends Module {
    public static ListSetting notifMode;

    public static BooleanSetting state;

    public Notifications() {
        super("Notifications", "Показывает информацию о модуле.", ModuleCategory.Visual);
        state = new BooleanSetting("Module State", true, () -> true);
        notifMode = new ListSetting("Notification Mode", "Rect", () -> true, "Rect", "Chat");
        addSettings(notifMode,state);
    }
}
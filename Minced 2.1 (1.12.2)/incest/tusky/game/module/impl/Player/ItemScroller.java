package incest.tusky.game.module.impl.Player;

import incest.tusky.game.module.Module;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.module.ModuleCategory;

public class ItemScroller extends Module {

    public static NumberSetting scrollerDelay;

    public ItemScroller() {
        super("ItemScroller", "Скорость скролла предметов", ModuleCategory.Player);

        scrollerDelay = new NumberSetting("Scroller Delay", 80, 0, 100, 1, () -> true);
        addSettings(scrollerDelay);

    }
}
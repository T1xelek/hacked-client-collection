package incest.tusky.game.module.impl.Util;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.module.Module;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.utils.inventory.InventoryUtil;

public class ElytraFix extends Module {
    public ElytraFix() {
        super("ElytraFix", "Свапает элитру назад", ModuleCategory.Player);
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (mc.player.ticksExisted % 4 == 0) {
            InventoryUtil.swapElytraToChestplate();
        }
    }
}

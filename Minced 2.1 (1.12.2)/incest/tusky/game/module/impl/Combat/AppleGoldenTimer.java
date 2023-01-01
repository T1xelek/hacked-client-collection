package incest.tusky.game.module.impl.Combat;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.OnEatFood;
import incest.tusky.game.module.Module;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.module.ModuleCategory;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAppleGold;

public class AppleGoldenTimer extends Module {
    public static boolean cooldown;
    private boolean isEated;
    private NumberSetting ticks = new NumberSetting("Cooldown", 3, 1, 6, 0.5f, NumberSetting.NumberType.SEC);

    public AppleGoldenTimer() {
        super("CDApple", "CoolDown на поедание яблока.", ModuleCategory.Combat);
        addSettings(ticks);
    }

    @EventTarget
    public void onUpdate(OnEatFood eventUpdate) {
        if (!(eventUpdate.getItem().getItem() instanceof ItemAppleGold)) return;
        mc.player.getCooldownTracker().setCooldown(Items.GOLDEN_APPLE, ticks.getCurrentValueInt() * 20);
    }
}
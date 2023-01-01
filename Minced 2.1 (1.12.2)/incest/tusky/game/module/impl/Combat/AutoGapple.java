package incest.tusky.game.module.impl.Combat;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventPreMotion;
import incest.tusky.game.module.Module;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.module.ModuleCategory;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;

public class AutoGapple extends Module {
    private boolean isActive;
    public static NumberSetting health;

    public AutoGapple() {
        super("AutoGApple", "Автоматически кушает гепл", ModuleCategory.Combat);
        health = new NumberSetting("Health Amount", 15, 1, 20, 1, () -> true);
        addSettings(health);
    }

    @EventTarget
    public void onUpdate(EventPreMotion eventUpdate) {
        if (mc.player == null || mc.world == null)
            return;
        if (isGoldenApple(mc.player.getHeldItemOffhand()) && mc.player.getHealth() <= health.getCurrentValue()) {
            isActive = true;
            mc.gameSettings.keyBindUseItem.pressed = true;
        } else if (isActive) {
            mc.gameSettings.keyBindUseItem.pressed = false;
            isActive = false;
        }
    }

    private boolean isGoldenApple(ItemStack itemStack) {
        return (itemStack != null && !itemStack.isEmpty() && itemStack.getItem() instanceof ItemAppleGold);
    }
}

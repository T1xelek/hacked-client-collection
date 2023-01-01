package incest.tusky.game.module.impl.Movement;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.utils.Helper;
import incest.tusky.game.utils.movement.MovementUtils;
import net.minecraft.init.MobEffects;

public class WaterSpeed extends Module {

    private final NumberSetting speed;
    private final BooleanSetting speedCheck;

    public WaterSpeed() {
        super("WaterSpeed", "Позволяет быстро бегать в воде" , ModuleCategory.Movement);
        speed = new NumberSetting("Speed Amount", 0.4f, 0.1F, 4, 0.01F, () -> true);
        speedCheck = new BooleanSetting("Potion Check", false, () -> true);
        addSettings(speedCheck, speed);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
    	if (mc.player.isInWater() || mc.player.isInLava()) {
        	if (mc.player.isPotionActive(MobEffects.SPEED) && speedCheck.getCurrentValue()) {
        	     MovementUtils.setSpeed(speed.getNumberValue() * 1.5f);
        	} else {
        	     MovementUtils.setSpeed(speed.getNumberValue());
        	}
        }
    }
}

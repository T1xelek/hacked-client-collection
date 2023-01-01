package incest.tusky.game.module.impl.Movement;

import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.utils.movement.MovementUtils;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.EventWebSolid;
import incest.tusky.game.event.events.impl.player.EventMove;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;

public class NoWeb extends Module {
	public static BooleanSetting solid = new BooleanSetting("Solid", true, () -> true);
    public NoWeb() {
        super("Anti Web", "Позволяет быстро ходить в паутине", ModuleCategory.Movement);
        addSettings(solid);
    }


    @EventTarget
    public void onMove(EventMove event) {
        if (isEnabled()) {
                if (mc.player.isInWeb) {
                    mc.player.motionY += 2F;
                } else {
                    if (mc.gameSettings.keyBindJump.isKeyDown())
                        return;
                    mc.player.isInWeb = false;
                }
                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    return;
                }
                if (mc.player.isInWeb && !mc.gameSettings.keyBindSneak.isKeyDown()) {
                    MovementUtils.setEventSpeed(event, 0.483);
                }
        }
    }
    @EventTarget
    public void onWebSolid(EventWebSolid event) {
    	if (solid.getCurrentValue())
        event.setCancelled(true);
    }
}

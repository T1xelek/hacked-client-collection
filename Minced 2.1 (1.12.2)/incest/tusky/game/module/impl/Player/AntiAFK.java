package incest.tusky.game.module.impl.Player;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventPreMotion;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.Module;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.utils.math.GCDFix;
import incest.tusky.game.utils.math.MathematicHelper;
import incest.tusky.game.utils.math.TimerHelper;
import org.apache.commons.lang3.RandomStringUtils;

public class AntiAFK extends Module {
    private final BooleanSetting sendMessage;
    private final BooleanSetting spin;

    public final NumberSetting sendDelay;
    public final BooleanSetting jump = new BooleanSetting("Jump", true, () -> true);
    public TimerHelper timerHelper = new TimerHelper();
    public float rot = 0;

    public AntiAFK() {
        super("AntiAFK", "Не дает серверу кикнуть вас, за афк", ModuleCategory.Player);
        spin = new BooleanSetting("Spin", true, () -> true);
        sendMessage = new BooleanSetting("Random SMS", true, () -> true);
        sendDelay = new NumberSetting("Send Delay", 1, 1, 20, 1, sendMessage::getCurrentValue, NumberSetting.NumberType.SEC);
        addSettings(spin, sendMessage, sendDelay, jump);
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (jump.getCurrentValue() && mc.player.onGround && !mc.gameSettings.keyBindJump.pressed) {
            mc.player.jump();
        }
    }

    @EventTarget
    public void onPreMotion(EventPreMotion event) {
        if (spin.getCurrentValue()) {
            float yaw = GCDFix.getFixedRotation((float) (Math.floor((double) this.spinAim(25)) + (double) MathematicHelper.randomizeFloat(-4.0F, 1.0F)));
            event.setYaw(yaw);
            mc.player.renderYawOffset = yaw;
            mc.player.rotationPitchHead = 0;
            mc.player.rotationYawHead = yaw;
        }
        if (timerHelper.hasReached(sendDelay.getCurrentValue() * 1000) && sendMessage.getCurrentValue()) {
            mc.player.sendChatMessage("/" + RandomStringUtils.randomAlphabetic(3) + RandomStringUtils.randomNumeric(3));
            timerHelper.reset();
        }
    }

    public float spinAim(float rots) {
        rot += rots;
        return rot;
    }
}

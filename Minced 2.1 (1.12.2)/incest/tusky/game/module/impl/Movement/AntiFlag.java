package incest.tusky.game.module.impl.Movement;

import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.packet.EventReceivePacket;
import incest.tusky.game.tuskevich;
import incest.tusky.game.ui.notif.NotifModern;
import incest.tusky.game.ui.notif.NotifRender;
import incest.tusky.game.ui.settings.Setting;
import incest.tusky.game.ui.settings.impl.BooleanSetting;


public class AntiFlag extends Module {
    public static BooleanSetting disable = new BooleanSetting("Auto Disable", true, () -> true);

    public AntiFlag() {
        super("AntiFlag", "Позволяет не флагаться", ModuleCategory.Movement);
        addSettings(new Setting[]{(Setting) disable});
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        if (isEnabled() &&
                event.getPacket() instanceof net.minecraft.network.play.server.SPacketPlayerPosLook) {
            if (tuskevich.instance.featureManager.getFeature(Speed.class).isEnabled()) {
                featureAlert("Speed");
                if (disable.getCurrentValue()) {
                    tuskevich.instance.featureManager.getFeature(Speed.class).toggle();
                }
            } else if (tuskevich.instance.featureManager.getFeature(Spider.class).isEnabled() && mc.player.isCollidedHorizontally) {
                featureAlert("Spider");
                if (disable.getCurrentValue())
                    tuskevich.instance.featureManager.getFeature(Spider.class).toggle();
            } else if (tuskevich.instance.featureManager.getFeature(elytrafly.class).isEnabled() && mc.player.isInLiquid()) {
                featureAlert("ElytraFlySunrise");
                if (disable.getCurrentValue()) {
                    tuskevich.instance.featureManager.getFeature(elytrafly.class).toggle();
                }
            } else if (tuskevich.instance.featureManager.getFeature(Timer.class).isEnabled()) {
                featureAlert("Timer");
                if (disable.getCurrentValue()) {
                    tuskevich.instance.featureManager.getFeature(Timer.class).toggle();
                }
            }
        }
    }

    public void featureAlert(String feature) {
        NotifRender.queue("Anti Flag Debug", "Module " + feature + " was flagged" + (mc.player.isInWater() ? " on water" : "") + (mc.player.isInLava() ? " in lava" : "") + "!", 3, NotifModern.WARNING);
    }
}

package incest.tusky.game.module.impl.Combat;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.packet.EventReceivePacket;
import incest.tusky.game.module.Module;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.ListSetting;
import incest.tusky.game.module.ModuleCategory;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;

public class Velocity extends Module {
    public static BooleanSetting cancelOtherDamage;
    public static ListSetting velocityMode;


    public Velocity() {
        super("Velocity", "Убирает отталкивание при ударе", ModuleCategory.Combat);
        velocityMode = new ListSetting("Velocity Mode", "Packet", () -> true, "Packet", "Matrix");
        cancelOtherDamage = new BooleanSetting("Pon", true, () -> true);
        addSettings(velocityMode);
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        String mode = velocityMode.getOptions();
            if (cancelOtherDamage.getCurrentValue() && mc.player.hurtTime > 0 && event.getPacket()
                    instanceof SPacketEntityVelocity && !mc.player.isPotionActive(MobEffects.FIRE_RESISTANCE) &&
                    (mc.player.isPotionActive(MobEffects.POISON) || mc.player.isPotionActive(MobEffects.WITHER) || mc.player.isBurning())) {
                event.setCancelled(true);
        }
        if (mode.equalsIgnoreCase("Packet")) {
            if ((event.getPacket() instanceof SPacketEntityVelocity || event.getPacket() instanceof SPacketExplosion) &&
                    ((SPacketEntityVelocity)event.getPacket()).getEntityID() == mc.player.getEntityId()) {
                event.setCancelled(true);
            }
        } else if (mode.equals("Matrix")) {
            if (mc.player.hurtTime > 8) {
                mc.player.onGround = true;
            }
        }
    }
}
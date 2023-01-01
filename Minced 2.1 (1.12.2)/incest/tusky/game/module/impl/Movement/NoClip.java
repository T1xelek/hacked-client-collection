package incest.tusky.game.module.impl.Movement;

import incest.tusky.game.module.Module;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.ListSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.tuskevich;
import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.packet.EventReceivePacket;
import incest.tusky.game.event.events.impl.packet.EventSendPacket;
import incest.tusky.game.event.events.impl.player.EventPreMotion;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.utils.math.TimerHelper;
import incest.tusky.game.utils.movement.MovementUtils;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

public class NoClip extends Module {
    public static final ListSetting noClipMode = new ListSetting("NoClip Mode", "Vanilla", "Vanilla", "NCP");
    public NumberSetting customSpeedXZ;
    public BooleanSetting customSpeed;
    public BooleanSetting rehub;
    TimerHelper timerHelper = new TimerHelper();

    public NoClip() {
        super("NoClip", "Позволяет ходить в блоках", ModuleCategory.Movement);
        customSpeed = new BooleanSetting("Custom Speed", false, () -> !noClipMode.currentMode.equals("NCP"));
        customSpeedXZ = new NumberSetting("XZ Speed", 1.0f, 0.01f, 1.5f, 0.01f, () -> !noClipMode.currentMode.equals("NCP") && customSpeed.getCurrentValue());
        rehub = new BooleanSetting("ReHub",true, () -> true);
        addSettings(noClipMode,customSpeed,customSpeedXZ, rehub);
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
    	if (event.getPacket() instanceof SPacketPlayerPosLook && mc.player.ticksExisted % 5 == 4 && rehub.getCurrentValue()) {
    		MovementUtils.rehub(300);
    	}
        if (noClipMode.currentMode.equals("NCP") && event.getPacket() instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook packet = (SPacketPlayerPosLook) event.getPacket();
            mc.player.connection.sendPacket(new CPacketConfirmTeleport(packet.getTeleportId()));
            mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(packet.getX(), mc.player.posY, packet.getZ(), packet.getYaw(), packet.getPitch(), false));
            mc.player.setPosition(packet.getX(), mc.player.posY, packet.getZ());
            event.setCancelled(true);
        }
    }

    @EventTarget
    public void onSendPacket(EventSendPacket event) {
        if (noClipMode.currentMode.equals("NCP") && event.getPacket() instanceof CPacketPlayer && !mc.player.onGround) {
            CPacketPlayer packet = (CPacketPlayer) event.getPacket();
            packet.y = mc.player.posY + 50.0;
        }
    }

    @EventTarget
    public void onPreMotionUpdate2(EventPreMotion event) {
        if (noClipMode.currentMode.equals("NCP")) {
            mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SPRINTING));
            if (mc.player.onGround) {
                mc.player.jump();
            }
        }
    }

    @EventTarget
    public void onPreMotionUpdate(EventPreMotion event) {
        if (noClipMode.currentMode.equals("NCP")) {
            mc.player.setVelocity(0.0, 0.0, 0.0);
            event.setCancelled(true);
            float speedY = 0.0f;
            if (mc.player.movementInput.jump) {
                if (!timerHelper.hasReached(3000.0)) {
                    speedY = mc.player.ticksExisted % 20 == 0 ? -0.04f : 0.031f;
                } else {
                    timerHelper.reset();
                    speedY = -0.08f;
                }
            } else if (mc.player.movementInput.sneak) {
                speedY = -0.0031f;
            }
            double[] dir = MovementUtils.forward(0.02f);
            mc.player.motionX = dir[0];
            mc.player.motionY = speedY;
            mc.player.motionZ = dir[1];
            mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(mc.player.posX + mc.player.motionX, mc.player.posY + mc.player.motionY, mc.player.posZ + mc.player.motionZ, mc.player.rotationYaw, mc.player.rotationPitch, mc.player.onGround));
            double x = mc.player.posX + mc.player.motionX;
            double y = mc.player.posY + mc.player.motionY;
            double z = mc.player.posZ + mc.player.motionZ;
            mc.player.connection.sendPacket(new CPacketPlayer.Position(x, y -= -1337.0, z, mc.player.onGround));
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (noClipMode.currentMode.equals("Vanilla")) {
            if (mc.player != null) {
                mc.player.noClip = true;
                mc.player.motionY = 0.00001;
                if(customSpeed.getCurrentValue()) {
                    mc.player.motionX *= customSpeedXZ.getCurrentValue();
                    mc.player.motionZ *= customSpeedXZ.getCurrentValue();
                }
                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.player.motionY = 0.2;
                }
                if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                    mc.player.motionY = -0.2;
                }
            }
        }
    }


    public static boolean isNoClip(Entity entity) {
        if (tuskevich.instance.featureManager.getFeature(NoClip.class).isEnabled() && mc.player != null && (mc.player.ridingEntity == null || entity == mc.player.ridingEntity))
            return true;
        return entity.noClip;

    }

    public void onDisable() {
        mc.player.noClip = false;
        super.onDisable();
    }
}

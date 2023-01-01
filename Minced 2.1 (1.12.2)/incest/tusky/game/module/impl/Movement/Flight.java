package incest.tusky.game.module.impl.Movement;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.packet.EventReceivePacket;
import incest.tusky.game.event.events.impl.player.EventPreMotion;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.ui.settings.impl.ListSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.utils.Helper;
import incest.tusky.game.utils.math.TimerHelper;
import incest.tusky.game.utils.movement.MovementUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Flight extends Module {
    public boolean damage = false;
    public boolean flaging = false;
    public TimerHelper timerHelper = new TimerHelper();
    public static ListSetting flyMode = new ListSetting("Mode", "Matrix Glide", () -> true, "Vanilla", "Matrix Fall", "Matrix Glide", "Matrix Web");
    public final NumberSetting speed = new NumberSetting("Speed", 5F, 0.1F, 15F, 0.1F, () -> flyMode.currentMode.equals("Vanilla") || flyMode.currentMode.equals("Matrix Glide"));
    public final NumberSetting motionY = new NumberSetting("Motion Y", 0.05f, 0.01f, 0.1f, 0.01F, () -> flyMode.currentMode.equals("Matrix Elytra"));

    public float ticks = 0;

    public Flight() {
        super("Flight", "Вы можете летать.", ModuleCategory.Movement);
        addSettings(flyMode, motionY, speed);
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        flaging = false;
        if (this.isEnabled()) {

            if (event.getPacket() instanceof SPacketPlayerPosLook) {
                flaging = true;
            }
        }
    }


    @EventTarget
    public void onPreUpdate(EventPreMotion event) {
        String mode = flyMode.getOptions();
        if (mode.equalsIgnoreCase("Matrix Glide")) {
            if (Helper.mc.player.onGround) {
                Helper.mc.player.jump();
                timerHelper.reset();
            } else if (!Helper.mc.player.onGround && timerHelper.hasReached(280)) {
                Helper.mc.player.motionY = -0.02D;
                if (Helper.mc.gameSettings.keyBindJump.pressed) {
                    Helper.mc.player.motionY = 1.0;
                }
                if (Helper.mc.gameSettings.keyBindSneak.pressed) {
                    Helper.mc.player.motionY = -0.1;
                }
                MovementUtils.setSpeed(speed.getCurrentValue());
            }
        } else if (mode.equalsIgnoreCase("Vanilla")) {
            Helper.mc.player.capabilities.isFlying = true;
            Helper.mc.player.capabilities.allowFlying = true;

            if (Helper.mc.gameSettings.keyBindJump.pressed) {
                Helper.mc.player.motionY = 2.0;
            }

            if (Helper.mc.gameSettings.keyBindSneak.pressed) {
                Helper.mc.player.motionY = -2.0;
            }
            MovementUtils.setSpeed(speed.getCurrentValue());

            
        }  else if (mode.equalsIgnoreCase("Matrix Web")) {

            if (Helper.mc.player.isInWeb) {
                Helper.mc.player.isInWeb = false;
                Helper.mc.player.motionY *= Helper.mc.player.ticksExisted % 2 == 0 ? -100 : -0.05;
            }
        } else if (mode.equalsIgnoreCase("Matrix Fall")) {
            if (damage) {
                if (Helper.mc.player.fallDistance > 0 && Helper.mc.player.ticksExisted % 4 == 0) Helper.mc.player.motionY = -0.003;
            }
            if (Helper.mc.player.fallDistance >= 3) {
                damage = true;
                Helper.mc.timer.timerSpeed = 2f;
                Helper.mc.player.motionY = 0;
                Helper.mc.player.connection.sendPacket(new CPacketPlayer.Position(Helper.mc.player.posX, Helper.mc.player.posY, Helper.mc.player.posZ, false));
             //   Helper.mc.player.connection.sendPacket(new CPacketPlayer.Position(Helper.mc.player.posX, Helper.mc.player.posY, Helper.mc.player.posZ, true));
                Helper.mc.player.motionY = -0.003;
                Helper.mc.timer.timerSpeed = 1f;

                Helper.mc.player.fallDistance = 0;
            }
        }

    }

    @Override
    public void onDisable() {
        super.onDisable();
        Helper.mc.player.capabilities.isFlying = false;
        Helper.mc.player.capabilities.allowFlying = false;
        Helper.mc.timer.timerSpeed = 1f;
    }
}
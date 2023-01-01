package incest.tusky.game.module.impl.Movement;

import incest.tusky.game.module.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventLiquidSolid;
import incest.tusky.game.event.events.impl.player.EventPreMotion;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.ListSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.utils.movement.MovementUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class Jesus extends Module
{
    public static boolean jesusTick;
    public static boolean swap;
    public static int ticks;
    public static ListSetting mode;
    public static NumberSetting speed;
    public static NumberSetting NCPSpeed;
    public static NumberSetting motionUp;
    public static BooleanSetting useTimer;
    private final NumberSetting timerSpeed;
    private final BooleanSetting speedCheck;
    private final BooleanSetting autoMotionStop;
    private final BooleanSetting autoWaterDown;
    private final BooleanSetting upTp;
    private int waterTicks;
    int y = 0;
    public static boolean inWater;

    public Jesus() {
        super("Jesus", "Позволяет глайдить на воде", ModuleCategory.Movement);
        this.speed = new NumberSetting("Speed", 10f, 1f, 35f, 1f, () -> true);
        this.timerSpeed = new NumberSetting("TimerSpeed", 1.05f, 1.01f, 1.5f, 0.01f, () -> Jesus.useTimer.getCurrentValue());
        this.speedCheck = new BooleanSetting("SpeedCheck", false, () -> true);
        this.autoMotionStop = new BooleanSetting("Auto Motion Stop", true, () -> Jesus.mode.currentMode.equals("ReallyWorld"));
        this.autoWaterDown = new BooleanSetting("Auto Water Down", false, () -> Jesus.mode.currentMode.equals("ReallyWorld"));
        upTp = new BooleanSetting("Auto Water Up", false, () -> Jesus.mode.currentMode.equals("ReallyWorld"));
        this.waterTicks = 0;
        this.addSettings(Jesus.mode, Jesus.speed, Jesus.NCPSpeed, Jesus.useTimer, this.timerSpeed, Jesus.motionUp, this.speedCheck, this.autoWaterDown, this.autoMotionStop, upTp);
    }

    @Override
    public void onDisable() {
        inWater = false;
        mc.player.speedInAir = 0.02f;
        Jesus.mc.timer.timerSpeed = 1.0f;
        if (Jesus.mode.currentMode.equals("ReallyWorld") && this.autoWaterDown.getCurrentValue()) {
            final EntityPlayerSP player = Jesus.mc.player;
            player.motionY -= 500.0;
        }
        this.waterTicks = 0;
        super.onDisable();
    }

    @EventTarget
    public void onLiquidBB(final EventLiquidSolid event) {
        if (Jesus.mode.currentMode.equals("ReallyWorld") || Jesus.mode.currentMode.equals("NCP")) {
        }
    }

    private boolean isWater() {
        final BlockPos bp1 = new BlockPos(Jesus.mc.player.posX - 0.5, Jesus.mc.player.posY - 0.5, Jesus.mc.player.posZ - 0.5);
        final BlockPos bp2 = new BlockPos(Jesus.mc.player.posX - 0.5, Jesus.mc.player.posY - 0.5, Jesus.mc.player.posZ + 0.5);
        final BlockPos bp3 = new BlockPos(Jesus.mc.player.posX + 0.5, Jesus.mc.player.posY - 0.5, Jesus.mc.player.posZ + 0.5);
        final BlockPos bp4 = new BlockPos(Jesus.mc.player.posX + 0.5, Jesus.mc.player.posY - 0.5, Jesus.mc.player.posZ - 0.5);
        return (Jesus.mc.player.world.getBlockState(bp1).getBlock() == Blocks.WATER && Jesus.mc.player.world.getBlockState(bp2).getBlock() == Blocks.WATER && Jesus.mc.player.world.getBlockState(bp3).getBlock() == Blocks.WATER && Jesus.mc.player.world.getBlockState(bp4).getBlock() == Blocks.WATER) || (Jesus.mc.player.world.getBlockState(bp1).getBlock() == Blocks.LAVA && Jesus.mc.player.world.getBlockState(bp2).getBlock() == Blocks.LAVA && Jesus.mc.player.world.getBlockState(bp3).getBlock() == Blocks.LAVA && Jesus.mc.player.world.getBlockState(bp4).getBlock() == Blocks.LAVA);
    }

    @EventTarget
    public void onPreMotion(final EventPreMotion event) {
        if (Jesus.mc.player.isPotionActive(MobEffects.SPEED) || !this.speedCheck.getCurrentValue()) {
            final BlockPos blockPos = new BlockPos(Jesus.mc.player.posX, Jesus.mc.player.posY - 0.1, Jesus.mc.player.posZ);
            final Block block = Jesus.mc.world.getBlockState(blockPos).getBlock();
            if (Jesus.useTimer.getCurrentValue()) {
                Jesus.mc.timer.timerSpeed = this.timerSpeed.getNumberValue();
            }
            if (Jesus.mode.currentMode.equalsIgnoreCase("Matrix")) {
                if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 0.008, mc.player.posZ)).getBlock() == Blocks.WATER
                        && !mc.player.onGround) {
                    boolean isUp = mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 0.03, mc.player.posZ)).getBlock() == Blocks.WATER;
                    mc.player.jumpMovementFactor = 0;
                    float yport = MovementUtils.getPlayerMotion() > 0.1 ? 0.02f : 0.032f;
                    mc.player.setVelocity(mc.player.motionX, mc.player.fallDistance < 3.5 ? (isUp ? yport : -yport) : -0.1, mc.player.motionZ);

                }
                if (mc.player.posY > (int) mc.player.posY + 0.89 && mc.player.posY <= (int) mc.player.posY + 1 || mc.player.fallDistance > 3.5) {
                    mc.player.posY = (int) mc.player.posY + 1 + 1E-45;
                    if (!mc.player.isInWater() && mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 0.1, mc.player.posZ)).getBlock() == Blocks.WATER) {

                        if (mc.player.collidedHorizontally) {
                            mc.player.motionY = 0.2;
                            mc.player.motionX *= 0f;
                            mc.player.motionZ *= 0f;
                        }
                        MovementUtils.setSpeed(MathHelper.clamp((float) (MovementUtils.getPlayerMotion() + 0.2f), 0.5f, 1.14f));
                    }

                }
                if ((mc.player.isInWater() || mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 0.15, mc.player.posZ)).getBlock() == Blocks.WATER)) {
                    mc.player.motionY = 0.16;
                    if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 2, mc.player.posZ)).getBlock() == Blocks.AIR) {
                        mc.player.motionY = 0.12;
                    }
                    if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 1, mc.player.posZ)).getBlock() == Blocks.AIR) {
                        mc.player.motionY = 0.18;
                    }
                }
            }
            else if (Jesus.mode.currentMode.equalsIgnoreCase("ReallyWorld")) {
                mc.player.speedInAir = 0.02f;
                if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY - 0.001f * speed.getNumberValue(), mc.player.posZ)).getBlock() == Block.getBlockById(9) && !mc.player.onGround) {
                    speed(9.0);
                    mc.player.jumpMovementFactor = 0.01f;
                }
                if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)).getBlock() == Block.getBlockById(9)) {
                    mc.player.motionX = 0.0;
                    mc.player.motionY = 0.04700000074505806;
                    mc.player.jumpMovementFactor = 0.01f;
                    mc.player.motionZ = 0.0;
                }
                if (upTp.getCurrentValue() && mc.player.posY < 63 && mc.world.getBlockState(new BlockPos(mc.player.posX, 62, mc.player.posZ)).getBlock() == Block.getBlockById(9) && mc.world.getBlockState(new BlockPos(mc.player.posX, 63, mc.player.posZ)).getBlock() == Blocks.AIR) {
                    if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 0.3, mc.player.posZ)).getBlock() == Block.getBlockById(9)) {
                        mc.player.setPositionAndUpdate(mc.player.posX, 62.8f + y, mc.player.posZ);
                    }
                } else {
                    if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 1, mc.player.posZ)).getBlock() == Block.getBlockById(9)) {
                        mc.player.motionY = 0.18;
                    }
                }
            } else if (mode.currentMode.equalsIgnoreCase("ReallyWorld2")) {
                BlockPos blockPos2;
                Block block2;
                if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)).getBlock() == Blocks.WATER || mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ)).getBlock() == Blocks.LAVA) {
                    boolean isUp = mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 0.0311, mc.player.posZ)).getBlock() == Blocks.WATER || mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 0.0311, mc.player.posZ)).getBlock() == Blocks.LAVA;


                    float yport = MovementUtils.getSpeed() > 0.1 ? 0.02f : 0.032f;
                    mc.player.setVelocity(mc.player.motionX * MovementUtils.getSpeed(),mc.player.fallDistance < 3.5 ? (isUp ? yport : -yport) : -0.114, mc.player.motionZ * MovementUtils.getSpeed());
                    if (mc.player.posY > (int)mc.player.posY + 0.89 && mc.player.posY <= (int)mc.player.posY + 1.0f) {
                        mc.player.posY = (int)mc.player.posY + 1.0f + 1E-45;
                        mc.player.fallDistance = -99999.0F;
                        if (!mc.player.isInWater()) {
                            mc.player.motionX = 0.0f;
                            mc.player.motionZ = 0.0f;
                            inWater = true;
                            MovementUtils.setSpeed(0.1f * speed.getNumberValue());
                        }

                    }

                }
                if ((mc.player.isInWater() || mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 0.15, mc.player.posZ)).getBlock() == Blocks.WATER)) {
                    mc.player.motionY = 0.16;
                    if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 2, mc.player.posZ)).getBlock() == Blocks.AIR) {
                        mc.player.motionY = 0.12;
                    }
                    if (upTp.getCurrentValue() && mc.player.posY < 63 && mc.world.getBlockState(new BlockPos(mc.player.posX, 62, mc.player.posZ)).getBlock() == Block.getBlockById(9) && mc.world.getBlockState(new BlockPos(mc.player.posX, 63, mc.player.posZ)).getBlock() == Blocks.AIR) {
                        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 0.3, mc.player.posZ)).getBlock() == Block.getBlockById(9)) {
                            mc.player.setPositionAndUpdate(mc.player.posX, 62.8f + y, mc.player.posZ);
                        }
                    } else {
                        if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 1, mc.player.posZ)).getBlock() == Block.getBlockById(9)) {
                            mc.player.motionY = 0.18;
                        }
                    }
                }
                if (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 1, mc.player.posZ)).getBlock() == Blocks.LAVA) {
                    mc.player.motionY = 0.18;
                }
            }
        }
    }

    public void speed(double speed) {
        Minecraft mc = Minecraft.getMinecraft();
        double forward = mc.player.movementInput.moveForward;
        double strafe = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            mc.player.motionX = 0.0;
            mc.player.motionZ = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe < 0.0) {
                    yaw += (float) (forward > 0.0 ? 45 : -45);
                } else if (strafe > 0.0) {
                    yaw += (float) (forward > 0.0 ? -45 : 45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
                mc.player.motionX = (forward * speed * Math.cos(Math.toRadians(yaw + 90.0)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0)));
                mc.player.motionZ = (forward * speed * Math.sin(Math.toRadians(yaw + 90.0)) + strafe * speed * Math.cos(Math.toRadians(yaw + 90.0)));
            }
        }
    }

    static {
        Jesus.mode = new ListSetting("Jesus Mode", "Matrix", () -> true, new String[] { "Matrix", "ReallyWorld","ReallyWorld2", "SunRise", "NCP" });
        Jesus.speed = new NumberSetting("Speed", 0.65f, 0.1f, 10.0f, 0.01f, () -> !Jesus.mode.currentMode.equals("NCP"));
        Jesus.NCPSpeed = new NumberSetting("NCP Speed", 0.25f, 0.01f, 0.5f, 0.01f, () -> Jesus.mode.currentMode.equals("NCP"));
        Jesus.useTimer = new BooleanSetting("Use Timer", false, () -> true);
    }
}
package incest.tusky.game.module.impl.Movement;

import incest.tusky.game.tuskevich;
import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.packet.EventSendPacket;
import incest.tusky.game.event.events.impl.player.EventPreMotion;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.ui.settings.impl.ListSetting;
import incest.tusky.game.utils.Helper;
import incest.tusky.game.utils.inventory.InventoryUtil;
import incest.tusky.game.utils.math.TimerHelper;
import incest.tusky.game.utils.movement.MovementUtils;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("all")
public class Speed extends Module {
    public static float ticks = 0;
    public TimerHelper timerHelper = new TimerHelper();
    public static ListSetting speedMode = new ListSetting("Speed Mode", "Matrix New", () -> true, "ReallyWorld2","ReallyWorld","Matrix New","Sunrise","Sunrise2");
    private float jumps = 0;

    public Speed() {
        super("Speed", "Быстрая скорость бега", ModuleCategory.Movement);
        addSettings(speedMode);

    }

    @EventTarget
    public void on(EventSendPacket event) {
        if (speedMode.currentMode.equals("ReallyWorld2") && isEnabled()) {
            if (event.getPacket() instanceof SPacketPlayerPosLook) {
            	ticks = 1;
            }
        }
        if (speedMode.currentMode.equals("Matrix Elytra")) {
            if (Helper.mc.player.isOnLadder() || Helper.mc.player.isInLiquid() || InventoryUtil.getSlotWithElytra() == -1) {
                return;
            }
            if (event.getPacket() instanceof CPacketPlayer) {
                CPacketPlayer cPacketPlayer = (CPacketPlayer) event.getPacket();
                cPacketPlayer.onGround = false;
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        String mode = speedMode.getOptions();
        if (mode.equalsIgnoreCase("Matrix Elytra")) {
            if (Helper.mc.player.isInWeb || Helper.mc.player.isOnLadder() || Helper.mc.player.isInLiquid() || Helper.mc.gameSettings.keyBindJump.isKeyDown() || InventoryUtil.getSlotWithElytra() == -1) {
                return;
            }
            if (Helper.mc.player.ticksExisted % 6 == 0) {
                InventoryUtil.disabler(InventoryUtil.getSlotWithElytra());
            }
            if (Helper.mc.world.getBlockState(new BlockPos(Helper.mc.player.posX, Helper.mc.player.posY - 0.9D, Helper.mc.player.posZ)).getBlock() != Blocks.AIR) {
                Helper.mc.player.motionX *= 1.8f;
                Helper.mc.player.motionZ *= 1.8f;
                MovementUtils.strafe();
            }

        } else if (mode.equalsIgnoreCase("NCPHop")) {
            if (MovementUtils.isMoving()) {
                if (Helper.mc.player.onGround) {
                    Helper.mc.player.jump();
                    Helper.mc.player.speedInAir = 0.0223f;
                }
                MovementUtils.strafe();
            } else {
                Helper.mc.player.motionX = 0.0;
                Helper.mc.player.motionZ = 0.0;
            }
        }
    }

    @EventTarget
    public void onPreMotion(EventPreMotion event) {
        String mode = speedMode.getOptions();
        if (mode.equalsIgnoreCase("Matrix")) {
            if (Helper.mc.player.isInWeb || Helper.mc.player.isOnLadder() || Helper.mc.player.isInLiquid()) {
                return;
            }
            if (Helper.mc.player.onGround && !Helper.mc.gameSettings.keyBindJump.pressed) {
                Helper.mc.player.jump();
                Helper.mc.timer.timerSpeed = 1.3f;
            }
            if (Helper.mc.world.getCollisionBoxes(Helper.mc.player, Helper.mc.player.getEntityBoundingBox().offset(0.D, -0.5D, 0.D).expand(-0.001D, 0.D, -0.001D)).isEmpty() && Helper.mc.player.onGround) {
                Helper.mc.player.jump();
            }
            if (Helper.mc.world.getBlockState(new BlockPos(Helper.mc.player.posX, Helper.mc.player.posY - 1.1D, Helper.mc.player.posZ)).getBlock() != Blocks.AIR) {
                if (Helper.mc.player.motionY == -0.4448259643949201D) {
                    event.setOnGround(false);
                    Helper.mc.player.motionX *= 2.D;
                    Helper.mc.player.motionZ *= 2.D;
                    MovementUtils.setMotion(MovementUtils.getSpeed() * 1.D + Math.sin(MovementUtils.getAllDirection()) * 0.005D);
                }
            }
        } else if (mode.equalsIgnoreCase("Matrix New")) {
            if (mc.player.onGround) {
                mc.player.jump();
                mc.player.speedInAir = 0.0201F;
                mc.timer.timerSpeed = 0.9F;
            }
            if (mc.player.fallDistance > 0.6 && mc.player.fallDistance < 1.3) {
                mc.player.speedInAir = 0.02F;
                mc.timer.timerSpeed = 2F;
            }
        } else if (mode.equalsIgnoreCase("Sunrise2")) {
            if (MovementUtils.isMoving()) {
            		 if (Helper.mc.player.onGround) {
                         mc.player.addVelocity(-Math.sin(MovementUtils.getAllDirection()) * 9.5 / 24.5, 0, Math.cos(MovementUtils.getAllDirection()) * 9.5 / 28.5);
                     } else if (mc.player.isInWater()) {
                         mc.player.addVelocity(-Math.sin(MovementUtils.getAllDirection()) * 8.5 / 24.5, mc.gameSettings.keyBindJump.pressed ? (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 4, mc.player.posZ)).getBlock() == Blocks.AIR) ? 0 : mc.gameSettings.keyBindSneak.pressed ? 0 : mc.player.isCollidedHorizontally ? 0.2 : 0.2 : mc.gameSettings.keyBindSneak.pressed ? -0.2f : 0, Math.cos(MovementUtils.getAllDirection()) * 8.5 / 45);
                     } else if (!mc.player.onGround) {
                         mc.player.addVelocity(-Math.sin(MovementUtils.getAllDirection()) * 0.11 / 24.5, 0, Math.cos(MovementUtils.getAllDirection()) * 0.11 / 28.5);
                     } else {
                         mc.player.addVelocity(-Math.sin(MovementUtils.getAllDirection()) * 0.005 * MovementUtils.getSpeed(), 0,
                                 Math.cos(MovementUtils.getAllDirection()) * 0.005 * MovementUtils.getSpeed());
                     }
                if (mc.player.isInWater() && (mc.world.getBlockState(new BlockPos(mc.player.posX, mc.player.posY + 2, mc.player.posZ)).getBlock() == Blocks.AIR) && mc.player.motionY > 0.5f) {
                    mc.player.motionY = 0;
                }
                MovementUtils.strafe(MovementUtils.getSpeed());
            }
            } else if (mode.equalsIgnoreCase("Sunrise")) {
            if (MovementUtils.isMoving()) {
                if (Helper.mc.player.onGround) {
                    Helper.mc.player.addVelocity(-Math.sin(MovementUtils.getAllDirection()) * 9.5D / 24.5D, 0, Math.cos(MovementUtils.getAllDirection()) * 9.5D / 24.5D);
                    MovementUtils.strafe();
                } else if (Helper.mc.player.isInWater()) {
                    Helper.mc.player.addVelocity(-Math.sin(MovementUtils.getAllDirection()) * 9.5D / 24.5D, 0, Math.cos(MovementUtils.getAllDirection()) * 9.5D / 24.5D);
                    MovementUtils.strafe();
                } else {
                    Helper.mc.player.addVelocity(-Math.sin(MovementUtils.getAllDirection()) * 0.005D * MovementUtils.getSpeed(), 0, Math.cos(MovementUtils.getAllDirection()) * 0.005 * MovementUtils.getSpeed());
                    MovementUtils.strafe();

                }
            }
        }else if (mode.equalsIgnoreCase("ReallyWorld")) {
        if (MovementUtils.isMoving()) {
            if (MovementUtils.getSpeed() < 0.215f && !mc.player.onGround) {
                MovementUtils.strafe(0.215f);
            }
            if (mc.player.onGround && MovementUtils.isMoving()) {
                mc.gameSettings.keyBindJump.pressed = false;
                mc.player.jump();
                if (!mc.player.isAirBorne) {
                    return;
                }
                if (ticks != 1)   mc.timer.timerSpeed = 1.25f;
                    if (MovementUtils.getSpeed() < 0.2177f) {
                        Helper.mc.player.jumpMovementFactor = 0.033f;
                        MovementUtils.strafe();
                        if (Math.abs(Helper.mc.player.movementInput.moveStrafe) < 0.1 && Helper.mc.gameSettings.keyBindForward.pressed) {
                            MovementUtils.strafe();
                        }
                        if (Helper.mc.player.onGround) {
                            MovementUtils.strafe();
                        }
                    }
            }else if (!MovementUtils.isMoving()) {
                mc.timer.timerSpeed = 1.00f;
                mc.player.motionX = 0.0;
                mc.player.motionZ = 0.0;
            }
        }

    } else if (mode.equalsIgnoreCase("ReallyWorld2")) {
            Helper.mc.timer.timerSpeed = 1.0f;

            if (!MovementUtils.isMoving() || Helper.mc.player.isInWater() || Helper.mc.player.isInLava() ||
                    Helper.mc.player.isOnLadder() || Helper.mc.player.isRiding()) { return;
            }
            if (Helper.mc.player.onGround) {
                Helper.mc.player.jump();
            }else {
            	if (mc.player.fallDistance <= 0.1) {
					mc.timer.timerSpeed = 1.4f;
				} else if (mc.player.fallDistance > 1.0) {
					mc.timer.timerSpeed = 0.94f;
				} else {
					mc.timer.timerSpeed = 1.0f;
				}
            }
        }else if (mode.equalsIgnoreCase("NCPYPort")) {
            if (MovementUtils.isMoving()) {
                Helper.mc.timer.timerSpeed = 1.05f;
                if (Helper.mc.player.onGround) {
                    Helper.mc.player.motionY = 0.4f;
                    if (!Helper.mc.player.isPotionActive(Potion.getPotionById(1))) {
                        MovementUtils.setSpeed(0.34f);
                    } else {
                        if (Helper.mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier() == 0) {
                            MovementUtils.setSpeed(0.49f);
                        }
                        if (Helper.mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier() == 1) {
                            MovementUtils.setSpeed(0.575f);
                        }
                        if (Helper.mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier() == 2) {
                            MovementUtils.setSpeed(0.73f);
                        }
                    }
                } else {
                    Helper.mc.player.motionY = -100.0;
                }
            }
        }

    }

    @Override
    public void onEnable() {
        if (speedMode.currentMode.equalsIgnoreCase("NCPHop")) {
            Helper.mc.timer.timerSpeed = 1.0865f;
        }
        ticks = 0;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        Helper.mc.player.speedInAir = 0.02f;
        Helper.mc.timer.timerSpeed = 1.0f;
        super.onDisable();
    }
}
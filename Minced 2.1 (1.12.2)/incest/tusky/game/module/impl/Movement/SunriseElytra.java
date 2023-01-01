package incest.tusky.game.module.impl.Movement;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventPreMotion;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.ListSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.utils.math.TimerHelper;
import incest.tusky.game.utils.movement.MovementUtils;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.play.client.CPacketEntityAction;

public class SunriseElytra extends Module {
    public boolean damage = false;
    public boolean flaging = false;
    public TimerHelper timerHelper = new TimerHelper();
    public static ListSetting flyMode = new ListSetting("Mode", "Motion", () -> true, "Fly", "Motion");
    public final NumberSetting speed = new NumberSetting("Speed", 0.3f, 0.1F, 1.4f, 0.1F, () -> flyMode.currentMode.equals("Fly"));
    public BooleanSetting boost = new BooleanSetting("Boost", "Boost", true, () -> flyMode.currentMode.equals("Motion"));

    public final NumberSetting speed1 = new NumberSetting("Speed", 0.3F, 0.1F, 0.4f, 0.1F, () -> boost.getCurrentValue());
    int eIndex = -1;

    private int boostTicks;
    private int lastSlot;

    boolean allowfly100;

    boolean jumped;
    boolean keybindfoward;


    boolean packetsended;
    public float ticks = 0;

    public SunriseElytra() {
        super("SunriseElytra", "Sunrise Elytra", ModuleCategory.Movement);
        addSettings(flyMode, boost, speed1, speed);
    }


    @EventTarget
    public void onPreUpdate(EventPreMotion event) {
        String mode = flyMode.getOptions();


        if (mode.equalsIgnoreCase("Fly")) {
            if (allowfly100) {
                mc.player.motionY += 0.005f;
                mc.player.capabilities.isFlying = true;
                mc.player.capabilities.setFlySpeed(speed.getNumberValue() / 5);
                MovementUtils.strafe(MovementUtils.getSpeed());
                if (mc.gameSettings.keyBindJump.pressed) {
                    mc.player.motionY = 0.25f;
                }
                if (mc.gameSettings.keyBindSneak.pressed) {
                    mc.player.motionY = -0.25f;
                }
            }

        } else if (mode.equalsIgnoreCase("Motion")) {
            if (allowfly100) {

                double mx = mc.player.motionX;
                double mz = mc.player.motionZ;
                double motion = Math.hypot(mx, mz) * 20;
                if (motion > 110) {
                    MovementUtils.setSpeed(5.0f);
                }
                mc.player.motionY += 0.005f;
                if (boost.getCurrentValue()) {
                    MovementUtils.setSpeed(MovementUtils.getSpeed() + speed1.getNumberValue());
                }
                if (!boost.getCurrentValue()) {
                    MovementUtils.setSpeed(0.8f);
                }
                mc.player.capabilities.isFlying = true;
                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.player.motionY = 0.3D;
                } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                    mc.player.motionY = -0.3D;
                }

                MovementUtils.strafe(MovementUtils.getSpeed());
            }
        }
        if (mc.player.onGround) {
            mc.player.jump();
        }
        if (mc.player.isAirBorne) {
            jumped = true;
        }
        if (mc.player.isElytraFlying()) {
            allowfly100 = true;
        }
        if (mc.player.isAirBorne) {
            mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        }
    }





    @Override
    public void onDisable() {
        super.onDisable();
        onswapdisable();
        mc.player.capabilities.isFlying = false;
        mc.player.capabilities.allowFlying = false;
        this.boostTicks = 0;
        allowfly100 = false;
        mc.player.capabilities.setFlySpeed(0.005f);
        jumped = false;
        keybindfoward = false;
        mc.player.capabilities.setFlySpeed(0.005f);
    }
    @Override
    public void onEnable() {
        super.onEnable();
        onswapenable();
        keybindfoward = false;
    }
    private int onswapdisable() {
        int eIndex = -1;

        for (int i = 0; i < 45; i++)
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ELYTRA && eIndex == -1) {
                mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, this.findarmor(), 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                return 0;
            }
        return eIndex;
    }
    private int onswapenable() {
        int eIndex = -1;
        int slot = this.findElytraSlot();
        this.lastSlot = this.findFreeSlot();
        for (int i = 0; i < 45; i++)
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ELYTRA && eIndex == -1) {
                mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, 6, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, 9, 1, ClickType.PICKUP, mc.player);
                return 0;
            }
        return eIndex;
    }
    private int findElytraSlot() {
        for (int i = 0; i < 45; ++i) {
            if (mc.player.inventoryContainer.getSlot(i).getStack() != null && mc.player.inventoryContainer.getSlot(i).getStack().getItem() == Items.ELYTRA) {
                return i;
            }
        }
        return -1;
    }
    private int findFreeSlot() {
        for (int i = 0; i < 45; ++i) {
            if (mc.player.inventoryContainer.getSlot(i).getStack() == null || mc.player.inventoryContainer.getSlot(i).getStack().getItem() == Items.field_190931_a) {
                return i;
            }
        }
        return -1;
    }
    private int findarmor() {
        for (int i = 0; i < 45; ++i) {
            if (mc.player.inventoryContainer.getSlot(i).getStack() != null && mc.player.inventoryContainer.getSlot(i).getStack().getUnlocalizedName().contains("chestplate")) {
                return i;
            }
        }
        return -1;
    }
}
package incest.tusky.game.module.impl.Movement;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.packet.EventSendPacket;
import incest.tusky.game.event.events.impl.player.EventMove;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.ui.settings.impl.ListSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.utils.Helper;
import incest.tusky.game.utils.inventory.InventoryUtil;
import incest.tusky.game.utils.movement.MovementUtils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.network.play.client.CPacketEntityAction;

public class Strafe extends Module {
    public ListSetting strafeMode = new ListSetting("Strafe Mode", "Matrix", () -> true, "Matrix");
   public Strafe() {
        super("Strafe", "Позволяет стрейфиться игроку в пвп", ModuleCategory.Movement);
        addSettings(strafeMode);
    }

    @EventTarget
    public void onPre(EventMove eventPreMotion) {
        if (strafeMode.currentMode.equalsIgnoreCase("Elytra")) {
            eventPreMotion.setCancelled(true);

            int emptySlot = InventoryUtil.getItemIndex(Item.getItemFromBlock(Blocks.AIR));
            if (emptySlot != -1 && Helper.mc.player.inventory.getItemStack().getItem() == Items.ELYTRA && InventoryUtil.getItemIndex(Items.ELYTRA) == -1) {
                Helper.mc.playerController.windowClick(0, emptySlot < 9 ? emptySlot + 36 : emptySlot, 1, ClickType.PICKUP, Helper.mc.player);
            }
            if (InventoryUtil.getItemIndex(Items.ELYTRA) == -1 || Helper.mc.player.otherCheck()) {
                return;
            }

            if (Helper.mc.player.inventory.getItemStack().getItem() instanceof ItemArmor && Helper.mc.player.inventory.armorItemInSlot(2).isEmpty()) {
                ItemArmor armor = (ItemArmor) Helper.mc.player.inventory.getItemStack().getItem();

                if (armor.armorType == EntityEquipmentSlot.CHEST) {
                    Helper.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, Helper.mc.player);
                }
            }
            if (Helper.mc.player.inventory.getItemStack().getItem() instanceof ItemArmor && !Helper.mc.player.inventory.armorItemInSlot(2).isEmpty()) {
                Helper.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, Helper.mc.player);
                int emptySlot2 = InventoryUtil.getItemIndex(Item.getItemFromBlock(Blocks.AIR));
                if (emptySlot2 != -1 && Helper.mc.player.inventory.getItemStack().getItem() == Items.ELYTRA && InventoryUtil.getItemIndex(Items.ELYTRA) == -1) {
                    Helper.mc.playerController.windowClick(0, emptySlot2 < 9 ? emptySlot2 + 36 : emptySlot2, 1, ClickType.PICKUP, Helper.mc.player);
                }
            }
            if (Helper.mc.player.ticksExisted % 25 == 0) {
                disabler();
            }
            if(!Helper.mc.player.onGround) {
                //     MovementUtils.setMotion((MovementUtils.getSpeed() * 1) + (strafeSpeed.getCurrentValue()));
                MovementUtils.strafe();
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate eventUpdate) {
        if (!isEnabled()) {
            return;
        }
        if (strafeMode.currentMode.equals("Matrix")) {
            if (MovementUtils.isMoving()) {
                Helper.mc.player.setSprinting(true);
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
            }
        }
    }

    private void disabler() {

        int elytra = InventoryUtil.getItemIndex(Items.ELYTRA);

        if (Helper.mc.player.inventory.getItemStack().getItem() != Items.ELYTRA)
            Helper.mc.playerController.windowClick(0, elytra < 9 ? elytra + 36 : elytra, 1, ClickType.PICKUP, Helper.mc.player);

        Helper.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, Helper.mc.player);
        Helper.mc.player.connection.sendPacket(new CPacketEntityAction(Helper.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        Helper.mc.player.connection.sendPacket(new CPacketEntityAction(Helper.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        Helper.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, Helper.mc.player);
        Helper.mc.playerController.windowClick(0, elytra < 9 ? elytra + 36 : elytra, 1, ClickType.PICKUP, Helper.mc.player);

    }

    @EventTarget
    public void on(EventSendPacket event) {
        if (strafeMode.currentMode.equals("Elytra")) {
            if (Helper.mc.player.isOnLadder() || Helper.mc.player.isInLiquid() || InventoryUtil.getSlotWithElytra() == -1) {
                return;

            }
        }
    }
}

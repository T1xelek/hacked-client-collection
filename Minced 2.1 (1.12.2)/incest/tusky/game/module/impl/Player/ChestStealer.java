package incest.tusky.game.module.impl.Player;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.Module;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.utils.Helper;
import incest.tusky.game.utils.math.TimerHelper;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.utils.movement.MovementUtils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.*;

public class ChestStealer
        extends Module {
    private final NumberSetting delay;
    private final BooleanSetting drop;
    private final BooleanSetting noMove;
    TimerHelper timer = new TimerHelper();
    TimerHelper timerClose = new TimerHelper();

    public ChestStealer() {
        super("ChestStealer", "Автоматически забирает ресурсы из сундука", ModuleCategory.Player);
        delay = new NumberSetting("Steal Speed", 10.0f, 0.0f, 100.0f, 1.0f, () -> true);
        drop = new BooleanSetting("Drop Items", "Выкидывает хлам", false, () -> true);
        noMove = new BooleanSetting("No Move Swap", false, () -> true);
        addSettings(delay, drop, noMove);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        float delay = this.delay.getCurrentValue() * 10.0f;
        if (Helper.mc.currentScreen instanceof GuiChest) {
            if (noMove.getCurrentValue() && MovementUtils.isMoving() && !Helper.mc.player.onGround) {
                return;
            }
            GuiChest chest = (GuiChest) Helper.mc.currentScreen;
            for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); ++index) {
                ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
                ContainerChest container = (ContainerChest) Helper.mc.player.openContainer;
                if (!isWhiteItem(stack) || !timerClose.hasReached(delay)) continue;
                if (!drop.getCurrentValue()) {
                    Helper.mc.playerController.windowClick(container.windowId, index, 0, ClickType.QUICK_MOVE, Helper.mc.player);
                } else {
                    Helper.mc.playerController.windowClick(container.windowId, index, 1, ClickType.THROW, Helper.mc.player);
                }
                timerClose.reset();
            }
            if (isEmpty(chest)) {
                Helper.mc.player.closeScreen();
            } else {
                timer.reset();
            }
        }
        if (Helper.mc.currentScreen == null) {
            timer.reset();
        }
    }

    public boolean isWhiteItem(ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemArmor || itemStack.getItem() instanceof ItemEnderPearl || itemStack.getItem() instanceof ItemSword || itemStack.getItem() instanceof ItemTool || itemStack.getItem() instanceof ItemFood || itemStack.getItem() instanceof ItemPotion || itemStack.getItem() instanceof ItemBlock || itemStack.getItem() instanceof ItemArrow || itemStack.getItem() instanceof ItemCompass;
    }

    private boolean isEmpty(GuiChest chest) {
        for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); ++index) {
            ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
            if (!isWhiteItem(stack)) continue;
            return false;
        }
        return true;
    }
}

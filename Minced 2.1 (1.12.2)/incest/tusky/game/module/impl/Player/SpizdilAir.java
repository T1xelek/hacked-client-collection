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
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemCompass;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

import org.apache.commons.lang3.RandomStringUtils;

public class SpizdilAir extends Module {
    public TimerHelper timerHelper = new TimerHelper();
    public SpizdilAir() {
        super("SpizdilAir", "Автоматически забирает все ресурсы с аирдропа", ModuleCategory.Player);
    }

    @EventTarget
    public void onUpdate(EventPreMotion event) {
        float delay = 0;
        if (mc.player.openContainer instanceof ContainerChest) {
            ContainerChest container = (ContainerChest) mc.player.openContainer;
            for (int index = 0; index < container.inventorySlots.size(); ++index) {
                if (container.getLowerChestInventory().getStackInSlot(index).getItem() != Item.getItemById(0) && timerHelper.hasReached((delay))) {
                    mc.playerController.windowClick(container.windowId, index, 0, ClickType.QUICK_MOVE, mc.player);
                    timerHelper.reset();
                    continue;
                }
            }
        }
    }

    public boolean isWhiteItem(ItemStack itemStack) {
        return (itemStack.getItem() instanceof ItemArmor || itemStack.getItem() instanceof ItemEnderPearl || itemStack.getItem() instanceof ItemSword || itemStack.getItem() instanceof ItemTool || itemStack.getItem() instanceof ItemFood || itemStack.getItem() instanceof ItemPotion || itemStack.getItem() instanceof ItemBlock || itemStack.getItem() instanceof ItemArrow || itemStack.getItem() instanceof ItemCompass);
    }

    private boolean isEmpty(Container container) {
        for (int index = 0; index < container.inventorySlots.size(); index++) {
            if (isWhiteItem(container.getSlot(index).getStack()))
                return false;
        }
        return true;
    }
}

package incest.tusky.game.module.impl.Player;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.input.EventMouse;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class MiddleClickPearl extends Module {

    public MiddleClickPearl() {
        super("Click Pearl","Позволяет кидать эндер-жемчуг с помощью колесика мыши", ModuleCategory.Player);
    }
    public static boolean s;

    @EventTarget
    public void onMouseEvent(EventMouse event) {
        if (event.getKey() == 2) {
        	s = true;
            for (int i = 0; i < 9; i++) {
                ItemStack itemStack = mc.player.inventory.getStackInSlot(i);
                if (itemStack.getItem() == Items.ENDER_PEARL) {
                    mc.player.connection.sendPacket(new CPacketHeldItemChange(i));
                    mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                    mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
                    s = false;
                }
            }
        }
    }

    @Override
    public void onDisable() {
    	s = false;
        mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
        super.onDisable();
    }
}

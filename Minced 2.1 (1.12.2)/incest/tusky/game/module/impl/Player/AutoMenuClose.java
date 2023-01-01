package incest.tusky.game.module.impl.Player;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventPreMotion;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.utils.Helper;
import net.minecraft.inventory.ContainerChest;

import org.lwjgl.input.Keyboard;

public class AutoMenuClose extends Module {
    public AutoMenuClose() {
        super("AutoMenuClose", "Скрывает менюшку риливорлда при заходе на сервер", ModuleCategory.Other);
    }

    @EventTarget
    public void onUpdate(EventPreMotion event) {
    	if (mc.player.openContainer instanceof ContainerChest) {
    		ContainerChest chest = (ContainerChest) mc.player.openContainer;
            String chestName = chest.getLowerChestInventory().getName();
            if (chestName.contains("Меню")) mc.player.closeScreen();
    	}
    }

    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }
}
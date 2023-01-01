package incest.tusky.game.module.impl.Player;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.utils.otherutils.gayutil.EventLight;
import net.minecraft.world.EnumSkyBlock;

public class AntiLagMachine extends Module {
    public AntiLagMachine() {
        super("AntiLagMachine", "", ModuleCategory.Player);
    }
    @EventTarget
    public void onWorldLight(EventLight event) {
        if (event.getEnumSkyBlock() == EnumSkyBlock.SKY) {
            event.cancel();
        }
    }

}
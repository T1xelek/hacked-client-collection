package incest.tusky.game.module.impl.Util;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.Module;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.utils.otherutils.gayutil.EventLight;
import net.minecraft.world.EnumSkyBlock;

public class MinecraftOptimizer extends Module {
    public static BooleanSetting entity;
    public MinecraftOptimizer() {
        super("Optimizer", "Кубы оптимайзер", ModuleCategory.Other);
        entity = new BooleanSetting("del entity", false, () -> true);
        addSettings(entity);
    }
    public BooleanSetting light = new BooleanSetting("Light", true);
    public BooleanSetting entities = new BooleanSetting("Entities", true);

    @EventTarget
    public void onWorldLight(EventLight event) {
        if (light.getCurrentValue()) {
            if (event.getEnumSkyBlock() == EnumSkyBlock.SKY) {
                event.cancel();
            }
            if (event.getEnumSkyBlock() == EnumSkyBlock.BLOCK) {
                event.cancel();
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {

    }

}

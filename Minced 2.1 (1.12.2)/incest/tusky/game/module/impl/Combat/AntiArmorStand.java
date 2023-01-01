package incest.tusky.game.module.impl.Combat;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.Module;
import net.minecraft.entity.Entity;
import incest.tusky.game.module.ModuleCategory;

public class AntiArmorStand extends Module {
    public AntiArmorStand() {
        super("AntiArmorStand", "Автоматически удаляет все армор-стенды с мира", ModuleCategory.Combat);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.player == null || mc.world == null) {
            return;
        }
        for (Entity entity : mc.world.loadedEntityList) {
            if (entity == null || !(entity instanceof net.minecraft.entity.item.EntityArmorStand))
                continue;
            mc.world.removeEntity(entity);
        }
    }
}

package incest.tusky.game.module.impl.Combat;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventPreMotion;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AntiBot extends Module {

    public AntiBot() {
        super("AntiBot", "Убирает бота сервера", ModuleCategory.Combat);
        addSettings();
    }
    public static List<Entity> isBotPlayer = new ArrayList<>();

    @EventTarget
    public void onEvent(EventPreMotion event) {
        if (event instanceof EventPreMotion) {
            for (Entity entity : mc.world.playerEntities) {
                if (!entity.getUniqueID().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + entity.getName()).getBytes(StandardCharsets.UTF_8))) && entity instanceof EntityOtherPlayerMP) {
                    isBotPlayer.add(entity);
                }
                if (!entity.getUniqueID().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + entity.getName()).getBytes(StandardCharsets.UTF_8))) && entity.isInvisible() && entity instanceof EntityOtherPlayerMP) {
                    mc.world.removeEntity(entity);
                }
            }
        }
    }
}
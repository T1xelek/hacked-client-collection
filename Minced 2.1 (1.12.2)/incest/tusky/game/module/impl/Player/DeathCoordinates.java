package incest.tusky.game.module.impl.Player;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.Module;
import incest.tusky.game.ui.notif.NotifModern;
import incest.tusky.game.ui.notif.NotifRender;
import incest.tusky.game.utils.Helper;
import incest.tusky.game.utils.other.ChatUtils;
import incest.tusky.game.module.ModuleCategory;
import net.minecraft.client.gui.GuiGameOver;

public class DeathCoordinates
        extends Module {
    public DeathCoordinates() {
        super("DeathCoords", "Показывает координаты вашей смерти", ModuleCategory.Player);
    }
    public static int x = 0;
    public static int y = 0;
    public static int z = 0;
    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (Helper.mc.player.getHealth() < 1.0f && Helper.mc.currentScreen instanceof GuiGameOver) {
            x = Helper.mc.player.getPosition().getX();
            y = Helper.mc.player.getPosition().getY();
            z = Helper.mc.player.getPosition().getZ();
            if (Helper.mc.player.deathTime < 1) {
                ChatUtils.addChatMessage("DeathCoords: X: " + x + " Y: " + y + " Z: " + z);
            }
        }
    }
}

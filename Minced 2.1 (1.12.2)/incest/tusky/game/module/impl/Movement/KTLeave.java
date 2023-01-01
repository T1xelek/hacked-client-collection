package incest.tusky.game.module.impl.Movement;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.utils.math.MathematicHelper;
import incest.tusky.game.utils.other.ChatUtils;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;

public class KTLeave extends Module
{

    public KTLeave() {
        super("KTLeave", "", ModuleCategory.Movement);
    }


    @EventTarget
    public void onUpdate(final EventUpdate event) {
        float endX = MathematicHelper.intRandom(7000, 15000);
        float endZ = MathematicHelper.intRandom(7000, 15000);
        float endY = 60;
            ChatUtils.addChatMessage("Телепортирую на заданные мне координаты " + endX + " " + endY + " " + endZ);
            if (mc.player.posX != endX && mc.player.posZ != endZ) {
                mc.player.connection.sendPacket(new CPacketPlayer.Position(endX, endY + 9, endZ, true));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(endX, endY, endZ, false));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(endX, endY + 9, endZ, true));
                mc.player.connection.sendPacket(new CPacketPlayer.Position(endX, endY, endZ, false));
                if (isEnabled())
                	this.toggle();
            } else {
            	if (isEnabled())
            	this.toggle();
            }
    }
}

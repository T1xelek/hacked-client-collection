package incest.tusky.game.module.impl.Combat;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.Event;
import incest.tusky.game.event.events.impl.packet.EventSendPacket;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;

public class SuperBow
        extends Module {
    private BooleanSetting bow;
    private BooleanSetting eggs;
    private BooleanSetting pearls;
    private BooleanSetting snowballs;
    private NumberSetting shotPower = new NumberSetting("Shot Power", 70.0f, 1.0f, 100.0f, 5.0f, () -> true);

    public SuperBow() {
        super("SuperBow", "", ModuleCategory.Combat);
        addSettings(shotPower);
    }

    @EventTarget
    public void onUpdate(Event event) {
        if (event instanceof EventSendPacket) {
            if (((EventSendPacket) event).getPacket() instanceof CPacketPlayerDigging) {
                CPacketPlayerDigging packet = (CPacketPlayerDigging) ((EventSendPacket) event).getPacket();
                if (packet.getAction() == CPacketPlayerDigging.Action.RELEASE_USE_ITEM
                        && mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBow) {
                    mc.player.connection
                            .sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SPRINTING));
                    for (int i = 0; i < shotPower.getNumberValue(); i++) {
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX,
                                mc.player.posY + 1.0E-10, mc.player.posZ, false));
                        mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX,
                                mc.player.posY - 1.0E-10, mc.player.posZ, true));
                    }
                }
            }
        }
    }
}


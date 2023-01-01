package incest.tusky.game.module.impl.Player;

import incest.tusky.game.tuskevich;
import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.packet.EventReceivePacket;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.friend.Friend;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.utils.Helper;
import incest.tusky.game.utils.math.TimerHelper;
import net.minecraft.network.play.server.SPacketChat;

@SuppressWarnings("all")

public class AutoTPAccept
        extends Module {
    private final BooleanSetting friendsOnly;
    private final NumberSetting delay;
    private final TimerHelper timerHelper = new TimerHelper();

    public AutoTPAccept() {
        super("AutoTPAccept", "Автоматически принимает телепорт от друзей", ModuleCategory.Player);
        friendsOnly = new BooleanSetting("Friends Only", false, () -> true);
        delay = new NumberSetting("Delay", 300.0f, 0.0f, 1000.0f, 100.0f, () -> true);
        addSettings(this.friendsOnly, delay);
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket e) {
        SPacketChat message = (SPacketChat) e.getPacket();
        if (message.getChatComponent().getFormattedText().contains("�����������������")) {
            if (this.friendsOnly.getCurrentValue()) {
                for (Friend friend : tuskevich.instance.friendManager.getFriends()) {
                    if (!message.getChatComponent().getFormattedText().contains(friend.getName()) || !this.timerHelper.hasReached(this.delay.getCurrentValue()))
                        continue;
                    Helper.mc.player.sendChatMessage("/tpaccept");
                    timerHelper.reset();
                }
            } else if (this.timerHelper.hasReached(this.delay.getCurrentValue())) {
                Helper.mc.player.sendChatMessage("/tpaccept");
                timerHelper.reset();
            }
        }
    }
}

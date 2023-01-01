package incest.tusky.game.module.impl.Player;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import incest.tusky.game.tuskevich;
import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.packet.EventReceivePacket;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.module.impl.Render.AirDropWay;
import incest.tusky.game.friend.Friend;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.utils.Helper;
import incest.tusky.game.utils.math.TimerHelper;
import incest.tusky.game.utils.movement.MovementUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketChat;

@SuppressWarnings("all")

public class RWHelper
        extends Module {
    private final BooleanSetting autoJoin;
    private final BooleanSetting airNotification;
    private final TimerHelper timerHelper = new TimerHelper();
    boolean a, b;
    public RWHelper() {
        super("RWHelper", "Выдает уведомление на компьютере о аир дропе", ModuleCategory.Other);
        autoJoin = new BooleanSetting("Kick Join", false, () -> true);
        airNotification = new BooleanSetting("Air Notification", false, () -> true);
        addSettings(this.autoJoin, airNotification);
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket e) {
        SPacketChat message = (SPacketChat) e.getPacket();
        if (message.getChatComponent().getFormattedText().contains("\u0412\u044b \u0431\u044b\u043b\u0438 \u043a\u0438\u043a\u043d\u0443\u0442\u044b")) {
        	if (autoJoin.getCurrentValue()) MovementUtils.rehub(200);
        	if (airNotification.getCurrentValue()) {
        		if (tuskevich.instance.featureManager.getFeature(AirDropWay.class).isEnabled()) tuskevich.instance.featureManager.getFeature(AirDropWay.class).toggle();

        		if (AirDropWay.xBoss != 0) {
        			a = true;
        			if (SystemTray.isSupported() && a && !b) {
        				b = true;
        	            SystemTray tray = SystemTray.getSystemTray();

        	            java.awt.Image image = Toolkit.getDefaultToolkit().getImage("assets/minecraft/icon32.png");
        	            TrayIcon trayIcon = new TrayIcon(image);
        	            try {
        					tray.add(trayIcon);
        				} catch (AWTException e2) {
        					e2.printStackTrace();
        				}
        	            trayIcon.displayMessage("RWHelper", "\u0410\u0438\u0440\u0414\u0440\u043e\u043f \u0430\u043a\u0442\u0438\u0432\u0435\u043d!",
        	                    TrayIcon.MessageType.INFO);
        	        }
        	    	
        		} else {
        			b = false;
        			a = false;
        		}
        	}
        }
    }
}

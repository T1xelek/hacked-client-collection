package incest.tusky.game.module.impl.Util;

import com.mojang.realmsclient.gui.ChatFormatting;

import incest.tusky.game.tuskevich;
import incest.tusky.game.drag.comp.impl.DragCoords;
import incest.tusky.game.drag.comp.impl.DragStaff;
import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.packet.EventReceivePacket;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.event.events.impl.render.EventRender2D;
import incest.tusky.game.friend.Friend;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.ui.notif.NotifModern;
import incest.tusky.game.ui.notif.NotifRender;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.utils.Helper;
import incest.tusky.game.utils.other.ChatUtils;
import incest.tusky.game.utils.render.RoundedUtil;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketPlayerListItem;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class StaffAlert extends Module {
    private boolean isJoined;
    ArrayList<NetworkPlayerInfo> data2 = new ArrayList<NetworkPlayerInfo>();
    public static ArrayList<NetworkPlayerInfo> staff = new ArrayList<NetworkPlayerInfo>();

    public StaffAlert() {
        super("StaffStatistic", "Позволяет видеть администрацию на сервере",ModuleCategory.Other);
        addSettings();
    }
    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        if (event.getPacket() instanceof SPacketPlayerListItem && ((SPacketPlayerListItem)event.getPacket()).getAction() == SPacketPlayerListItem.Action.UPDATE_LATENCY) {
            this.isJoined = true;
        }
        if (event.getPacket() instanceof SPacketPlayerListItem && ((SPacketPlayerListItem)event.getPacket()).getAction() == SPacketPlayerListItem.Action.REMOVE_PLAYER) {
            
        }
        SPacketChat message = (SPacketChat) event.getPacket();
        if (message.getChatComponent().getFormattedText().contains("moder")) {
        	String t = message.getChatComponent().getFormattedText();
        	t = t.split("MODER ")[1];
        	t = t.split(" ")[0];
        	
        }
    }
    public static float max;
    @EventTarget
    public void onUpdate(EventRender2D event) {
    	int y = 0;
    	DragStaff ds = (DragStaff) tuskevich.instance.draggableHUD.getDraggableComponentByClass(DragStaff.class);
        ds.setWidth(160);
        GlStateManager.pushMatrix();
        GL11.glTranslated(ds.getX(), ds.getY(), 1);
        RoundedUtil.drawRound(0,0, ds.getWidth(),ds.getHeight() -5,3, new Color(25,25,25));
    	for (NetworkPlayerInfo p : staff) {
             String name = p.getDisplayName().getFormattedText();
             for (NetworkPlayerInfo data : mc.player.connection.getPlayerInfoMap()) {
            	 if (!staff.contains(data)) {
            		 String server;
                     if (this.mc.isSingleplayer()) {
                         server = "1";
                     } else {
                         assert (this.mc.getCurrentServerData() != null);
                         server = this.mc.getCurrentServerData().serverIP.toLowerCase();
                     }
                    	 name = "[SPEC] " + p.getDisplayName().getFormattedText();
            	 }
             }
             for (NetworkPlayerInfo data : mc.player.connection.getPlayerInfoMap()) {
            	 if (data == p) {
            		 name = p.getDisplayName().getFormattedText();
            	 }
             }
             mc.Nunito14.drawString(name, 3, 15 + y,-1);
    		y+=10;
    	}
    	mc.Nunito16.drawCenteredString("Staff Board", ds.getWidth() / 2, 4,-1);
  	  	ds.setHeight(y + 20);
    	GlStateManager.popMatrix();
    }

    private boolean havePermissionFixed(String displayName) {
        return displayName.toLowerCase().contains("helper") || displayName.toLowerCase().contains("хелпер") || displayName.toLowerCase().contains("YT")
                || displayName.toLowerCase().contains("moder") || displayName.toLowerCase().contains("Модер") || displayName.toLowerCase().contains("Админ")
                || displayName.toLowerCase().contains("admin");
    }

    @EventTarget
    public void onEvent(EventUpdate event) {
    	for (NetworkPlayerInfo data : mc.player.connection.getPlayerInfoMap()) {
    		if (!data2.contains(data)) {
    			staff.remove(data);
    			data2.add(data);
    		}
    	}
    	for (NetworkPlayerInfo data : data2) {
    		if (mc.world == null || mc.player == null) staff.clear();
    		if (data == null || data.getDisplayName() == null) 
    			data.a = false;
    		if (data != null && data.getDisplayName() != null) {
                data.getDisplayName().getFormattedText();
                if (data.getGameProfile().getName() != null) {
                	String displayName = data.getDisplayName().getFormattedText();
                    boolean havePerm = havePermissionFixed(displayName);
                    if (havePerm && !data.a) {
                    	data.a = true;
                    	staff.remove(data);
                    	staff.remove(data);
                    	staff.remove(data);
                    	staff.add(data);
                    	System.out.println("test");
                    	ChatUtils.addChatMessage(ChatFormatting.WHITE + "Администратор " + ChatFormatting.RESET + data.getDisplayName().getUnformattedText() + ChatFormatting.WHITE + " зашёл на сервер");
                        isJoined = false;
                    } else {
                    //	staff.remove(data);
                    }
                }
            }
    	}
    }
}

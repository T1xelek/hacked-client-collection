package incest.tusky.game.ui.notif;

import incest.tusky.game.module.impl.Render.Notifications;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import incest.tusky.game.tuskevich;
import incest.tusky.game.ui.celestun4ik.guiscreencomponent;
import incest.tusky.game.utils.Helper;
import incest.tusky.game.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import incest.tusky.game.utils.render.RoundedUtil;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static incest.tusky.game.module.impl.Render.ModuleList.oneColor;

public final class NotifRender implements Helper {
    private static final List<Notif> NOTIFICATIONS = new CopyOnWriteArrayList<>();

    public static void queue(String title, String content, int second, NotifModern type) {
        NOTIFICATIONS.add(new Notif(title, content, type, second * 1000, Minecraft.getMinecraft().mntsb_15));
    }

    public static void publish(ScaledResolution sr) {
        if (tuskevich.instance.featureManager.getFeature(Notifications.class).isEnabled() && Notifications.notifMode.currentMode.equalsIgnoreCase("Rect") && !mc.gameSettings.showDebugInfo && mc.world != null && !(mc.currentScreen instanceof guiscreencomponent)) {
            if (!NOTIFICATIONS.isEmpty()) {
                int y = sr.getScaledHeight() - 40;
                double better;
                for (Notif notification : NOTIFICATIONS) {
                    better = Minecraft.getMinecraft().neverlose500_18.getStringWidth(notification.getTitle() + " " + notification.getContent());

                    if (!notification.getTimer().hasReached(notification.getTime() / 2)) {
                        notification.notificationTimeBarWidth = 360;
                    } else {
                        notification.notificationTimeBarWidth = MathHelper.EaseOutBack((float) notification.notificationTimeBarWidth, 0, (float) (4 * tuskevich.deltaTime()));
                    }

                    if (!notification.getTimer().hasReached(notification.getTime())) {
                        notification.x = sr.getScaledWidth() - 185;
                        notification.y = MathHelper.EaseOutBack((float) notification.y, (float) y, (float) (5 * tuskevich.deltaTime()));
                    } else {
                        notification.x = sr.getScaledWidth() - 185;
                        NOTIFICATIONS.remove(notification);
                    }
                    GlStateManager.pushMatrix();
                    GlStateManager.disableBlend();
                    RenderUtils.drawBlurredShadow((float) (notification.x + 45 + (110 - mc.tenacity.getStringWidth(notification.getContent()))), (float) (notification.y - 12), 10 +  Minecraft.getMinecraft().tenacity.getStringWidth(notification.getContent()) - 7 + 15, 10 + 5 +4, 6, new Color(25,25,25));

                    RoundedUtil.drawRound((float) (notification.x + 45 + (110 - mc.tenacity.getStringWidth(notification.getContent()))), (float) (notification.y - 12), 10 +  Minecraft.getMinecraft().tenacity.getStringWidth(notification.getContent()) - 7 + 15, 10 + 5 +4, 4, new Color(25,25,25));
                    Minecraft.getMinecraft().tenacity.drawString(TextFormatting.WHITE + notification.getContent(), (float) (notification.x +  55 + 3 + (105 - mc.tenacity.getStringWidth(notification.getContent()))), (float) (notification.y - 6), new Color(255,255,255, 255).getRGB());
                    GlStateManager.popMatrix();
                    y -= 35;
                }
            }
        }
    }
}
package incest.tusky.game.module.impl.Render;

import incest.tusky.game.drag.comp.impl.DragModuleList;
import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.render.EventBossBar;
import incest.tusky.game.event.events.impl.render.EventRender2D;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.tuskevich;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.ColorSetting;
import incest.tusky.game.ui.settings.impl.ListSetting;
import incest.tusky.game.utils.Helper;
import incest.tusky.game.utils.math.AnimationHelper;
import incest.tusky.game.utils.math.MathematicHelper;
import incest.tusky.game.utils.math.RotationHelper;
import incest.tusky.game.utils.render.ClientHelper;
import incest.tusky.game.utils.render.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;

import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class AirDropWay extends Module {
    public static float xBoss = 0;
    public static float zBoss = 0;
    String bossname = "";

    public AirDropWay() {
        super("AirDropWay", "Рисует стрелку до аир дропа", ModuleCategory.Visual);
    }

    @EventTarget
    public void onBossBar(EventBossBar event) {
        if (event.bossName.contains("x:")) {
            String bossBar = event.bossName;
            bossname = bossBar;
            	 String string = bossBar;
            	 String[] parts = string.split("z");
            	 String part1 = !event.bossName.contains("сек") ? parts[0] : parts[0].split("сек")[1]; // 004
            	 String part2 = parts[1]; // 034556
            	 xBoss = (part1.contains("-") ? -1 : 1) * Integer.parseInt(part1.replaceAll("§4", "").replaceAll("\\D+","").substring(1).substring(1).substring(1).substring(1));
            	 zBoss = (part2.contains("-") ? -1 : 1) * Integer.parseInt(part2.replaceAll("§4", "").replaceAll("\\D+","").substring(1));
        } else {
            xBoss = 1;
            zBoss = 1;
        }
    }

    public static float getAngle(BlockPos entity) {
        return (float) (RotationHelper.getRotations(entity.getX(), 0, entity.getZ())[0] - AnimationHelper.Interpolate(mc.player.rotationYaw, mc.player.prevRotationYaw, 1.0D));
    }
    
    @EventTarget
    public void Event2D(EventRender2D event) {
    	if (xBoss == 0 && zBoss == 0) {
    		return;
    	}
        if ((int) mc.player.getDistance(xBoss, mc.player.posY, zBoss) <= 3) {
            toggle();
        }
        if ((int) mc.player.getDistance(xBoss, mc.player.posY, zBoss) <= 10) {
            int x = event.getResolution().getScaledWidth() / 2;
            int y = event.getResolution().getScaledHeight() / 2;
        }
        if ((int) mc.player.getDistance(xBoss, mc.player.posY, zBoss) <= 10) {
            return;
        }

        int x2 = event.getResolution().getScaledWidth() / 2;
        int y2 = event.getResolution().getScaledHeight() / 2 + 5;
        int x = event.getResolution().getScaledWidth() / 2;
        int y = event.getResolution().getScaledHeight() / 2;
        GL11.glPushMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.resetColor();
        mc.mntsb_16.drawString(MathematicHelper.round((int) mc.player.getDistance(xBoss, mc.player.posY, zBoss), 0) + "m", (float) (x2 - mc.mntsb_16.getStringWidth(MathematicHelper.round((int) mc.player.getDistance(xBoss, mc.player.posY, zBoss), 0) + "m") / 2), (float) (y2), -1);
        mc.mntsb_16.drawString("Air Drop", (float) (x2 - mc.mntsb_16.getStringWidth("Air Drop") / 2), (float) (y2 + 10), -1);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y - 15, 0.0F);
        GL11.glRotatef(getAngle(new BlockPos(xBoss, 0, zBoss)) % 360.0F + 180.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef((float) (-x), (float) (-y - 30), 0.0F);
        RenderUtils.drawTriangle((float) x - 5, (float) (y + 50), 5.0F, 10.0F, ClientHelper.getClientColor(0,0,0).darker().getRGB(), ClientHelper.getClientColor(0,0,0).getRGB());
        GL11.glTranslatef((float) x, (float) y, 0.0F);
        GL11.glRotatef(-(getAngle(new BlockPos(xBoss, 0, zBoss)) % 360.0F + 180.0F), 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef((float) (-x), (float) (-y - 30), 0.0F);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
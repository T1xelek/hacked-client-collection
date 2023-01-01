package incest.tusky.game.utils.render;

import incest.tusky.game.utils.Helper;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import incest.tusky.game.module.impl.Render.ModuleList;
import incest.tusky.game.module.impl.Render.EntityESP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

import java.awt.*;

public class ClientHelper implements Helper {
    public static ServerData serverData;

    public static Color getESPColor() {
        Color color = Color.white;
        Color onecolor = new Color(EntityESP.colorEsp.getColorValue());
        double time = 10;
        String mode = EntityESP.colorMode.getOptions();
        float yDist = (float) 4;
        int yTotal = 0;
        for (int i = 0; i < 30; i++) {
            yTotal += Minecraft.getMinecraft().sfui18.getFontHeight() + 5;
        }
        if (mode.equalsIgnoreCase("Rainbow")) {
            color = ColorUtils.rainbow((int) (1 * 200 * 0.1f), 0.5f, 1.0f);
        } else if (mode.equalsIgnoreCase("Astolfo")) {
            color = ColorUtils.astolfoColors(5000.0F, 1);
        } else if (mode.equalsIgnoreCase("Custom")) {
            color = new Color(onecolor.getRGB());
        }
        return color;
    }
    public static Color getHealthColor() {
        Color color = Color.white;
        Color onecolor = new Color(EntityESP.healColor.getColorValue());
        double time = 10;
        String mode = EntityESP.healcolorMode.getOptions();
        float yDist = (float) 4;
        int yTotal = 0;
        for (int i = 0; i < 30; i++) {
            yTotal += Minecraft.getMinecraft().sfui18.getFontHeight() + 5;
        }
        if (mode.equalsIgnoreCase("Rainbow")) {
            color = ColorUtils.rainbow((int) (1 * 200 * 0.1f), 0.5f, 1.0f);
        } else if (mode.equalsIgnoreCase("Astolfo")) {
            color = ColorUtils.astolfoColors(5000.0F, 1);
        } else if (mode.equalsIgnoreCase("Custom")) {
            color = new Color(onecolor.getRGB());
        }
        return color;
    }

    public static Color getClientColor(float yStep, float yStepFull, int speed) {
        Color color = Color.white;
        Color onecolor = new Color(ModuleList.oneColor.getColorValue());
        double time = 10;
        float yDist = (float) 4;
        int yTotal = 0;
        for (int i = 0; i < 30; i++) {
            yTotal += Minecraft.getMinecraft().sfui18.getFontHeight() + 5;
        }
        return color;
    }

    public static Color getClientColor(float yStep, float astolfoastep, float yStepFull, int speed) {
        Color color = Color.white;
        Color onecolor = new Color(ModuleList.oneColor.getColorValue());
        double time = 11;
        int yTotal = 0;
        for (int i = 0; i < 30; i++) {
            yTotal += Minecraft.getMinecraft().sfui18.getFontHeight() + 5;
        }
        return color;
    }

   /* public static MCFontRenderer getFontRender() {
        Minecraft mc = Minecraft.getMinecraft();
        MCFontRenderer font = mc.mntsb_13;
        String mode = FeatureList.fontList.getOptions();
        switch (mode) {
            case "Myseo":
                font = mc.tahoma16;
                break;
            case "Tenacity":
                font = mc.tenacity;
                break;
            case "SF-UI":
                font = mc.sfui16;
                break;
            case "Montserrat":
                font = mc.mntsb_14;
                break;
        }
        return font;
    }*/
    public static void drawCircle2(final float x, final float y, float start, float end, final float radius, final int color, final int linewidth) {
        GlStateManager.color(0.0f, 0.0f, 0.0f, 0.0f);
        if (start > end) {
            final float endOffset = end;
            end = start;
            start = endOffset;
        }
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        RenderUtils.enableSmoothLine((float)linewidth);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glBegin(3);
        for (float i = end; i >= start; i -= 4.0f) {
            RenderUtils.glColor(color, 255);
            final float cos = (float)(Math.cos(i * 3.141592653589793 / 180.0) * radius * 1.0);
            final float sin = (float)(Math.sin(i * 3.141592653589793 / 180.0) * radius * 1.0);
            GL11.glVertex2f(x + cos, y + sin);
        }
        GL11.glEnd();
        RenderUtils.disableSmoothLine();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
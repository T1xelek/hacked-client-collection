package net.minecraft.client.gui;

import java.awt.*;
import java.io.IOException;

import com.mojang.realmsclient.gui.ChatFormatting;
import incest.tusky.game.files.FileManager;
import incest.tusky.game.tuskevich;
import incest.tusky.game.ui.FileHelper;
import incest.tusky.game.ui.altmanager.GuiAltManager;
import incest.tusky.game.ui.button.GuiMainMenuButton;

import incest.tusky.game.utils.math.AnimationHelper;
import incest.tusky.game.utils.math.animations.Animation;
import incest.tusky.game.utils.math.animations.impl.DecelerateAnimation;
import incest.tusky.game.utils.otherutils.gayutil.DrawHelper;
import incest.tusky.game.utils.otherutils.tenacity.TenacityColorUtil;
import incest.tusky.game.utils.particle.ParticleUtils;
import incest.tusky.game.utils.render.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class GuiMainMenu extends GuiScreen {
    private final long initTime;
    private int width;
    private int height;
    private animbackground backgroundShader;
    public static int bg = 0;

    public GuiMainMenu() {
        this.initTime = System.currentTimeMillis();
        try {
            this.backgroundShader = new animbackground("/1.fsh");
        }
        catch (IOException var2) {
            throw new IllegalStateException("Failed to load backgound shader", var2);
        }
    }

    public void initGui() {
        ScaledResolution sr = new ScaledResolution(this.mc);
        this.width = sr.getScaledWidth();
        this.height = sr.getScaledHeight();
        this.buttonList.add(new GuiMainMenuButton(0, this.width / 2 - 60, this.height / 2 - 10, 120, 10, "Синглплеер"));
        this.buttonList.add(new GuiMainMenuButton(1, this.width / 2 - 60, this.height / 2 + 10, 120, 10, "Мультиплеер"));
        this.buttonList.add(new GuiMainMenuButton(2, this.width / 2 - 60, this.height / 2 + 30, 120, 10, "Менеджер аккаунтов"));
        this.buttonList.add(new GuiMainMenuButton(3, this.width / 2 - 60, this.height / 2 + 50, 120, 10,"Настройки"));
        this.buttonList.add(new GuiMainMenuButton(4, this.width / 2 - 60, this.height / 2 + 70, 120, 10, "Выйти из игры"));

    }

    public static double deltaTime() {
        return Minecraft.getDebugFPS() > 0 ? (1.0000 / Minecraft.getDebugFPS()) : 1;
    }

    public static double animate(double target, double current, double speed) {
        boolean bl = target > current;
        if (speed < 0.0) {
            speed = 0.0;
        } else if (speed > 1.0) {
            speed = 1.0;
        }
        double dif = Math.max(target, current) - Math.min(target, current);
        double factor = dif * speed;
        if (factor < 0.1) {
            factor = 0.1;
        }
        current = bl ? (current += factor) : (current -= factor);
        return current;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(mc);


        this.backgroundShader.useShader(sr.getScaledWidth(), sr.getScaledHeight(), mouseX, mouseY, (float) (System.currentTimeMillis() - this.initTime) / 1500.0f);
        GL11.glBegin(7);
        GL11.glVertex2f(-1.0f, -1.0f);
        GL11.glVertex2f(-1.0f, 1.0f);
        GL11.glVertex2f(1.0f, 1.0f);
        GL11.glVertex2f(1.0f, -1.0f);
        GL11.glEnd();
        GL20.glUseProgram(0);

        GlStateManager.disableCull();
        StencilUtil.initStencilToWrite();
        RoundedUtil.drawRoundOutline(width / 2 - 77, height / 2 - 57, 153, 153, 5, 1, new Color(0,0,0,100), new Color(-1));
        StencilUtil.readStencilBuffer(1);
        GaussianBlur.renderBlur(10);
        StencilUtil.uninitStencilBuffer();

        RoundedUtil.drawRoundOutline(width / 2 - 80, height / 2 - 60, 160, 160, 5, 0.1f, new Color(0,0,0,50), new Color(-1));

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();

        this.mc.mntsb_30.drawCenteredStringWithShadow(ChatFormatting.WHITE + "MINCED CLIENT", (float)(this.width / 2), (float)(this.height / 2 - 45), -1);
        this.mc.mntsb_18.drawCenteredStringWithShadow("Version - 2.1", (float)(this.width / 2), (float)(this.height / 2 - 26), -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                this.mc.displayGuiScreen(new GuiWorldSelection(this));
                break;
            case 1:
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            case 2:
                this.mc.displayGuiScreen(new GuiAltManager());
                break;
            case 3:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            case 4:
                System.exit(0);
                tuskevich.instance.configManager.saveConfig("default");
                tuskevich.instance.fileManager.saveFiles();
            case 5:
                if (bg <= 2) {
                    bg++;
                    try {
                        this.backgroundShader = new animbackground("/" + GuiMainMenu.bg + ".fsh");
                    }
                    catch (IOException var2) {
                        throw new IllegalStateException("Failed to load backgound shader", var2);
                    }
                } else {
                    bg = 0;
                }
                break;
        }
        super.actionPerformed(button);
    }
}

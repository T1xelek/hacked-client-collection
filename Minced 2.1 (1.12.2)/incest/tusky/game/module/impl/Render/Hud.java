package incest.tusky.game.module.impl.Render;

import com.mojang.realmsclient.gui.ChatFormatting;
import incest.tusky.game.drag.comp.impl.*;
import incest.tusky.game.module.Module;
import incest.tusky.game.tuskevich;
import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.render.EventRender2D;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.module.impl.Util.NameProtect;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.ColorSetting;
import incest.tusky.game.ui.settings.impl.ListSetting;
import incest.tusky.game.utils.Helper;
import incest.tusky.game.utils.math.AnimationHelper;
import incest.tusky.game.utils.render.ClientHelper;
import incest.tusky.game.utils.render.GLUtils;
import incest.tusky.game.utils.render.RenderUtils;
import incest.tusky.game.utils.render.RoundedUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import optifine.CustomColors;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static incest.tusky.game.module.impl.Render.ModuleList.oneColor;

public class Hud extends Module {
    public static BooleanSetting waterMark = new BooleanSetting("WaterMark", true, () -> true);
    public static ListSetting waterMarkMode = new ListSetting("WaterMark Mode ", "Minced", () -> waterMark.getCurrentValue(), "Minced");
    public static BooleanSetting coords = new BooleanSetting("Coords", false, () -> false);
    public static BooleanSetting armorHUD = new BooleanSetting("Armor", true, () -> true);
    public static BooleanSetting potions = new BooleanSetting("Potion", true, () -> true);
    public Color color;
    public float scale = 2;

    public Hud() {
        super("ClientOverlay", "Визуальные компоненты клиента", ModuleCategory.Visual);
        addSettings(waterMark, waterMarkMode, potions, armorHUD);
    }

    private Color gradientColor1 = Color.WHITE, gradientColor2 = Color.WHITE;

    @EventTarget
    public void onRender(EventRender2D eventRender2D) {
        if (waterMark.getCurrentValue()) {
            switch (waterMarkMode.currentMode) {
                case "Minced": {
                    String gay = "MINCED" + " 2.1 | "  + mc.player.getName() +" | " + " fps: " + Minecraft.getDebugFPS();
                    String coords = "Coords:  " + mc.player.getPosition().getX() + ", " + mc.player.getPosition().getY() + ", " + mc.player.getPosition().getZ();
                    RoundedUtil.drawRound(5, 5f,  mc.Nunito15.getStringWidth(gay) + 7, 12, 2.5f, new Color(25,25,25));
                    mc.Nunito15.drawString(gay, 8, 8.5f, new Color(255,255,255).getRGB());
                    RoundedUtil.drawRound(5, 22,  mc.Nunito15.getStringWidth(coords) + 3, 12, 2.5f, new Color(25,25,25));
                    mc.Nunito15.drawString(coords, 6.5f, 25.5f,new Color(255,255,255).getRGB());

                    GLUtils.INSTANCE.rescaleMC();
                    break;
                }
            }

        }
        if (armorHUD.getCurrentValue()) {
            DragArmor das = (DragArmor) tuskevich.instance.draggableHUD.getDraggableComponentByClass(DragArmor.class);
            das.setWidth(100);
            das.setHeight(30);
            GlStateManager.pushMatrix();
            GlStateManager.enableTexture2D();
            int i = das.getX();
            int y = das.getY();
            int count = 0;
            for (ItemStack is : Helper.mc.player.inventory.armorInventory) {
                ++count;
                int x = i - 90 + (9 - count) * 20 + 2;
                GlStateManager.enableDepth();
                Helper.mc.getRenderItem().zLevel = 200.0f;
                Helper.mc.getRenderItem().renderItemAndEffectIntoGUI(is, x, y);
                Helper.mc.getRenderItem().renderItemOverlayIntoGUI(Helper.mc.tenacity, is, x, y, "");
                Helper.mc.getRenderItem().zLevel = 0.0f;
                GlStateManager.enableTexture2D();
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
            }
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
            GlStateManager.popMatrix();
        }
        if (coords.getCurrentValue()) {
            DragCoords dci = (DragCoords) tuskevich.instance.draggableHUD.getDraggableComponentByClass(DragCoords.class);
            dci.setWidth(90);
            dci.setHeight(25);

            String xCoord = "" + Math.round(Helper.mc.player.posX);
            String yCoord = "" + Math.round(Helper.mc.player.posY);
            String zCoord = "" + Math.round(Helper.mc.player.posZ);
            String fps = "" + Math.round(Minecraft.getDebugFPS());
            String bps = "" + calculateBPS();
            String ping = "" + Objects.requireNonNull(mc.getConnection()).getPlayerInfo(mc.player.getUniqueID()).getResponseTime();
            mc.mntsb_16.drawStringWithShadow("BPS: ", 2, 800, -1);
            mc.mntsb_16.drawStringWithShadow(ChatFormatting.GRAY + bps, getX() + 22, getY()  + 3, -1);
            mc.mntsb_16.drawStringWithShadow("FPS: ", getX(), getY()  - 5, -1);
            mc.mntsb_16.drawStringWithShadow(ChatFormatting.GRAY + fps, getX() + 22, getY() -5, -1);
            mc.mntsb_16.drawStringWithShadow("XYZ: ", getX(), getY()  + 11, -1);
            mc.mntsb_16.drawStringWithShadow(ChatFormatting.GRAY + xCoord, getX() + 22, getY() + 11, -1);
            mc.mntsb_16.drawStringWithShadow(ChatFormatting.GRAY + yCoord, getX() + 47 + Helper.mc.mntsb_15.getStringWidth(xCoord) - 17, getY()+ 11, -1);
            mc.mntsb_16.drawStringWithShadow(ChatFormatting.GRAY + zCoord, getX() + 75 + Helper.mc.mntsb_15.getStringWidth(xCoord) - 23 + Helper.mc.mntsb_15.getStringWidth(yCoord) - 17, getY() + 11, -1);
        }

        if (potions.getCurrentValue()) {
            DragPotion dph = (DragPotion) tuskevich.instance.draggableHUD.getDraggableComponentByClass(DragPotion.class);
            dph.setWidth(100);
            dph.setHeight(150);
            GLUtils.INSTANCE.rescale(scale);
            int x = 21;
            int y = 14;
            int yOffset = 16;
            Collection<PotionEffect> collection = Helper.mc.player.getActivePotionEffects();
            {
                GlStateManager.color(1F, 1F, 1F, 1F);
                GlStateManager.disableLighting();
                int listOffset = 23;
                if (collection.size() > 5) {
                    listOffset = 132 / (collection.size() - 1);
                }
                List<PotionEffect> potions = new ArrayList<>(Helper.mc.player.getActivePotionEffects());
                potions.sort(Comparator.comparingDouble(effect -> -Helper.mc.mntsb_14.getStringWidth((Objects.requireNonNull(Potion.getPotionById(CustomColors.getPotionId(effect.getEffectName()))).getName()))));

                for (PotionEffect potion : potions) {

                    Potion effect = Potion.getPotionById(CustomColors.getPotionId(potion.getEffectName()));

                    String potionPower = I18n.format(effect.getName());
                    if (potion.getAmplifier() == 1) {
                        potionPower = potionPower + " " + I18n.format("enchantment.level.2");
                    } else if (potion.getAmplifier() == 2) {
                        potionPower = potionPower + " " + I18n.format("enchantment.level.3");
                    } else if (potion.getAmplifier() == 3) {
                        potionPower = potionPower + " " + I18n.format("enchantment.level.4");
                    }

                    if (effect.hasStatusIcon()) {
                        GlStateManager.color(1F, 1F, 1F, 1F);
                        Helper.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
                        int statusIconIndex = effect.getStatusIconIndex();
                        new Gui().drawTexturedModalRect((float) ((dph.getX() + x) - 20), (dph.getY() + yOffset) - y - 4, statusIconIndex % 8 * 18, 198 + statusIconIndex / 8 * 18, 18, 18);
                    }
                    int potionColor = -1;
                    if ((potion.getDuration() < 200)) {
                        potionColor = new Color(215, 59, 59).getRGB();
                    } else if (potion.getDuration() < 400) {
                        potionColor = new Color(231, 143, 32).getRGB();
                    } else if (potion.getDuration() > 400) {
                        potionColor = new Color(172, 171, 171).getRGB();
                    }
                    Helper.mc.mntsb_15.drawStringWithShadow(ChatFormatting.WHITE + potionPower, dph.getX() + x, (dph.getY() + yOffset) - y, -1);
                    Helper.mc.mntsb_15.drawStringWithShadow(Potion.getDurationString(potion), dph.getX() + x, (dph.getY() + yOffset + 10) - y, potionColor);
                    yOffset += listOffset;
                }
                GLUtils.INSTANCE.rescaleMC();
            }

        }
    }

    private double calculateBPS() {
        double bps = (Math.hypot(Helper.mc.player.posX - Helper.mc.player.prevPosX, Helper.mc.player.posZ - Helper.mc.player.prevPosZ) * Helper.mc.timer.timerSpeed) * 20;
        return Math.round(bps * 100.0) / 100.0;
    }

    public float getX2() {
        DragUserInfo dci = (DragUserInfo) tuskevich.instance.draggableHUD.getDraggableComponentByClass(DragUserInfo.class);

        return dci.getX();
    }

    public float getY2() {
        DragUserInfo dci = (DragUserInfo) tuskevich.instance.draggableHUD.getDraggableComponentByClass(DragUserInfo.class);

        return dci.getY() + 10;
    }

    public float getY3() {
        DragWaterMark dwm = (DragWaterMark) tuskevich.instance.draggableHUD.getDraggableComponentByClass(DragWaterMark.class);

        return dwm.getY() - 5;
    }

    public float getX3() {
        DragWaterMark dwm = (DragWaterMark) tuskevich.instance.draggableHUD.getDraggableComponentByClass(DragWaterMark.class);

        return dwm.getX() - 5;
    }

    public float getX() {
        DragCoords dci = (DragCoords) tuskevich.instance.draggableHUD.getDraggableComponentByClass(DragCoords.class);

        return dci.getX();
    }

    public float getY() {
        DragCoords dci = (DragCoords) tuskevich.instance.draggableHUD.getDraggableComponentByClass(DragCoords.class);

        return dci.getY() + 15;
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }
}

package incest.tusky.game.ui.ClickGui.component.impl;

import incest.tusky.game.tuskevich;
import incest.tusky.game.ui.ClickGui.component.Component;
import incest.tusky.game.ui.ClickGui.component.PropertyComponent;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.module.impl.Render.ClickGUI;
import incest.tusky.game.ui.settings.Setting;
import incest.tusky.game.utils.math.AnimationHelper;
import incest.tusky.game.utils.math.MathematicHelper;
import incest.tusky.game.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import incest.tusky.game.utils.render.RoundedUtil;

import java.awt.*;

import static incest.tusky.game.module.impl.Render.ModuleList.oneColor;


public class NumberSettingComponent extends incest.tusky.game.ui.ClickGui.component.Component implements PropertyComponent {

    public NumberSetting numberSetting;
    public float currentValueAnimate = 0f;
    private boolean sliding;
    Minecraft mc = Minecraft.getMinecraft();
    public NumberSettingComponent(Component parent, NumberSetting numberSetting, int x, int y, int width, int height) {
        super(parent, numberSetting.getName(), x, y, width, height);
        this.numberSetting = numberSetting;
    }

    @Override
    public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        super.drawComponent(scaledResolution, mouseX, mouseY);
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        GlStateManager.pushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtils.scissorRect(0, 25.5f, sr.getScaledWidth(), 239);
        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();
        double min = numberSetting.getMinValue();
        double max = numberSetting.getMaxValue();
        boolean hovered = isHovered(mouseX, mouseY);

        if (this.sliding) {
            numberSetting.setValueNumber((float) MathematicHelper.round((double) (mouseX - x + 20) * (max - min) / (double) width + min, numberSetting.getIncrement()));
            if (numberSetting.getNumberValue() > max) {
                numberSetting.setValueNumber((float) max);
            } else if (numberSetting.getNumberValue() < min) {
                numberSetting.setValueNumber((float) min);
            }
        }

        float amountWidth = (float) (((numberSetting.getNumberValue()) - (min)) / (max - (min)));
        Color onecolor = new Color(ClickGUI.color.getColorValue());
        String valueString = "";

        NumberSetting.NumberType numberType = numberSetting.getType();


        switch (numberType) {
            case PERCENTAGE:
                valueString += '%';
                break;
            case MS:
                valueString += "ms";
                break;
            case DISTANCE:
                valueString += 'm';
            case SIZE:
                valueString += "SIZE";
            case APS:
                valueString += "APS";
                break;
            default:
                valueString = "";
        }
        currentValueAnimate = AnimationHelper.animation(currentValueAnimate, amountWidth, 0);
        float optionValue = (float) MathematicHelper.round(numberSetting.getNumberValue(), numberSetting.getIncrement());


        RenderUtils.drawRect(x + 13, y + 13.5, x + 5 + 1 * (width - 30), y + 15F, 0);

        mc.mntsb_13.drawString("" + String.format("%.1f", Float.valueOf("" + min)), x - 1, y + height- 11, tuskevich.instance.featureManager.getFeature(parent.getName()).isEnabled() ? -1 : 0);

        mc.mntsb_13.drawString("" + String.format("%.1f", Float.valueOf("" + max)), x + width - mc.mntsb_13.getStringWidth("" + String.format("%.1f", Float.valueOf("" + max))) - 4, y + height- 11, tuskevich.instance.featureManager.getFeature(parent.getName()).isEnabled() ? -1 : 0);

        RenderUtils.drawGradientSideways(x + 13, y + 13.5, x + 5 + 1 * (width - 30), y + 15F, tuskevich.instance.featureManager.getFeature(parent.getName()).isEnabled() ? Color.WHITE.getRGB() : Color.BLACK.getRGB(), tuskevich.instance.featureManager.getFeature(parent.getName()).isEnabled() ? Color.WHITE.getRGB() : Color.BLACK.getRGB());

        RenderUtils.drawGradientSideways(x + 13, y + 13.5, x + 5 + currentValueAnimate * (width - 30), y + 15F, tuskevich.instance.featureManager.getFeature(parent.getName()).isEnabled() ? Color.WHITE.getRGB() : new Color(ClickGUI.color.getColorValue()).getRGB(), tuskevich.instance.featureManager.getFeature(parent.getName()).isEnabled() ? Color.WHITE.getRGB() : new Color(ClickGUI.color.getColorValue()).darker().getRGB());

        RenderUtils.drawRect(x, y, x, y, tuskevich.instance.featureManager.getFeature(parent.getName()).isEnabled() ? Color.WHITE.getRGB() : new Color(ClickGUI.color.getColorValue()).darker().getRGB());

        RenderUtils.drawBlurredShadow((int) (x + 3 + currentValueAnimate * (width - 32)), (int) (y + 11.7f), 5, 5, 5, tuskevich.instance.featureManager.getFeature(parent.getName()).isEnabled() ? Color.WHITE : new Color(ClickGUI.color.getColorValue()).darker());

        RoundedUtil.drawRound((int) (x + 3 + currentValueAnimate * (width - 32)), (int) (y + 11.7f), 5, 5, 2f, tuskevich.instance.featureManager.getFeature(parent.getName()).isEnabled() ? Color.WHITE : new Color(ClickGUI.color.getColorValue()).darker());

        RoundedUtil.drawRound((int) (x + 4 + currentValueAnimate * (width - 32)), (int) (y + 12.7f), 3, 3, 1, tuskevich.instance.featureManager.getFeature(parent.getName()).isEnabled() ? new Color(ClickGUI.color.getColorValue()).darker() : Color.WHITE);

        mc.mntsb_15.drawString(numberSetting.getName(), x - 1, y + height / 2.5F - 7F, tuskevich.instance.featureManager.getFeature(parent.getName()).isEnabled() ? -1 : 0);
        mc.mntsb_14.drawString(optionValue + " " + valueString, x + currentValueAnimate * (width - (mc.mntsb_14.getStringWidth(optionValue + " " + valueString)/ 2) - 30), y + height / 2.5F - 4F + 14, tuskevich.instance.featureManager.getFeature(parent.getName()).isEnabled() ? -1 : 0);

        if (hovered) {
            if (numberSetting.getDesc() != null) {
                RenderUtils.drawSmoothRect(x + width + 20, y + height / 1.5F + 4.5F, x + width + 25 + mc.rubik_18.getStringWidth(numberSetting.getDesc()), y + 5.5F, 0);
                mc.mntsb_15.drawString(numberSetting.getDesc(), x + width + 22, y + height / 1.35F - 5F, -1);
            }
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GlStateManager.popMatrix();
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int button) {
        if (!sliding && button == 0 && isHovered(mouseX, mouseY)) {
            sliding = true;
        }
    }

    @Override
    public void onMouseRelease(int button) {
        this.sliding = false;
    }

    @Override
    public Setting getSetting() {
        return numberSetting;
    }
}

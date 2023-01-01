package incest.tusky.game.ui.celestun4ik.component.impl;

import incest.tusky.game.tuskevich;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.module.impl.Render.ClickGUI;
import incest.tusky.game.ui.celestun4ik.component.Component;
import incest.tusky.game.ui.celestun4ik.component.PropertyComponent;
import incest.tusky.game.ui.settings.Setting;
import incest.tusky.game.utils.math.AnimationHelper;
import incest.tusky.game.utils.math.MathematicHelper;
import incest.tusky.game.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import incest.tusky.game.utils.render.RoundedUtil;

import static incest.tusky.game.module.impl.Render.ModuleList.oneColor;

import java.awt.*;


public class NumberSettingComponent extends Component implements PropertyComponent {

    public NumberSetting numberSetting;
    public float currentValueAnimate = 0f;
    public float anim = 0f;
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
        int e = 10;
        int x = getX() + e;
        int y = getY();
        int width = getWidth() - e;
        int height = getHeight();
        double min = numberSetting.getMinValue();
        double max = numberSetting.getMaxValue();
        double max2 = numberSetting.getMaxValue() * 1.5f;
        boolean hovered = isHovered(mouseX, mouseY);
        double selected = 0;
        if (this.sliding) {
            numberSetting.setValueNumber((float) MathematicHelper.round((double) (mouseX - x) * (max2 - min) / (double) getWidth() + min, numberSetting.getIncrement()));
            if (numberSetting.getNumberValue() > max) {
                numberSetting.setValueNumber((float) max);
            } else if (numberSetting.getNumberValue() < min) {
                numberSetting.setValueNumber((float) min);
            }
        }

        float amountWidth = (float) (((numberSetting.getNumberValue()) - (min)) / (max - (min)));

        Color onecolor = new Color(oneColor.getColorValue());
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
        anim = AnimationHelper.animation(anim, hovered ? 1 : 0, 0);
        float optionValue = (float) MathematicHelper.round(numberSetting.getNumberValue(), numberSetting.getIncrement());

        boolean soeo = true;
        RenderUtils.drawRect(x + 13 - e, y + 13.5, x + 5 + 1 * (width - 30), y + 15F, 0);

        mc.mntsb_13.drawString("" + String.format("%.1f", Float.valueOf("" + min)), x - 1 - e, y + height- 11 + 7, new Color(220,220,220).getRGB());

        mc.mntsb_13.drawString("" + String.format("%.1f", Float.valueOf("" + max)), x + width - mc.mntsb_13.getStringWidth("" + String.format("%.1f", Float.valueOf("" + max))) - 4, y + height- 11 + 7, new Color(220,220,220).getRGB());

        RenderUtils.drawGradientSideways(x - e + 13, y + 13.5, x + 5 + 5 + 1 * (width - 30), y + 15F, soeo ? Color.WHITE.getRGB() : Color.BLACK.getRGB(), soeo ? Color.WHITE.getRGB() : Color.BLACK.getRGB());

        RenderUtils.drawGradientSideways(x - e + 13, y + 13.5, x + 5 + currentValueAnimate * (width - 30 + 5), y + 15F, oneColor.getColorValueColor().brighter().getRGB(), oneColor.getColorValueColor().getRGB());

        RenderUtils.drawRect(x - e, y, x, y, soeo ? Color.WHITE.getRGB() : new Color(oneColor.getColorValue()).darker().getRGB());

        RenderUtils.drawBlurredShadow((int) (x + 3 + currentValueAnimate * (width - 32 + 5)) + 1 - anim, (int) (y + 11.7f) + 1 - anim, 5 * anim, 5 * anim, 5, soeo ? Color.WHITE : new Color(oneColor.getColorValue()).darker());

        RoundedUtil.drawRound((int) (x + 3 + currentValueAnimate * (width - 32 + 5)) + 1 - anim, (int) (y + 11.5f) + 2 - anim * 2, 5 * anim, 5 * anim, 2f * anim, soeo ? Color.WHITE : new Color(oneColor.getColorValue()).darker());

        //RoundedUtil.drawRound((int) (x + 4 + currentValueAnimate * (width - 32 + 5)), (int) (y + 12.7f), 3, 3, 1, soeo ? new Color(ClickGUI.color.getColorValue()).darker() : Color.WHITE);

        mc.Nunito14.drawString(numberSetting.getName() + ":", x - e - 1, y + height / 2.5F - 6F, new Color(220,220,220).getRGB());
        mc.Nunito14.drawString(optionValue + " " + valueString, x + 7 + (width - 30) / 2 - (mc.mntsb_14.getStringWidth(optionValue + " " + valueString)/ 2), y + height / 2.5F - 4F + 14, new Color(250,250,250).getRGB());

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

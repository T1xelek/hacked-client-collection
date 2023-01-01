package incest.tusky.game.ui.celestun4ik.component.impl;

import incest.tusky.game.tuskevich;
import incest.tusky.game.ui.celestun4ik.component.Component;
import incest.tusky.game.ui.celestun4ik.component.PropertyComponent;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.module.impl.Render.ClickGUI;
import incest.tusky.game.ui.settings.Setting;
import incest.tusky.game.utils.math.AnimationHelper;
import incest.tusky.game.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import incest.tusky.game.utils.render.RoundedUtil;

import static incest.tusky.game.module.impl.Render.ModuleList.oneColor;

import java.awt.*;


public class BooleanSettingComponent extends Component implements PropertyComponent {
    public float textHoverAnimate = 0f;
    public float leftRectAnimation = 0;
    public float rightRectAnimation = 0;
    public BooleanSetting booleanSetting;
    Minecraft mc = Minecraft.getMinecraft();

    public BooleanSettingComponent(Component parent, BooleanSetting booleanSetting, int x, int y, int width, int height) {
        super(parent, booleanSetting.getName(), x, y, width, height);
        this.booleanSetting = booleanSetting;
    }

    @Override
    public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        if (booleanSetting.isVisible()) {
            ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
            GlStateManager.pushMatrix();
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            RenderUtils.scissorRect(0, 25.5f, sr.getScaledWidth(), 239);
            int x = getX();
            int y = getY();
            int width = getWidth();
            int height = getHeight();
            int middleHeight = getHeight() / 2;
            boolean hovered = isHovered(mouseX, mouseY);
            mc.Nunito14.drawString(getName(), x - 2, y + middleHeight, new Color(220,220,220).getRGB());
            textHoverAnimate = AnimationHelper.animation(textHoverAnimate, hovered ? 2.3f : 2, 0);
            leftRectAnimation = AnimationHelper.animation(leftRectAnimation, booleanSetting.getCurrentValue() ? 4 : 0, 0);
            rightRectAnimation = 12;
            RoundedUtil.drawRound(x + width + 3 - 15, y + 1 + 2 + 7 , x + width - (x + width) + 8.5f, y + height - 7 - 4.5f - (y + 7.5f)+ 0.5f + 2, 4, Color.WHITE);

            RoundedUtil.drawRound(x + width + 3 - 15 + 4 - leftRectAnimation, y + 1 + 2 + 7 + 4 - leftRectAnimation, 8.5f * leftRectAnimation / 4, (y + height - 7 - 4.5f - (y + 7.5f)+ 0.5f + 2) * leftRectAnimation / 4 , leftRectAnimation, oneColor.getColorValueColor());
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int button) {
        if (button == 0 && isHovered(mouseX, mouseY) && booleanSetting.isVisible()) {
            booleanSetting.setBoolValue(!booleanSetting.getCurrentValue());
        }
    }

    @Override
    public Setting getSetting() {
        return booleanSetting;
    }
}

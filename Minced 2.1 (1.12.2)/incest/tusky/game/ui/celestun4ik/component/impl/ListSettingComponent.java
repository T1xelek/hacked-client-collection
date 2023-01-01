package incest.tusky.game.ui.celestun4ik.component.impl;

import incest.tusky.game.tuskevich;
import incest.tusky.game.ui.celestun4ik.component.Component;
import incest.tusky.game.ui.celestun4ik.component.ExpandableComponent;
import incest.tusky.game.ui.celestun4ik.component.PropertyComponent;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import incest.tusky.game.ui.settings.impl.ListSetting;
import incest.tusky.game.module.impl.Render.ClickGUI;
import incest.tusky.game.ui.celestun4ik.panelcomponent;
import incest.tusky.game.ui.settings.Setting;
import incest.tusky.game.utils.render.RenderUtils;
import incest.tusky.game.utils.render.RoundedUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

import static incest.tusky.game.module.impl.Render.ModuleList.oneColor;


public class ListSettingComponent extends ExpandableComponent implements PropertyComponent {

    private final ListSetting listSetting;
    Minecraft mc = Minecraft.getMinecraft();

    public ListSettingComponent(Component parent, ListSetting listSetting, int x, int y, int width, int height) {
        super(parent, listSetting.getName(), x, y, width, height);
        this.listSetting = listSetting;
    }

    @Override
    public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        GlStateManager.pushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtils.drawRect(0, 0, 0, 0, new Color(30, 30, 30, 255).getRGB());
        RenderUtils.scissorRect(0, 25.5f, sr.getScaledWidth(), 239);
        super.drawComponent(scaledResolution, mouseX, mouseY);
        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();
        String selectedText = listSetting.currentMode;
        int dropDownBoxY = y + 4;
        int textColor = 0xFFFFFF;
        RoundedUtil.drawRound(x + panelcomponent.X_ITEM_OFFSET - 2, y + height - 1 - 20, x - 3 + width - panelcomponent.X_ITEM_OFFSET-(x + panelcomponent.X_ITEM_OFFSET - 2), y + (isExpanded() ? getHeightWithExpand() + 18 : 39 )-(y + height - 1), 7, new Color(55, 55, 55, 200));

        mc.Nunito14.drawCenteredString(getName(), x - 2 + width / 2, dropDownBoxY - 1, new Color(220,220,220).getRGB());
        handleRender(x, y + getHeight() + 2, width, textColor);
        if (isExpanded()) {
            RenderUtils.drawGradientSideways(x + panelcomponent.X_ITEM_OFFSET + width / 2 + 0.5f - 48 + 5, getY() + 20,x + panelcomponent.X_ITEM_OFFSET + width / 2 + 0.5f - 48 + width / 2, getY() + 20.5f, new Color(255,255,255,0).getRGB(), -1);
            RenderUtils.drawGradientSideways(x + panelcomponent.X_ITEM_OFFSET + width / 2 + 0.5f - 48 +  width / 2, getY() + 20,x + panelcomponent.X_ITEM_OFFSET + width / 2 + 0.5f - 48 + width - 9, getY() + 20.5f, -1, new Color(255,255,255,0).getRGB());
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GlStateManager.popMatrix();
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int button) {
        super.onMouseClick(mouseX, mouseY, button);
        if (isExpanded()) {
            handleClick(mouseX, mouseY, getX(), getY() + getHeight() + 2, getWidth());
        }
    }

    private void handleRender(int x, int y, int width, int textColor) {
        int color = 0;
        Color onecolor = new Color(oneColor.getColorValue());

        for (String e : listSetting.getModes()) {
            if (listSetting.currentMode.equals(e)) {
                mc.Nunito14.drawCenteredString(e, x + panelcomponent.X_ITEM_OFFSET + width / 2 + 0.5f - 48 + width / 2, getY() + 12, -1);
                if (isExpanded()) {
                    mc.Nunito14.drawCenteredString(e, x + panelcomponent.X_ITEM_OFFSET + width / 2 + 0.5f- 2, y + 0.5F, oneColor.getColorValue());
                }
            } else {
                if (isExpanded()) {
                    mc.Nunito14.drawCenteredString(e, x + panelcomponent.X_ITEM_OFFSET + width / 2 + 0.5f - 2, y + 0.5F, new Color(220,220,220).getRGB());
                }
            }

            y += (panelcomponent.ITEM_HEIGHT - 3);
        }
    }

    private void handleClick(int mouseX, int mouseY, int x, int y, int width) {
        for (String e : this.listSetting.getModes()) {
            if (mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + panelcomponent.ITEM_HEIGHT - 3) {
                listSetting.setListMode(e);
            }

            y += panelcomponent.ITEM_HEIGHT - 3;
        }
    }


    @Override
    public int getHeightWithExpand() {
        return getHeight() + listSetting.getModes().toArray().length * (panelcomponent.ITEM_HEIGHT - 3);
    }

    @Override
    public void onPress(int mouseX, int mouseY, int button) {
    }

    @Override
    public boolean canExpand() {
        return listSetting.modes.toArray().length > 0;
    }

    @Override
    public Setting getSetting() {
        return listSetting;
    }
}

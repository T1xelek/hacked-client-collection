package incest.tusky.game.ui.celestun4ik.component.impl;

import incest.tusky.game.module.Module;
import incest.tusky.game.ui.celestun4ik.guiscreencomponent;
import incest.tusky.game.ui.celestun4ik.panelcomponent;
import incest.tusky.game.ui.celestun4ik.component.Component;
import incest.tusky.game.ui.celestun4ik.component.ExpandableComponent;
import incest.tusky.game.ui.settings.impl.*;
import javafx.animation.Interpolator;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import incest.tusky.game.module.impl.Render.ClickGUI;
import incest.tusky.game.ui.components.SorterHelper;
import incest.tusky.game.ui.settings.Setting;
import incest.tusky.game.utils.math.TimerHelper;
import incest.tusky.game.utils.render.GaussianBlur;
import incest.tusky.game.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import incest.tusky.game.utils.render.RoundedUtil;
import incest.tusky.game.utils.render.StencilUtil;

import static incest.tusky.game.module.impl.Render.ModuleList.oneColor;

import java.awt.*;


public final class ModuleComponent extends ExpandableComponent {
    Minecraft mc = Minecraft.getMinecraft();
    private final Module module;
    public static TimerHelper timerHelper = new TimerHelper();
    private boolean binding;
    int alpha = 0;
    private final TimerHelper descTimer = new TimerHelper();

    public ModuleComponent(Component parent, Module module, int x, int y, int width, int height) {
        super(parent, module.getLabel(), x, y, width, height);
        this.module = module;
        int propertyX = panelcomponent.X_ITEM_OFFSET;
        for (Setting setting : module.getSettings()) {
            if (setting instanceof BooleanSetting) {
                components.add(new BooleanSettingComponent(this, (BooleanSetting) setting, propertyX, height, width - (panelcomponent.X_ITEM_OFFSET * 2), panelcomponent.ITEM_HEIGHT + 6));
            } else if (setting instanceof ColorSetting) {
                components.add(new ColorPickerComponent(this, (ColorSetting) setting, propertyX, height, width - (panelcomponent.X_ITEM_OFFSET * 2), panelcomponent.ITEM_HEIGHT));
            } else if (setting instanceof NumberSetting) {
                components.add(new NumberSettingComponent(this, (NumberSetting) setting, propertyX, height, width - (panelcomponent.X_ITEM_OFFSET * 2), panelcomponent.ITEM_HEIGHT + 5));
            } else if (setting instanceof ListSetting) {
                components.add(new ListSettingComponent(this, (ListSetting) setting, propertyX, height, width - (panelcomponent.X_ITEM_OFFSET * 2), panelcomponent.ITEM_HEIGHT + 2));
            } else if (setting instanceof MultipleBoolSetting) {
                components.add(new MultipleBoolSettingComponent(this, (MultipleBoolSetting) setting, propertyX, height, width - (panelcomponent.X_ITEM_OFFSET * 2), panelcomponent.ITEM_HEIGHT + 1));
            }

        }
    }

    public boolean ready = false;
    static String i = " ";

    String getI(String s) {
        if (!timerHelper.hasReached(5)) {
            return i;
        } else {
            timerHelper.reset();
        }
        if (i.length() < s.length()) {
            ready = false;
            return i += s.charAt(i.length());
        }
        ready = true;
        return i;
    }

    @Override
    public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        components.sort(new SorterHelper());
        float x = getX();
        float y = getY() - 2;
        int width = getWidth();
        int height = getHeight();
        Color color = new Color(oneColor.getColorValue());
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 140);
        boolean hovered = isHovered(mouseX, mouseY);
        ScaledResolution sr = new ScaledResolution(mc);
        GlStateManager.pushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtils.drawRect(x, y, x, y, new Color(30, 30, 30, 255).getRGB());
        RenderUtils.scissorRect(0, 25.5f, sr.getScaledWidth(), 239);



        components.sort(new SorterHelper());


        if (module.isEnabled()) {
            if (components.size() > 0.5) {
                mc.fontRendererObj.drawString(isExpanded() ? "\u25b2" : "\u25bc", x + 93.5f-8, y + height / 2F - 3.5f,  oneColor.getColorValueColor().getRGB());
            }
            mc.Nunito16.drawCenteredString(binding ? "Press a key.. " : getName(), x + 53.5f-8, y + height / 2F - 3, oneColor.getColorValueColor().getRGB());

        } else {
            if (components.size() > 0.5) {
                mc.fontRendererObj.drawString(isExpanded() ? "\u25b2" : "\u25bc", x + 93.5f-8, y + height / 2F - 3.5f, Color.WHITE.getRGB());
            }
            mc.Nunito16.drawCenteredString(binding ? "Press a key.. " : getName(), x + 53.5f-8, y + height / 2F - 3, Color.WHITE.getRGB());
        }
        if (isExpanded()) {
            int childY = panelcomponent.ITEM_HEIGHT;
            for (Component child : components) {
                int cHeight = child.getHeight();
                if (child instanceof BooleanSettingComponent) {
                    BooleanSettingComponent booleanSettingComponent = (BooleanSettingComponent) child;
                    if (!booleanSettingComponent.booleanSetting.isVisible()) {
                        continue;
                    }
                }
                if (child instanceof NumberSettingComponent) {
                    NumberSettingComponent numberSettingComponent = (NumberSettingComponent) child;
                    if (!numberSettingComponent.numberSetting.isVisible()) {
                        continue;
                    }
                }

                if (child instanceof ColorPickerComponent) {
                    ColorPickerComponent colorPickerComponent = (ColorPickerComponent) child;
                    if (!colorPickerComponent.getSetting().isVisible()) {
                        continue;
                    }
                }
                if (child instanceof ListSettingComponent) {
                    ListSettingComponent listSettingComponent = (ListSettingComponent) child;
                    if (!listSettingComponent.getSetting().isVisible()) {
                        continue;
                    }
                }
                if (child instanceof ExpandableComponent) {
                    ExpandableComponent expandableComponent = (ExpandableComponent) child;
                    if (expandableComponent.isExpanded()) cHeight = expandableComponent.getHeightWithExpand();
                }
                child.setY(childY);
                child.drawComponent(scaledResolution, mouseX, mouseY);
                childY += cHeight;
            }
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GlStateManager.popMatrix();

    }

    @Override
    public boolean canExpand() {
        return !components.isEmpty();
    }

    @Override
    public void onPress(int mouseX, int mouseY, int button) {
        switch (button) {
            case 0:
                module.toggle();
                break;
            case 2:
                binding = !binding;
                break;
        }
    }

    @Override
    public void onKeyPress(int keyCode) {
        if (binding) {
            guiscreencomponent.escapeKeyInUse = true;
            module.setBind(keyCode == Keyboard.KEY_DELETE ? Keyboard.KEY_NONE : keyCode);
            binding = false;
        }
    }

    @Override
    public int getHeightWithExpand() {
        int height = getHeight();
        if (isExpanded()) {
            for (Component child : components) {
                int cHeight = child.getHeight();
                if (child instanceof BooleanSettingComponent) {
                    BooleanSettingComponent booleanSettingComponent = (BooleanSettingComponent) child;
                    if (!booleanSettingComponent.booleanSetting.isVisible()) {
                        continue;
                    }
                }
                if (child instanceof NumberSettingComponent) {
                    NumberSettingComponent numberSettingComponent = (NumberSettingComponent) child;
                    if (!numberSettingComponent.numberSetting.isVisible()) {
                        continue;
                    }
                }
                if (child instanceof ColorPickerComponent) {
                    ColorPickerComponent colorPickerComponent = (ColorPickerComponent) child;
                    if (!colorPickerComponent.getSetting().isVisible()) {
                        continue;
                    }
                }
                if (child instanceof ListSettingComponent) {
                    ListSettingComponent listSettingComponent = (ListSettingComponent) child;
                    if (!listSettingComponent.getSetting().isVisible()) {
                        continue;
                    }
                }
                if (child instanceof ExpandableComponent) {
                    ExpandableComponent expandableComponent = (ExpandableComponent) child;
                    if (expandableComponent.isExpanded()) cHeight = expandableComponent.getHeightWithExpand();
                }
                height += cHeight;
            }
        }
        return height;
    }

}

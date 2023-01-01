package incest.tusky.game.ui.ClickGui.component.impl;

import incest.tusky.game.module.Module;
import incest.tusky.game.module.impl.Render.ClickGUI;
import incest.tusky.game.ui.ClickGui.SorterHelper;
import incest.tusky.game.ui.ClickGui.GuiScreen;
import incest.tusky.game.ui.ClickGui.panelcomponent;
import incest.tusky.game.ui.ClickGui.component.Component;
import incest.tusky.game.ui.ClickGui.component.ExpandableComponent;
import incest.tusky.game.ui.settings.impl.*;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import incest.tusky.game.ui.settings.Setting;
import incest.tusky.game.utils.math.TimerHelper;
import incest.tusky.game.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import incest.tusky.game.utils.render.RoundedUtil;

import java.awt.*;

import static incest.tusky.game.module.impl.Render.ModuleList.oneColor;


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
        Color color = new Color(ClickGUI.color.getColorValue());
        Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 140);
        boolean hovered = isHovered(mouseX, mouseY);
        ScaledResolution sr = new ScaledResolution(mc);
        GlStateManager.pushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtils.drawRect(x, y, x, y, new Color(30, 30, 30, 255).getRGB());
        RenderUtils.scissorRect(0, 25.5f, sr.getScaledWidth(), 239);

        Color clor = module.isEnabled() ? new Color(ClickGUI.color.getColorValue()): Color.WHITE;

        Color clor2 = module.isEnabled() ? new Color(ClickGUI.color2.getColorValue()).darker(): Color.WHITE;



        components.sort(new SorterHelper());
        if (hovered && module.getDesc() != null) {


            if (!hovered) {
                i = " ";
            }
            RoundedUtil.drawGradientRound(0, 0,0,0, 2, clor, clor,clor2,clor2);

            if (hovered && ready) {
                RoundedUtil.drawRound(x + width + 18, y + height / 1.5F + 3.5F, x + width + 25 + mc.fontRendererObj.getStringWidth(module.getDesc()), y + 3.5F, 1, new Color(25,25,25));
                mc.mntsb_17.drawStringWithShadow(module.getDesc(), x + width + 22, y + height / 1.35F - 6F, -1);
            }
            RoundedUtil.drawGradientRound(0, 0,0,0, 2, clor, clor,clor2,clor2);

            if (module == null) i = "";
            else {
                if (ready && !i.equals(module.getDesc())) i = "";
            }
        } else {
            ready = false;
        }

        if (ClickGUI.shadow.getCurrentValue()) {
            RenderUtils.drawBlurredShadow(x - 3, y + height / 2F - 2 - 5, width + 2, isExpanded() ? getHeightWithExpand() + 1 : 15.5f, 5, new Color(0,0,0, 150));
        }

        RoundedUtil.drawGradientRound(x - 3, y + height / 2F - 2 - 5, width + 1, isExpanded() ? getHeightWithExpand() + 1 : 15.5f, 2, clor, clor,clor2,clor2);

        if (components.size() > 0.5) {
            mc.fontRendererObj.drawString(isExpanded() ? "\u25b2" : "\u25bc", x + 93.5f-8, y + height / 2F - 3.5f, module.isEnabled() ? Color.WHITE.getRGB() : Color.BLACK.getRGB());
        }


        if (module.isEnabled()) {
            mc.mntsb_15.drawCenteredString(binding ? "Press a key.. " : getName(), x + 53.5f-8 + 0.5f, y + height / 2F - 3 + 1 + 0.5f, Color.BLACK.getRGB());
        }


        mc.mntsb_15.drawCenteredString(binding ? "Press a key.. " : getName(), x + 53.5f-8, y + height / 2F - 3 + 1, module.isEnabled() ? Color.WHITE.getRGB() : Color.BLACK.getRGB());

        if (isExpanded()) {
            int childY = incest.tusky.game.ui.ClickGui.panelcomponent.ITEM_HEIGHT;
            for (incest.tusky.game.ui.ClickGui.component.Component child : components) {
                int cHeight = child.getHeight();
                if (child instanceof incest.tusky.game.ui.ClickGui.component.impl.BooleanSettingComponent) {
                    incest.tusky.game.ui.ClickGui.component.impl.BooleanSettingComponent booleanSettingComponent = (incest.tusky.game.ui.ClickGui.component.impl.BooleanSettingComponent) child;
                    if (!booleanSettingComponent.booleanSetting.isVisible()) {
                        continue;
                    }
                }
                if (child instanceof incest.tusky.game.ui.ClickGui.component.impl.NumberSettingComponent) {
                    incest.tusky.game.ui.ClickGui.component.impl.NumberSettingComponent numberSettingComponent = (incest.tusky.game.ui.ClickGui.component.impl.NumberSettingComponent) child;
                    if (!numberSettingComponent.numberSetting.isVisible()) {
                        continue;
                    }
                }

                if (child instanceof incest.tusky.game.ui.ClickGui.component.impl.ColorPickerComponent) {
                    incest.tusky.game.ui.ClickGui.component.impl.ColorPickerComponent colorPickerComponent = (incest.tusky.game.ui.ClickGui.component.impl.ColorPickerComponent) child;
                    if (!colorPickerComponent.getSetting().isVisible()) {
                        continue;
                    }
                }
                if (child instanceof incest.tusky.game.ui.ClickGui.component.impl.ListSettingComponent) {
                    incest.tusky.game.ui.ClickGui.component.impl.ListSettingComponent listSettingComponent = (incest.tusky.game.ui.ClickGui.component.impl.ListSettingComponent) child;
                    if (!listSettingComponent.getSetting().isVisible()) {
                        continue;
                    }
                }
                if (child instanceof incest.tusky.game.ui.ClickGui.component.ExpandableComponent) {
                    incest.tusky.game.ui.ClickGui.component.ExpandableComponent expandableComponent = (incest.tusky.game.ui.ClickGui.component.ExpandableComponent) child;
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
            GuiScreen.escapeKeyInUse = true;
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

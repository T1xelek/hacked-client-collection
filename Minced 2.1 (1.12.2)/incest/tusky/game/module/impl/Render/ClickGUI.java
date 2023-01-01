package incest.tusky.game.module.impl.Render;

import incest.tusky.game.tuskevich;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.ColorSetting;
import incest.tusky.game.ui.settings.impl.ListSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.utils.Helper;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class ClickGUI extends Module {
    public static BooleanSetting blur = new BooleanSetting("Blur", false, () -> true);
    public static BooleanSetting shadow = new BooleanSetting("Shadow", true, () -> true);


    public ListSetting mode = new ListSetting("ClickGui Mode", "Nursultan", () -> true, "Nursultan", "Celestial");
    public static ColorSetting color;
    public static ColorSetting color2;

    public ClickGUI() {
        super("Click GUI", "Клек гуе", ModuleCategory.Visual);
        setBind(Keyboard.KEY_RSHIFT);
        color = new ColorSetting("Gui Color", new Color(158, 13, 239, 255).getRGB(), () -> mode.currentMode.equals("Celestial"));
        color2 = new ColorSetting("Gui Color 2", new Color(0, 255, 244).getRGB(), () -> mode.currentMode.equals("Celestial"));
        addSettings(mode, color, color2, blur);

    }


    @Override
    public void onEnable() {
        if (mode.currentMode.equals("Celestial")) {
            mc.displayGuiScreen(tuskevich.instance.Celestial);
        } else if (mode.currentMode.equals("Nursultan")) {
            mc.displayGuiScreen(tuskevich.instance.clickGui);
        }
        tuskevich.instance.featureManager.getFeature(ClickGUI.class).setEnabled(false);
        super.onEnable();
    }
}
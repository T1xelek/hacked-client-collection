package incest.tusky.game.module.impl.Render;

import incest.tusky.game.module.Module;
import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.render.EventFogColor;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.ui.settings.impl.ColorSetting;
import incest.tusky.game.ui.settings.impl.ListSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.utils.render.ClientHelper;
import incest.tusky.game.utils.render.ColorUtils;

import java.awt.*;

public class FogColor extends Module {
    public ListSetting colorMode = new ListSetting("Fog Color", "Rainbow", () -> true, "Rainbow", "Custom");
    public static NumberSetting distance;
    public ColorSetting customColor;

    public FogColor() {
        super("FogColor", "Изменяет цвет тумана", ModuleCategory.Player);
        distance = new NumberSetting("Fog Distance", 0.7f, 0.1f, 4.0f, 0.01f, () -> true);
        customColor = new ColorSetting("Custom Fog", new Color(0xFFFFFF).getRGB(), () -> colorMode.currentMode.equals("Custom"));
        addSettings(colorMode, distance, customColor);
    }

    @EventTarget
    public void onFogColor(EventFogColor event) {
        String colorModeValue = colorMode.getOptions();
        if (colorModeValue.equalsIgnoreCase("Rainbow")) {
            Color color = ColorUtils.rainbow(1, 1.0f, 1.0f);
            event.setRed(color.getRed());
            event.setGreen(color.getGreen());
            event.setBlue(color.getBlue());

        } else if (colorModeValue.equalsIgnoreCase("Custom")) {
            Color customColorValue = new Color(customColor.getColorValue());
            event.setRed(customColorValue.getRed());
            event.setGreen(customColorValue.getGreen());
            event.setBlue(customColorValue.getBlue());
        }
    }
}

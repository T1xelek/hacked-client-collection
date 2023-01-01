package incest.tusky.game.module.impl.Render;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.packet.EventReceivePacket;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.ui.settings.impl.BooleanSetting;
import incest.tusky.game.ui.settings.impl.ColorSetting;
import incest.tusky.game.ui.settings.impl.ListSetting;
import incest.tusky.game.ui.settings.impl.NumberSetting;
import incest.tusky.game.utils.Helper;
import net.minecraft.network.play.server.SPacketTimeUpdate;

import java.awt.*;

public class WorldFeatures extends Module {
    private long spinTime = 0;

    public static BooleanSetting snow = new BooleanSetting("Snow", true, () -> true);
    public static ColorSetting weatherColor = new ColorSetting("Weather", new Color(0xFFFFFF).getRGB(), () -> snow.getCurrentValue());
    public static BooleanSetting worldColor = new BooleanSetting("World Color", false, () -> true);
    public static ColorSetting worldColors = new ColorSetting("Color World", new Color(0xFFFFFF).getRGB(), () -> worldColor.getCurrentValue());
    public BooleanSetting ambience = new BooleanSetting("Ambience", false, () -> true);
    public ListSetting ambienceMode = new ListSetting("Ambience Mode", "Day", () -> ambience.getCurrentValue(), "Day", "Night", "Morning", "Sunset", "Spin");
    public NumberSetting ambienceSpeed = new NumberSetting("Ambience Speed", 20.f, 0.1f, 1000.f, 1, () -> ambienceMode.currentMode.equals("Spin"));

    public WorldFeatures() {
        super("WorldFeature","Меняет мир по новому", ModuleCategory.Visual);
        addSettings(snow, weatherColor, worldColor, worldColors, ambience, ambienceMode, ambienceSpeed);
    }

    @EventTarget
    public void onPacket(EventReceivePacket event) {
        if (ambience.getCurrentValue()) {
            if (event.getPacket() instanceof SPacketTimeUpdate) {
                event.setCancelled(true);
            }
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        String mode = ambienceMode.getOptions();
        if (ambience.getCurrentValue()) {
            if (mode.equalsIgnoreCase("Spin")) {
                Helper.mc.world.setWorldTime(spinTime);
                this.spinTime = (long) (spinTime + ambienceSpeed.getCurrentValue());
            } else if (mode.equalsIgnoreCase("Day")) {
                Helper.mc.world.setWorldTime(5000);
            } else if (mode.equalsIgnoreCase("Night")) {
                Helper.mc.world.setWorldTime(17000);
            } else if (mode.equalsIgnoreCase("Morning")) {
                Helper.mc.world.setWorldTime(0);
            } else if (mode.equalsIgnoreCase("Sunset")) {
                Helper.mc.world.setWorldTime(13000);
            }
        }
    }
}

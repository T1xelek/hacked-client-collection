package incest.tusky.game.module.impl.Render;

import incest.tusky.game.event.EventTarget;
import incest.tusky.game.event.events.impl.player.EventUpdate;
import incest.tusky.game.module.Module;
import incest.tusky.game.module.ModuleCategory;
import incest.tusky.game.ui.settings.impl.ListSetting;
import incest.tusky.game.utils.Helper;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

@SuppressWarnings("all")
public class FullBright extends Module {

    private final ListSetting brightMode = new ListSetting("FullBright Mode", "Gamma", () -> true, "Gamma", "Potion");
    ;

    public FullBright() {
        super("FullBright", ModuleCategory.Player);
        addSettings(brightMode);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (isEnabled()) {
            String mode = brightMode.getOptions();
            if (mode.equalsIgnoreCase("Gamma")) {
                Helper.mc.gameSettings.gammaSetting = 10f;
            }
            if (mode.equalsIgnoreCase("Potion")) {
                Helper.mc.player.addPotionEffect(new PotionEffect(Potion.getPotionById(16), 817, 1));
            } else {
                Helper.mc.player.removePotionEffect(Potion.getPotionById(16));
            }
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        Helper.mc.gameSettings.gammaSetting = 0.1f;
        Helper.mc.player.removePotionEffect(Potion.getPotionById(16));
    }
}
